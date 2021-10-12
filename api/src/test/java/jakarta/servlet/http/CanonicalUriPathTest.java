package jakarta.servlet.http;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CanonicalUriPathTest {

    private static final Set<String> ENCODED_DOT_SEGMENT;
    static {
        Set<String> set = Collections.newSetFromMap(new TreeMap<>(String.CASE_INSENSITIVE_ORDER));
        set.add("%2e");
        set.add("%2e%2e");
        set.add("%2e.");
        set.add(".%2e");
        ENCODED_DOT_SEGMENT = Collections.unmodifiableSet(set);
    }

    public static String canonicalUriPath(String uriPath, Consumer<String> rejection) {
        if (uriPath == null)
            throw new IllegalArgumentException("null path");

        String path = uriPath;

        // Discard fragment.
        if (path.contains("#"))
            path = path.substring(0, path.indexOf('#'));

        // Separation of path and query.
        if (path.contains("?"))
            path = path.substring(0, path.indexOf('?'));

        // Remember start/end conditions
        boolean startsWithSlash = path.startsWith("/");
        boolean endsWithSlash = path.endsWith("/");
        boolean dotSegmentWithParam;
        boolean encodedDotSegment;
        boolean emptySegmentWithParam;
        boolean emptySegmentBeforeDotDot = false;
        boolean decodeError = false;

        // Split path into segments.
        List<String> segments = new ArrayList<>(Arrays.asList(path.substring(startsWithSlash ? 1 : 0).split("/")));

        // Remove path parameters.
        emptySegmentWithParam = segments.stream().anyMatch(s -> s.startsWith(";"));
        dotSegmentWithParam = segments.stream().anyMatch(s -> s.startsWith(".;") || s.startsWith("..;"));
        segments.replaceAll(s -> (s.contains(";")) ? s.substring(0, s.indexOf(';')) : s);

        // Decode characters
        encodedDotSegment = segments.stream().anyMatch(ENCODED_DOT_SEGMENT::contains);
        try {
            segments.replaceAll(CanonicalUriPathTest::decode);
        } catch (Exception e) {
            decodeError = true;
        }

        // Remove Empty Segments
        segments.removeIf(s -> s.length() == 0);

        // Remove dot-segments
        int count = 0;
        for (ListIterator<String> s = segments.listIterator(); s.hasNext();) {
            String segment = s.next();
            if (segment.equals(".")) {
                s.remove();
            } else if (segment.equals("..")) {
                if (count > 0) {
                    s.remove();
                    String prev = s.previous();
                    s.remove();
                    count--;
                    emptySegmentBeforeDotDot |= prev.length() == 0;
                }
            } else {
                count++;
            }
        }

        // Concatenate segments
        StringBuilder buf = new StringBuilder();
        segments.forEach(s -> buf.append("/").append(s));
        if (endsWithSlash || segments.size() == 0)
            buf.append("/");
        path = buf.toString();

        // Rejecting Errors and Suspicious Sequences
        if (decodeError)
            rejection.accept("decode error");
        // Any path not starting with the `"/"` character
        if (!startsWithSlash)
            rejection.accept("must start with /");
        // Any path starting with an initial segment of `".."`
        if (!segments.isEmpty() && segments.get(0).equals(".."))
            rejection.accept("leading dot-dot-segment");
        // The encoded `"/"` character
        if (uriPath.contains("%2f") || uriPath.contains("%2F"))
            rejection.accept("encoded /");
        // Any `"."` or `".."` segment that had a path parameter
        if (dotSegmentWithParam)
            rejection.accept("dot segment with parameter");
        // Any `"."` or `".."` segment with any encoded characters
        if (encodedDotSegment)
            rejection.accept("encoded dot segment");
        // Any `".."` segment preceded by an empty segment
        if (emptySegmentBeforeDotDot)
            rejection.accept("empty segment before dot dot");
        // Any empty segment with parameters
        if (emptySegmentWithParam)
            rejection.accept("empty segment with parameters");
        // The `"\"` character encoded or not.
        if (path.contains("\\"))
            rejection.accept("backslash character");
        // Any control characters either encoded or not.
        for (char c : path.toCharArray()) {
            if (c < 0x20 || c == 0x7f) {
                rejection.accept("control character");
                break;
            }
        }

        return path;
    }

    private static String decode(String segment) {
        if (segment.contains("%")) {
            StringBuilder buf = new StringBuilder();
            ByteArrayOutputStream utf8 = new ByteArrayOutputStream();
            for (int i = 0; i < segment.length(); i++) {
                char c = segment.charAt(i);
                if (c == '%') {
                    int b = Integer.parseInt(segment.substring(i + 1, i + 3), 16);
                    if (b < 0)
                        throw new IllegalArgumentException("negative encoding");
                    utf8.write(b);
                    i += 2;
                } else {
                    if (utf8.size() > 0) {
                        buf.append(fromUtf8(utf8.toByteArray()));
                        utf8.reset();
                    }

                    buf.append(c);
                }
            }
            if (utf8.size() > 0) {
                buf.append(fromUtf8(utf8.toByteArray()));
                utf8.reset();
            }
            segment = buf.toString();
        }
        return segment;
    }

    private static CharBuffer fromUtf8(byte[] bytes) {
        try {
            return StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(bytes));
        } catch (CharacterCodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Stream<Arguments> data() {
        List<Object[]> data = new ArrayList<>();
        data.add(new Object[] { "foo/bar", "/foo/bar", true });
        data.add(new Object[] { "/foo/bar", "/foo/bar", false });
        data.add(new Object[] { "/foo/bar;jsessionid=1234", "/foo/bar", false });
        data.add(new Object[] { "/foo/bar/", "/foo/bar/", false });
        data.add(new Object[] { "/foo/bar/;jsessionid=1234", "/foo/bar/", false });
        data.add(new Object[] { "/foo;/bar;", "/foo/bar", false });
        data.add(new Object[] { "/foo;/bar;/;", "/foo/bar/", false });
        data.add(new Object[] { "/foo%00/bar/", "/foo\00/bar/", true });
        data.add(new Object[] { "/foo%2Fbar", "/foo/bar", true });
        data.add(new Object[] { "/foo\\bar", "/foo\\bar", true });
        data.add(new Object[] { "/foo%5Cbar", "/foo\\bar", true });
        data.add(new Object[] { "/foo;%2F/bar", "/foo/bar", true });
        data.add(new Object[] { "/foo/./bar", "/foo/bar", false });
        data.add(new Object[] { "/foo/././bar", "/foo/bar", false });
        data.add(new Object[] { "/./foo/bar", "/foo/bar", false });
        data.add(new Object[] { "/foo/%2e/bar", "/foo/bar", true });
        data.add(new Object[] { "/foo/.;/bar", "/foo/bar", true });
        data.add(new Object[] { "/foo/%2e;/bar", "/foo/bar", true });
        data.add(new Object[] { "/foo/.%2Fbar", "/foo/./bar", true });
        data.add(new Object[] { "/foo/.%5Cbar", "/foo/.\\bar", true });
        data.add(new Object[] { "/foo/bar/.", "/foo/bar", false });
        data.add(new Object[] { "/foo/bar/./", "/foo/bar/", false });
        data.add(new Object[] { "/foo/bar/.;", "/foo/bar", true });
        data.add(new Object[] { "/foo/bar/./;", "/foo/bar/", false });
        data.add(new Object[] { "/foo/.bar", "/foo/.bar", false });
        data.add(new Object[] { "/foo/../bar", "/bar", false });
        data.add(new Object[] { "/foo/../../bar", "/../bar", true });
        data.add(new Object[] { "/../foo/bar", "/../foo/bar", true });
        data.add(new Object[] { "/foo/%2e%2E/bar", "/bar", true });
        data.add(new Object[] { "/foo/%2e%2e/%2E%2E/bar", "/../bar", true });
        data.add(new Object[] { "/foo/./../bar", "/bar", false });
        data.add(new Object[] { "/foo/..;/bar", "/bar", true });
        data.add(new Object[] { "/foo/%2e%2E;/bar", "/bar", true });
        data.add(new Object[] { "/foo/..%2Fbar", "/foo/../bar", true });
        data.add(new Object[] { "/foo/..%5Cbar", "/foo/..\\bar", true });
        data.add(new Object[] { "/foo/bar/..", "/foo", false });
        data.add(new Object[] { "/foo/bar/../", "/foo/", false });
        data.add(new Object[] { "/foo/bar/..;", "/foo", true });
        data.add(new Object[] { "/foo/bar/../;", "/foo/", false });
        data.add(new Object[] { "/foo/..bar", "/foo/..bar", false });
        data.add(new Object[] { "/foo/.../bar", "/foo/.../bar", false });
        data.add(new Object[] { "/foo//bar", "/foo/bar", false });
        data.add(new Object[] { "//foo//bar//", "/foo/bar/", false });
        data.add(new Object[] { "/;/foo;/;/bar/;/;", "/foo/bar/", true });
        data.add(new Object[] { "/foo//../bar", "/bar", false });
        data.add(new Object[] { "/foo/;/../bar", "/bar", true });
        data.add(new Object[] { "/foo%E2%82%ACbar", "/foo€bar", false });
        data.add(new Object[] { "/foo%20bar", "/foo bar", false });
        data.add(new Object[] { "/foo%E2%82", "/foo%E2%82", true });
        data.add(new Object[] { "/foo%E2%82bar", "/foo%E2%82bar", true });
        data.add(new Object[] { "/foo%XX/bar", "/foo%XX/bar", true });
        data.add(new Object[] { "/foo%/bar", "/foo%/bar", true });
        data.add(new Object[] { "/foo/bar%0", "/foo/bar%0", true });
        data.add(new Object[] { "/good%20/bad%/%20mix%", "/good /bad%/%20mix%", true });
        data.add(new Object[] { "/foo/bar#f", "/foo/bar", false });
        data.add(new Object[] { "/foo/bar?q#f", "/foo/bar", false });
        data.add(new Object[] { "/foo/bar/?q", "/foo/bar", false });
        data.add(new Object[] { "/foo/bar/#f", "/foo/bar", false });
        data.add(new Object[] { "/foo/bar/?q#f", "/foo/bar", false });
        data.add(new Object[] { "/foo/bar;?q", "/foo/bar", false });
        data.add(new Object[] { "/foo/bar;#f", "/foo/bar", false });
        data.add(new Object[] { "/foo/bar;?q#f", "/foo/bar", false });
        data.add(new Object[] { "/", "/", false });
        data.add(new Object[] { "//", "/", false });
        data.add(new Object[] { "/;/", "/", true });
        data.add(new Object[] { "/.", "/", false });
        data.add(new Object[] { "/..", "/..", true });
        data.add(new Object[] { "/./", "/", false });
        data.add(new Object[] { "/../", "/../", true });
        data.add(new Object[] { "foo/bar/", "/foo/bar/", true });
        data.add(new Object[] { ";/foo/bar/", "/foo/bar/", true });

        return data.stream().map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("data")
    public void testCanonicalUriPath(String path, String expected, boolean rejected) {
        List<String> rejections = new ArrayList<>();
        String canonical = canonicalUriPath(path, rejections::add);

        Assertions.assertEquals(expected, canonical);
        Assertions.assertEquals(rejected, !rejections.isEmpty());

        // print for inclusion in adoc
        System.err.printf("| `%s` | `%s` | ", path, canonical);
        if (!rejections.isEmpty()) {
            for (int i = 0; i < rejections.size(); i++) {
                System.err.print(i == 0 ? "400 " : " & ");
                System.err.print(rejections.get(i));
            }
        }
        System.err.println();
    }
}

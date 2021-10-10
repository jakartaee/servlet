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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CanonicalUriPathTest {
    private static final Set<String> ENCODED_DOT_SEGMENT = Collections.unmodifiableSet(Set.of(
            "%2e",
            "%2E",
            "%2e%2e",
            "%2e%2E",
            "%2E%2e",
            "%2E%2E",
            ".%2e",
            ".%2E",
            "%2e.",
            "%2E."));

    public static String canonicalUriPath(String uriPath) {
        if (uriPath == null)
            throw new IllegalArgumentException("null path");

        // Remember start/end conditions
        String path = uriPath;
        boolean startsWithSlash = path.startsWith("/");
        boolean endsWithSlash = path.endsWith("/");
        boolean dotSegmentWithParam;
        boolean encodedDotSegment;
        boolean emptySegmentWithParam;
        boolean emptySegmentBeforeDotDot = false;
        boolean decodeError = false;

        // Discard fragment.
        if (path.contains("#"))
            path = path.substring(0, path.indexOf('#'));

        // Separation of path and query.
        if (path.contains("?"))
            path = path.substring(0, path.indexOf('?'));

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
        String reject = "";
        if (decodeError)
            reject += "decode error; ";
        // Any path not starting with the `"/"` character
        if (!startsWithSlash)
            reject += "must start with /; ";
        // Any path starting with an initial segment of `".."`
        if (!segments.isEmpty() && segments.get(0).equals(".."))
            reject += "leading dot-dot-segment; ";
        // The encoded `"/"` character
        if (uriPath.contains("%2f") || uriPath.contains("%2F"))
            reject += "encoded /; ";
        // Any `"."` or `".."` segment that had a path parameter
        if (dotSegmentWithParam)
            reject += "dot segment with parameter; ";
        // Any `"."` or `".."` segment with any encoded characters
        if (encodedDotSegment)
            reject += "encoded dot segment; ";
        // Any `".."` segment preceded by an empty segment
        if (emptySegmentBeforeDotDot)
            reject += "empty segment before dot dot; ";
        // Any empty segment with parameters
        if (emptySegmentWithParam)
            reject += "empty segment with parameters; ";
        // The `"\"` character encoded or not.
        if (path.contains("\\"))
            reject += "backslash character; ";
        // Any control characters either encoded or not.

        for (char c : path.toCharArray()) {
            if (c < 0x20 || c == 0x7f) {
                reject = "control character; ";
                break;
            }
        }

        System.err.printf("| `%s` | `%s` | %s %n",
                uriPath,
                path,
                reject.length() == 0 ? "" : ("400 " + reject.substring(0, reject.length() - 2)));

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

    @ParameterizedTest
    @ValueSource(strings = {
            "foo/bar",
            "/foo/bar",

            "/foo/bar/",
            "/foo;/bar;",
            "/foo;/bar;/;",
            "/foo%00/bar/",
            "/foo%7F/bar/",
            "/foo%2Fbar",
            "/foo\\bar",
            "/foo%5Cbar",
            "/foo;%2E/bar",
            "/foo;%2F/bar",

            "/foo/./bar",
            "/foo/././bar",
            "/./foo/bar",
            "/foo/%2e/bar",
            "/foo/.;/bar",
            "/foo/%2e;/bar",
            "/foo/.%2Fbar",
            "/foo/.%5Cbar",
            "/foo/bar/.",
            "/foo/bar/./",
            "/foo/bar/.;",
            "/foo/bar/./;",
            "/foo/.bar",

            "/foo/../bar",
            "/foo/../../bar",
            "/../foo/bar",
            "/foo/%2e%2E/bar",
            "/foo/%2e%2e/%2E%2E/bar",
            "/foo/./../bar",
            "/foo/..;/bar",
            "/foo/%2e%2E;/bar",
            "/foo/..%2Fbar",
            "/foo/..%5Cbar",
            "/foo/bar/..",
            "/foo/bar/../",
            "/foo/bar/..;",
            "/foo/bar/../;",
            "/foo/..bar",

            "/foo/.../bar",

            "/foo//bar",
            "//foo//bar//",
            "/;/foo;/;/bar/;/;",
            "/foo//../bar",
            "/foo/;/../bar",

            "/foo%E2%82%ACbar",
            "/foo%20bar",
            "/foo%E2%82",
            "/foo%E2%82%AC/%E2%82%ACfoo%E2%820bar",
            "/foo%-1bar",
            "/foo%XX/bar",
            "/foo%/bar",
            "/foo/bar%0",

            "/foo/bar?q",
            "/foo/bar#f",
            "/foo/bar?q#f",
            "/foo/bar/?q",
            "/foo/bar/#f",
            "/foo/bar/?q#f",
            "/foo/bar;?q",
            "/foo/bar;#f",
            "/foo/bar;?q#f",

            "/",
            "//",
            "/;/",
            "/.",
            "/..",
            "/./",
            "/../",

            "foo/bar/",
            "./foo/bar/",
            "%2e/foo/bar/",
            "../foo/bar/",
            ".%2e/foo/bar/",
            ";/foo/bar/",
    })

    public void testCanonicalUriPath(String path) {
        canonicalUriPath(path);
    }
}

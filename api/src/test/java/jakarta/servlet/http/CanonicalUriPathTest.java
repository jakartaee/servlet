package jakarta.servlet.http;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CanonicalUriPathTest {

    public static String canonicalUriPath(String uriPath) {
        if (uriPath == null)
            throw new IllegalArgumentException("null path");

        String path = uriPath;

        // Separation of path and query.
        if (path.contains("?"))
            path = path.substring(0, path.indexOf('?'));

        // Discard fragment.
        if (path.contains("#"))
            path = path.substring(0, path.indexOf('#'));

        // Remember start/end conditions
        boolean startsWithSlash = path.startsWith("/");
        boolean endsWithSlash = path.startsWith("/");
        boolean dotSegmentWithParam = false;
        boolean encodedDotSegment = false;
        boolean emptySegmentBeforeDotDot = false;

        // Split path into segments.
        List<String> segments = new ArrayList<>(Arrays.asList(path.substring(1).split("/")));

        // Remove path parameters.
        for (ListIterator<String> s = segments.listIterator(); s.hasNext();) {
            String segment = s.next();
            if (segment.contains(";")) {
                segment = segment.substring(0, segment.indexOf(';'));
                s.set(segment);

                dotSegmentWithParam |= (".".equals(segment) || "..".equals(segment));
            }
        }

        // Decode characters
        for (ListIterator<String> s = segments.listIterator(); s.hasNext();) {
            String segment = s.next();
            if (segment.contains("%")) {
                StringBuilder buf = new StringBuilder();
                ByteArrayOutputStream utf8 = new ByteArrayOutputStream();
                for (int i = 0; i < segment.length(); i++) {
                    char c = segment.charAt(i);
                    if (c == '%') {
                        utf8.write(Integer.parseInt(segment.substring(i + 1, i + 3), 16));
                        i += 2;
                    } else {
                        if (utf8.size() > 0) {
                            buf.append(new String(utf8.toByteArray(), StandardCharsets.UTF_8));
                            utf8.reset();
                        }

                        buf.append(c);
                    }
                }
                if (utf8.size() > 0) {
                    buf.append(new String(utf8.toByteArray(), StandardCharsets.UTF_8));
                    utf8.reset();
                }
                segment = buf.toString();
                s.set(segment);
                encodedDotSegment |= (".".equals(segment) || "..".equals(segment));
            }
        }

        // Remove Empty Segments
        segments.removeIf(s1 -> s1.length() == 0);

        // Remove dot-segments
        int count = 0;
        for (ListIterator<String> s = segments.listIterator(); s.hasNext();) {
            String segment = s.next();

            if (segment.equals(".")) {
                s.remove();
            } else if (segment.equals("..")) {
                if (count > 0) {
                    s.remove();
                    String previous = s.previous();
                    s.remove();
                    count--;
                    emptySegmentBeforeDotDot |= previous.length() == 0;
                }
            } else {
                count++;
            }
        }

        // Concatenate segments
        StringBuilder buf = new StringBuilder();

        for (ListIterator<String> s = segments.listIterator(); s.hasNext();) {
            String segment = s.next();
            if (s.hasPrevious() || startsWithSlash)
                buf.append("/");
            buf.append(segment);
        }
        if (endsWithSlash)
            buf.append("/");
        path = buf.toString();

        // Rejecting Suspicious Sequences
        String reject = null;
        // Any path not starting with the `"/"` character
        if (!startsWithSlash)
            reject = "must start with /";
        // Any path starting with an initial segment of `".."`
        else if (segments.get(0).equals(".."))
            reject = "leading dot-dot-segment";
        // The encoded `"/"` character
        else if (uriPath.contains("%2f") || uriPath.contains("%2F"))
            reject = "encoded /";
        // Any `"."` or `".."` segment that had a path parameter
        else if (dotSegmentWithParam)
            reject = "dot segment with parameter";
        // Any `"."` or `".."` segment with any encoded characters
        else if (encodedDotSegment)
            reject = "encoded dot segment";
        // Any `".."` segment preceded by an empty segment
        else if (emptySegmentBeforeDotDot)
            reject = "empty segment before dot dot";
        // The `"\"` character encoded or not.
        else if (path.contains("\\"))
            reject = "backslash character";
        // Any control characters either encoded or not.
        else {
            for (char c : path.toCharArray()) {
                if (c < 0x20 || c == 0x7f) {
                    reject = "control character";
                    break;
                }
            }
        }

        System.err.printf("| `%s` | `%s` | %s %n",
                uriPath,
                path,
                reject == null ? "" : ("400 " + reject));

        return path;
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/public/file.txt",
            "/public//file.txt",
            "/public/;/file.txt",
            "/PUBLIC/file.txt",
            "/public%2Ffile.txt",
            "/public%5Cfile.txt",
            "/public%00/file.txt",
            "/public/./file.txt",
            "/public/.;/file.txt",
            "/public/%2e/file.txt",
            "/public/%2e;/file.txt",
            "/../docroot/public/file.txt",
            "/public/dir/../file.txt",
            "/public//../file.txt",
            "/public/;param/../file.txt",
            "/public/dir/..;/file.txt",
            "/public/dir/%2e%2E/file.txt",
            "/public/dir/.%2e/file.txt",
            "/public/dir/%2E./file.txt",
            "/public/dir/%2e%2e;/file.txt",
            "/WEB-INF/web.xml",
            "/web-inf/web.xml",
            "/WEB-IN~1.DIR/web.xml",
            "/WEB-INF;/web.xml",
            "/WEB-INF%2Fweb.xml",
            "/WEB-INF%5Cweb.xml",
            "/WEB-INF%00/web.xml",
            "/WEB-INF/./web.xml",
            "/public/../WEB-INF/web.xml",
            "/public/..;/WEB-INF/web.xml",
            "/public/%2e%2e/WEB-INF/web.xml",
            "/public/%2e%2e;/WEB-INF/web.xml",
            "/secret/private.xml",
            "/SeCreT/private.xml",
            "/SECRET~1.DIR/private.xml",
            "/secret;/private.xml",
            "/secret%2Fprivate.xml",
            "/secret%5Cprivate.xml",
            "/secret%00/private.xml",
            "/./secret/private.xml",
            "/.;/secret/private.xml",
            "/%2e/secret/private.xml",
            "/%2e;/secret/private.xml",
            "/public/../secret/private.xml",
            "/public/..;/secret/private.xml",
            "/public/%2e%2e/secret/private.xml",
            "/public/%2e%2e;/secret/private.xml",
            "/dispatch/public/file.txt",
            "/dispatch/public%2Ffile.txt",
            "/dispatch/public%5Cfile.txt",
            "/dispatch/public%252Ffile.txt",
            "/dispatch/WEB-INF/web.xml",
            "/dispatch/secret/private.xml",
            "/dispatch/%2E%2E/%2E%2E/etc/password",
    })

    public void testCanonicalUriPath(String path) {
        canonicalUriPath(path);
    }
}

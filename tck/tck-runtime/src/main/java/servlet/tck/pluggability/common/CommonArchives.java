package servlet.tck.pluggability.common;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import java.util.Arrays;
import java.util.List;

public class CommonArchives {
    private CommonArchives() {
        // nothing
    }

    public static List<JavaArchive> getCommonWebFragmentArchives() {
        JavaArchive javaArchive1 = ShrinkWrap.create(JavaArchive.class, "fragment-1.jar")
                .addClasses(TestServlet1.class, RequestListener1.class)
                .addAsResource("servlet/tck/pluggability/common/web-fragment_1.xml",
                        "META-INF/web-fragment.xml");
        JavaArchive javaArchive2 = ShrinkWrap.create(JavaArchive.class, "fragment-2.jar")
                .addClasses(RequestListener2.class)
                .addAsResource("servlet/tck/pluggability/common/web-fragment_2.xml",
                        "META-INF/web-fragment.xml");
        JavaArchive javaArchive3 = ShrinkWrap.create(JavaArchive.class, "fragment-3.jar")
                .addClasses(RequestListener3.class)
                .addAsResource("servlet/tck/pluggability/common/web-fragment_3.xml",
                        "META-INF/web-fragment.xml");
        JavaArchive javaArchive4 = ShrinkWrap.create(JavaArchive.class, "fragment-4.jar")
                .addClasses(RequestListener4.class)
                .addAsResource("servlet/tck/pluggability/common/web-fragment_4.xml",
                        "META-INF/web-fragment.xml");
        JavaArchive javaArchive5 = ShrinkWrap.create(JavaArchive.class, "fragment-5.jar")
                .addClasses(RequestListener5.class)
                .addAsResource("servlet/tck/pluggability/common/web-fragment_5.xml",
                        "META-INF/web-fragment.xml");

        return Arrays.asList(javaArchive1, javaArchive2, javaArchive3, javaArchive4, javaArchive5);
    }

}

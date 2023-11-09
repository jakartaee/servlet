package servlet.tck.common.servlets;

import servlet.tck.api.common.sharedfiles.HSessionAttributeListener;
import servlet.tck.api.common.sharedfiles.HSessionListener;
import servlet.tck.common.response.HttpResponseTestServlet;
import servlet.tck.common.response.ResponseTestServlet;
import servlet.tck.common.response.ResponseTests;
import servlet.tck.common.util.Data;
import servlet.tck.common.util.ServletTestUtil;
import servlet.tck.common.util.StaticLog;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import java.util.ArrayList;
import java.util.List;

public final class CommonServlets {

    private static final CommonServlets INSTANCE = new CommonServlets();

    private final JavaArchive[] javaArchives;

    private CommonServlets() {
        List<JavaArchive> archives = new ArrayList<>();
//
//        File[] files = Maven.configureResolver().workOffline().loadPomFromFile("pom.xml")
//                .resolve("org.slf4j:slf4j-simple")
//                .withTransitivity()
//                .asFile();
//        List<JavaArchive> slf4jJars =
//                Arrays.stream(files).map(file -> ShrinkWrap.createFromZipFile(JavaArchive.class, file))
//                        .collect(Collectors.toList());
//
//        //archives.addAll(slf4jJars);

        archives.add(ShrinkWrap.create(JavaArchive.class, "common-servlets.jar")
                .addClasses(GenericCheckTestResultServlet.class, GenericTCKServlet.class, RequestTestServlet.class,
                        HttpCheckTestResultServlet.class, HttpRequestTestServlet.class, RequestTests.class,
                        HttpTCKServlet.class, Data.class, StaticLog.class, ServletTestUtil.class,
                        ResponseTests.class, ResponseTestServlet.class, HttpResponseTestServlet.class,
                        HSessionListener.class, HSessionAttributeListener.class));

        javaArchives = archives.toArray(new JavaArchive[0]);
    }

    public static JavaArchive[] getCommonServletsArchive() {
        return INSTANCE.javaArchives;
    }

}

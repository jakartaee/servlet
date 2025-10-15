/*
 * Copyright (c) 2025 Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
package servlet.tck.spec.welcomefiles;

import servlet.tck.common.client.AbstractTckTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class WelcomeFilesLegacyTests extends AbstractTckTest {

    /**
     * Deployment for the test.
     *
     * @return The web archive to test.
     *
     * @throws Exception If an error occurs creating the archive.
     */
    @Deployment(testable = false)
    public static WebArchive getTestArchive() throws Exception {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "servlet_spec_welcomefiles_legacy_web.war")
                .addClasses(IndexServletDo.class)
                .setWebXML(WelcomeFilesLegacyTests.class.getResource("servlet_spec_welcomefiles_legacy_web.xml"));
        Arrays.asList("legacy/index.html")
                .forEach(s -> webArchive.addAsWebResource("spec/welcomefiles/" +s, s));
        return webArchive;
    }


    /*
     * Test should trigger a 404 since default.jsp is a valid welcome file but does not exist.
     */
    @Test
    public void partialfoundLegacy() throws Exception {
        TEST_PROPS.get().setProperty(FOLLOW_REDIRECT, "follow_redirect");
        TEST_PROPS.get().setProperty(STATUS_CODE, NOT_FOUND);
        TEST_PROPS.get().setProperty(SEARCH_STRING, "<html");
        TEST_PROPS.get().setProperty(APITEST, "legacy");
        invoke();
    }
}

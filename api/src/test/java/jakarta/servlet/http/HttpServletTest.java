/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation.
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

package jakarta.servlet.http;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import jakarta.servlet.MockServletConfig;
import jakarta.servlet.MockServletOutputStream;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class HttpServletTest {
    public interface Handler {
        void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    }

    @ParameterizedTest
    @MethodSource("headTest")
    public void testLegacyHead(String test, Handler doGet, boolean expectedFlushed, long expectedContentLength)
            throws ServletException, IOException {
        HttpServlet servlet = new HttpServlet() {
            private static final long serialVersionUID = 20214996986006168L;

            @Override
            protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                doGet.handle(request, response);
            }
        };

        MockServletConfig servletConfig = new MockServletConfig();
        servletConfig.setInitParameter("jakarta.servlet.http.legacyDoHead", "true");
        servlet.init(servletConfig);

        MockHttpServletRequest request = new MockHttpServletRequest(servletConfig.getServletContext()) {
            @Override
            public String getMethod() {
                return "HEAD";
            }
        };

        AtomicBoolean committed = new AtomicBoolean();
        AtomicLong contentLength = new AtomicLong(-1);
        MockHttpServletResponse response = new MockHttpServletResponse() {
            @Override
            public void flushBuffer() throws IOException {
                committed.set(true);
            }

            @Override
            public boolean isCommitted() {
                return committed.get();
            }

            @Override
            public void setContentLengthLong(long len) {
                contentLength.set(len);
            }
        };

        servlet.service(request, response);
        MockServletOutputStream out = response.getMockServletOutputStream();
        String actual = out == null ? null : out.takeOutputAsString();

        // Check if the output should have already been flushed
        assertThat(test, committed.get(), is(expectedFlushed));
        assertThat(test, contentLength.get(), is(expectedContentLength));
        assertThat(test, actual, anyOf(is(""), nullValue()));
    }

    @ParameterizedTest
    @MethodSource("traceHeadersTest")
    public void testTraceHeaders(String testHeader, Handler doTrace)
            throws ServletException, IOException {
        HttpServlet servlet = new HttpServlet() {

            private static final long serialVersionUID = 20214996986006169L;

            @Override
            protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                doTrace.handle(request, response);
            }
        };
        MockServletConfig servletConfig = new MockServletConfig();
        MockHttpServletRequest request = new MockHttpServletRequest(servletConfig.getServletContext()) {
            @Override
            public String getMethod() {
                return "TRACE";
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                return Collections.enumeration(Arrays.asList(testHeader));
            }
        };

        MockHttpServletResponse response = new MockHttpServletResponse();
        servlet.service(request, response);
        MockServletOutputStream out = response.getMockServletOutputStream();
        assertNotNull(out);
        String actual = out.takeOutputAsString();
        assertNotNull(actual);
        assertThat(actual, !actual.contains(testHeader));
    }

    private static Stream<Arguments> traceHeadersTest() {
        return Stream.of(
                Arguments.of("Authorization",
                        (Handler) (request, response) -> {
                        }),
                Arguments.of("Cookie",
                        (Handler) (request, response) -> {
                        }),
                Arguments.of("X-Forwarded-Ip",
                        (Handler) (request, response) -> {
                        }),
                Arguments.of("Forwarded",
                        (Handler) (request, response) -> {
                        }),
                Arguments.of("Proxy-Authorization",
                        (Handler) (request, response) -> {
                        }));
    }

    private static Stream<Arguments> headTest() {
        return Stream.of(
                Arguments.of("Nothing output",
                        (Handler) (request, response) -> {
                        }, false, 0),

                Arguments.of("Output smaller than buffer",
                        (Handler) (request, response) -> {
                            response.setBufferSize(2048);
                            response.getOutputStream().print("Hello World");
                        }, false, 11),

                Arguments.of("Write smaller than buffer",
                        (Handler) (request, response) -> {
                            response.setBufferSize(2048);
                            response.getWriter().print("Hello World");
                        }, false, 11),

                Arguments.of("Output bigger than buffer",
                        (Handler) (request, response) -> {
                            response.setBufferSize(5);
                            response.getOutputStream().print("Hello World");
                        },
                        false, // this is a known deficiency: a GET would commit the response
                        11 // this is a known deficiency: a GET would not know the content-length
                ),

                Arguments.of("Write bigger than buffer",
                        (Handler) (request, response) -> {
                            response.setBufferSize(5);
                            response.getWriter().print("Hello World");
                        },
                        false, // this is a known deficiency: a GET would commit the response
                        11 // this is a known deficiency: a GET would not know the content-length
                ),

                Arguments.of("Outputs with Content-Length smaller than buffer",
                        (Handler) (request, response) -> {
                            response.setBufferSize(1024);
                            response.setContentLength(11);
                            response.getOutputStream().print("Hello World");
                        },
                        false, // this is a known deficiency: a GET would commit the response
                        11),

                Arguments.of("Write with Content-Length smaller than buffer",
                        (Handler) (request, response) -> {
                            response.setBufferSize(1024);
                            response.setContentLength(11);
                            response.getWriter().print("Hello World");
                        },
                        false, // this is a known deficiency: a GET would commit the response
                        11),

                Arguments.of("Output with resetBuffer",
                        (Handler) (request, response) -> {
                            response.setBufferSize(2048);
                            response.getOutputStream().print("THIS IS WRONG");
                            response.resetBuffer();
                            response.getOutputStream().print("Hello World");
                        }, false, 11),

                Arguments.of("Write with resetBuffer",
                        (Handler) (request, response) -> {
                            response.setBufferSize(2048);
                            response.getWriter().print("THIS IS WRONG");
                            response.resetBuffer();
                            response.getWriter().print("Hello World");
                        },
                        false,
                        11),

                Arguments.of("Output with reset",
                        (Handler) (request, response) -> {
                            response.setBufferSize(2048);
                            response.getWriter().print("THIS IS WRONG");
                            response.reset();
                            response.getOutputStream().print("Hello World");
                        }, false, 11),

                Arguments.of("Write with reset",
                        (Handler) (request, response) -> {
                            response.setBufferSize(2048);
                            response.getOutputStream().print("THIS IS WRONG");
                            response.reset();
                            response.getWriter().print("Hello World");
                        }, false, 11)

        );
    }

    @Test
    public void testContainerHead()
            throws ServletException, IOException {
        HttpServlet servlet = new HttpServlet() {
            private static final long serialVersionUID = -7111162937549196282L;

            @Override
            protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                response.setBufferSize(2048);
                response.getOutputStream().print("Hello World");
            }
        };

        ServletConfig servletConfig = new MockServletConfig();
        servlet.init(servletConfig);

        MockHttpServletRequest request = new MockHttpServletRequest(servletConfig.getServletContext()) {
            @Override
            public String getMethod() {
                return "HEAD";
            }
        };

        MockHttpServletResponse response = new MockHttpServletResponse();

        servlet.service(request, response);
        MockServletOutputStream out = response.getMockServletOutputStream();
        String actual = out == null ? null : out.takeOutputAsString();

        // Check output makes it to container (which should then consume it)
        assertThat(actual, is("Hello World"));
    }
}

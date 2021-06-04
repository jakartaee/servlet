/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates and others.
 * All rights reserved.
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jakarta.servlet.http;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import jakarta.servlet.MockServletConfig;
import jakarta.servlet.MockServletOutputStream;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import java.io.IOException;
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

    public static Stream<Arguments> headTest() {
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

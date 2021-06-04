package jakarta.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class MockServletResponse implements ServletResponse {
    private ServletOutputStream servletOutputStream;
    private PrintWriter printWriter;
    private int bufferSize = 1024;

    @Override
    public String getCharacterEncoding() {
        return StandardCharsets.ISO_8859_1.name();
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (printWriter != null)
            throw new IllegalStateException();
        if (servletOutputStream == null)
            servletOutputStream = new MockServletOutputStream();
        return servletOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (printWriter != null)
            return printWriter;
        if (servletOutputStream != null)
            throw new IllegalStateException();
        printWriter = new PrintWriter(getOutputStream());
        return printWriter;
    }

    @Override
    public void setCharacterEncoding(String charset) {

    }

    @Override
    public void setContentLength(int len) {
        setContentLengthLong((long) len);
    }

    @Override
    public void setContentLengthLong(long len) {

    }

    @Override
    public void setContentType(String type) {

    }

    @Override
    public void setBufferSize(int size) {
        bufferSize = size;
    }

    @Override
    public int getBufferSize() {
        return bufferSize;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {
        if (servletOutputStream instanceof MockServletOutputStream)
            ((MockServletOutputStream) servletOutputStream).reset();
    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {
        printWriter = null;
        servletOutputStream = null;
    }

    @Override
    public void setLocale(Locale loc) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    public MockServletOutputStream getMockServletOutputStream() {
        if (servletOutputStream instanceof MockServletOutputStream)
            return (MockServletOutputStream) servletOutputStream;
        return null;
    }
}

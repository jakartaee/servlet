/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation.
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

package ee.jakarta.servlet;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        setContentLengthLong(len);
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

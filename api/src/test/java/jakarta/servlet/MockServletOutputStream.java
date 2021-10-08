package jakarta.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MockServletOutputStream extends ServletOutputStream {
    private final OutputStream out;

    public MockServletOutputStream() {
        this(null);
    }

    public MockServletOutputStream(OutputStream out) {
        this.out = out == null ? new ByteArrayOutputStream() : out;
    }

    @Override
    public void close() throws IOException {
        out.close();
    }

    @Override
    public void flush() throws IOException {
        out.flush();
    }

    @Override
    public boolean isReady() {
        return false;
    }

    public void reset() {
        if (out instanceof ByteArrayOutputStream)
            ((ByteArrayOutputStream) out).reset();
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void write(byte[] b) throws IOException {
        out.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    public String takeOutputAsString() {
        return takeOutputAsString(StandardCharsets.ISO_8859_1);
    }

    public String takeOutputAsString(Charset charset) {
        byte[] bytes = takeOutput();
        return bytes == null ? null : new String(bytes, charset);
    }

    public byte[] takeOutput() {
        if (out instanceof ByteArrayOutputStream) {
            ByteArrayOutputStream bout = (ByteArrayOutputStream) out;
            byte[] bytes = bout.toByteArray();
            bout.reset();
            return bytes;
        }
        return null;
    }
}

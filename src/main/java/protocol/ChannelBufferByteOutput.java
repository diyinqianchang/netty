package protocol;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteOutput;

import java.io.IOException;

/**
 * @author zhangguolin
 * @description desc
 * @date 2020-12-04 14:45
 */
public class ChannelBufferByteOutput implements ByteOutput {

    private final ByteBuf buf;

    public ChannelBufferByteOutput(ByteBuf buf) {
        this.buf = buf;
    }

    @Override
    public void write(int i) throws IOException {
        buf.writeByte(i);
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        buf.writeBytes(bytes);
    }

    @Override
    public void write(byte[] bytes, int i, int i1) throws IOException {
        buf.writeBytes(bytes,i,i1);
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void flush() throws IOException {

    }

    public ByteBuf getBuf() {
        return buf;
    }
}

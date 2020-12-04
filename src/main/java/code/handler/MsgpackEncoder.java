package code.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * @author zhangguolin
 * @description desc
 * @date 2020-12-02 14:56
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {

        MessagePack msgPack = new MessagePack();
        byte[] bytes = msgPack.write(o);
        byteBuf.writeBytes(bytes);

    }
}

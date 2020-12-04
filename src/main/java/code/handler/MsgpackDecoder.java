package code.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @author zhangguolin
 * @description desc
 * @date 2020-12-02 14:58
 */
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        final byte[] bytes;
        final int length = byteBuf.readableBytes();
        bytes = new byte[length];
        byteBuf.getBytes(byteBuf.readerIndex(),bytes,0,length);
        MessagePack msgPack = new MessagePack();
        list.add(msgPack.read(bytes));

    }
}

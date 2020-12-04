package code;

import code.handler.EchoClientHandler;
import code.handler.EchoClientHandler2;
import code.handler.MsgpackDecoder;
import code.handler.MsgpackEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @author zhangguolin
 * @description desc
 * @date 2020-12-02 15:08
 */
public class EchoClient2 {

//    private final String host;

//    private final int port;

//    private final int sendNum;

//    public EchoClient2(String host, int port, int sendNum) {
//        this.host = host;
//        this.port = port;
//        this.sendNum = sendNum;
//    }

    public void connection(int port,String host,int sendNum) throws Exception{

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,3000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            socketChannel.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535,0,
                                    2,0,2));
                            socketChannel.pipeline().addLast("msgpack译码器",new MsgpackDecoder());
                            socketChannel.pipeline().addLast("frameEncoder",new LengthFieldPrepender(2));
                            socketChannel.pipeline().addLast("msgpack编码器", new MsgpackEncoder());
                            socketChannel.pipeline().addLast(new EchoClientHandler2(sendNum));

                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }

    }


    public static void main(String[] args) throws Exception {

        int port = 28080;
        new EchoClient2().connection(port,"127.0.0.1",5);
//        echoClient2.run();

    }

}

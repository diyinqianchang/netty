package http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author zhangguolin
 * @description desc
 * @date 2020-12-03 18:02
 */
public class HttpFileServer {

    private static final String DEFAULT_URL = "";

    public void run(final int port,final String url) throws Exception{

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("http-decoder",new HttpRequestDecoder());
                            pipeline.addLast("http-aggregator",new HttpObjectAggregator(65536));
                            pipeline.addLast("http-encoder",new HttpResponseEncoder());
                            pipeline.addLast("http-chunked",new ChunkedWriteHandler());
                            pipeline.addLast("fileServerHandler",new HttpFileServerHandler(url));
                        }
                    });
            System.out.println("http://127.0.0.1:"+port+url);
            ChannelFuture future = bootstrap.bind("127.0.0.1",port).sync();
            future.channel().closeFuture().sync();

        }finally {

            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {

        int port = 28080;

        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String url = DEFAULT_URL;
        if (args.length > 1)
        {
            url = args[1];
        }
        new HttpFileServer().run(port, url);
    }

}

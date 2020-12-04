package code.handler;

import code.model.UserInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author zhangguolin
 * @description desc
 * @date 2020-12-02 15:13
 */
public class EchoClientHandler2 extends ChannelInboundHandlerAdapter {


    private final  int sendNumber;

    private int count;

    public EchoClientHandler2(int sendNumber) {
        this.sendNumber = sendNumber;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("EchoClientHandler2-----channelActive : "+sendNumber);
        UserInfo[] infos = userInfos();

        for (UserInfo userInfo:infos) {
            System.out.println(userInfo);
//            ctx.write(userInfo);
            ctx.writeAndFlush(userInfo);
        }
//        ctx.flush();
    }

    private UserInfo[] userInfos(){

        UserInfo[] userInfos = new UserInfo[sendNumber];
        UserInfo userInfo = null;
        for (int i = 0; i < sendNumber; i++) {
            userInfo = new UserInfo();
            userInfo.setAge(i);
            userInfo.setName("ABCDEFG----->"+i);
            userInfos[i] = userInfo;

        }
        return userInfos;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("这是客户端接收的消息【  " + ++count + "  】时间:【" + msg + "】");

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        ctx.close();
    }
}

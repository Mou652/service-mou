package cn.mou.netty_chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty编程练习
 * 技多不压身,加油
 *
 * @author: mou
 * @date: 2019/10/12
 */
public class WebSocketNettyServer {

    public static void main(String[] args) {
        //创建主线程(boss线程池)
        NioEventLoopGroup mainGroup = new NioEventLoopGroup();
        //创建从线程池(worker线程池)
        NioEventLoopGroup subGroup = new NioEventLoopGroup();

        try {
            //创建服务器启动器
            ServerBootstrap server = new ServerBootstrap();

            //指定使用主线程和从线程
            server.group(mainGroup, subGroup)
                    //指定使用nio通道类型
                    .channel(NioServerSocketChannel.class)
                    //指定通道初始化器加载通道处理器
                    .childHandler(new WsServerInitializer());

            //绑定端口启动服务器
            //future是netty的回调消息
            ChannelFuture future = server.bind(9090).sync();
            //等待服务器关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //关闭线程池
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }
    }
}

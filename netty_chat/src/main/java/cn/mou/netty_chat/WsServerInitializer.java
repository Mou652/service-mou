package cn.mou.netty_chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * 通道初始化类
 *
 * @author: mou
 * @date: 2019/10/12
 */
public class WsServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 用于支持 Http 协议

        // websocket 基于 http 协议，需要有 http 的编解码器
        pipeline.addLast(new HttpServerCodec());
        // 对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 添加对 HTTP 请求和响应的聚合器:只要使用 Netty 进行 Http 编程都需要使用
        // 对 HttpMessage 进行聚合，聚合成 FullHttpRequest 或者 FullHttpResponse
        // 在 netty 编程中都会使用到 Handler
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));

        // 用于支持websocket

        //websocket服务器处理协议,用于指定给客户端连接的路由:/ws
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        // 本 handler 会帮你处理一些握手动作: handshaking(close, ping, pong) ping +pong = 心跳
        // 对于 websocket 来讲，都是以 frames 进行传输的，不同的数据类型对应的 frames 也不同
        //添加自定义handler
        pipeline.addLast(new ChatHandler());
    }
}

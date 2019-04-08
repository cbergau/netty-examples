package de.christianbergau.nettyhttpexamples.handlers;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

public class MyWebServer extends SimpleChannelInboundHandler<DefaultHttpMessage> {
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DefaultHttpMessage httpMessage) throws Exception {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer("Test".getBytes())
        );

        channelHandlerContext.writeAndFlush(response).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) {
                if (channelFuture.isSuccess()) {
                    channelFuture.channel().close();
                } else {
                    System.out.println(channelFuture.cause().getMessage());
                    channelFuture.channel().close();
                }
            }
        });
    }
}

package com.acongfly.studyjava.netty.nettyDemo01.server;

import org.jboss.netty.channel.*;

/**
 * @author shicongyang
 * @ClassName: HelloHandler
 * @Description: 消息接受处理
 * @date 2018年4月17日 下午8:04:09
 */
public class HelloHandler extends SimpleChannelHandler {

    /**
     * 接收到客户端的信息
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        String eString = (String) e.getMessage();
        System.out.println(eString);
        ctx.getChannel().write("hi");
        super.messageReceived(ctx, e);
    }

    /**
     * 异常捕获
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        System.out.println("exceptionCaught");
        super.exceptionCaught(ctx, e);
    }

    /**
     * 新连接
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelConnected");
        super.channelConnected(ctx, e);
    }

    /**
     * 必须是连接已经建立了，关闭通道的时候才会触发
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelDisconnected");
        super.channelDisconnected(ctx, e);
    }

    /**
     * channel关闭时候触发
     */
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelClosed");
        super.channelClosed(ctx, e);
    }


}

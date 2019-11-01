package com.acongfly.studyjava.netty.nettyDemo01.client;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

/**
 * @author shicongyang
 * @ClassName: Clinet
 * @Description: netty客户端
 * @date 2018年4月17日 下午9:51:44
 */
public class Clinet {

    public static void main(String[] args) {
        ClientBootstrap clientBootstrap = new ClientBootstrap();

        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService work = Executors.newCachedThreadPool();

        clientBootstrap.setFactory(new NioClientSocketChannelFactory(boss, work));

        // 管道工厂
        clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {

            @Override
            public ChannelPipeline getPipeline() throws Exception {

                ChannelPipeline channelPipeline = Channels.pipeline();
                channelPipeline.addLast("decoder", new StringDecoder());
                channelPipeline.addLast("encoder", new StringEncoder());
                channelPipeline.addLast("clientHandler", new ClientHandler());
                return channelPipeline;
            }
        });

        // 连接服务器
        ChannelFuture connect = clientBootstrap.connect(new InetSocketAddress("127.0.0.1", 8888));
        Channel channel = connect.getChannel();

        System.out.println("client start!!!");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入：");
            channel.write(scanner.next());
        }

    }

}

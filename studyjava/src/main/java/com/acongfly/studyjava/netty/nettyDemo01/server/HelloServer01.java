package com.acongfly.studyjava.netty.nettyDemo01.server;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author shicongyang
 * @ClassName: HelloServer01
 * @Description: netty服务端入门
 * @date 2018年4月17日 下午6:55:41
 */
public class HelloServer01 {

    public static void main(String[] args) {

        //服务类
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //boss为线程的监听端口，work线程负责数据读写
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService work = Executors.newCachedThreadPool();

        //设置niosocket工厂
        serverBootstrap.setFactory(new NioServerSocketChannelFactory(boss, work));

        //设置管道工厂
        serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {

            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new StringDecoder());
                pipeline.addLast("encoder", new StringEncoder());
                pipeline.addLast("", new HelloHandler());
                return pipeline;
            }
        });

        serverBootstrap.bind(new InetSocketAddress(8888));
        System.out.println("server start!!!");
    }


}

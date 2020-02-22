/*
 * Developed by DasDirt on 1/4/20, 12:33 AM.
 * Copyright (c) 2020, for Wrapper by DasDirt
 * All rights reserved.
 */

package de.dirty.server.netty;

import de.dirty.server.netty.packet.Packet;
import de.dirty.server.netty.packet.Protocol;
import de.dirty.server.netty.packet.handler.PacketDecoder;
import de.dirty.server.netty.packet.handler.PacketEncoder;
import de.dirty.server.netty.packet.handler.PacketHandler;
import de.dirty.server.netty.packet.handler.PacketPrepender;
import de.dirty.server.netty.packet.handler.PacketSplitter;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ServerBootstrap extends ChannelInitializer<Channel> {
  public static final boolean EPOLL = Epoll.isAvailable();
  private EventLoopGroup eventLoopGroup;
  private Channel channel;
  public static ServerBootstrap serverBootstrap;

  public ServerBootstrap(int port) {
    serverBootstrap = this;
    try {
      new Protocol();
      io.netty.bootstrap.ServerBootstrap serverBootstrap = new io.netty.bootstrap.ServerBootstrap();

      serverBootstrap.group(
          eventLoopGroup = EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup());
      serverBootstrap.channel(
          EPOLL ? EpollServerSocketChannel.class : NioServerSocketChannel.class);
      serverBootstrap.childHandler(this);
      serverBootstrap.bind(port).sync().channel().closeFuture().syncUninterruptibly();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void sendPacket(Channel channel, final Packet packet) {
    if (channel != null) {
      if (channel.isOpen() && channel.isWritable()) {
        if (channel.eventLoop().inEventLoop()) {
          channel
              .writeAndFlush(packet)
              .addListeners(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        } else {
          channel
              .eventLoop()
              .execute(
                  () ->
                      channel
                          .writeAndFlush(packet)
                          .addListeners(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE));
        }
      }
    }
  }

  public void disconnect() {
    eventLoopGroup.shutdownGracefully();
    // todo: close all channels
    Thread.currentThread().interrupt();
  }

  @Override
  protected void initChannel(Channel channel) throws Exception {
    channel
        .pipeline()
        .addLast("splitter", new PacketPrepender())
        .addLast("decoder", new PacketDecoder())
        .addLast("prepender", new PacketSplitter())
        .addLast("encoder", new PacketEncoder())
        .addLast(new PacketHandler());
    this.channel = channel;
  }
}

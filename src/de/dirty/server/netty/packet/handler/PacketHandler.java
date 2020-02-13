/*
 * Developed by DasDirt on 1/3/20, 12:33 AM.
 * Copyright (c) 2020, for Wrapper by DasDirt
 * All rights reserved.
 */

package de.dirty.server.netty.packet.handler;

import de.dirty.server.Main;
import de.dirty.server.netty.packet.Packet;
import de.dirty.server.netty.packet.packets.PacketStartConnection;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;

public class PacketHandler extends SimpleChannelInboundHandler<Object> {

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o)
      throws Exception {
    ((Packet) o).handle(this, channelHandlerContext.channel());
  }

  public void handle(PacketStartConnection packetStartConnection, Channel channel) {
    System.out.printf("New connection request from: %s%n", channel);
    String ip = ((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress();
    // handshake
    System.out.println("Sending handshake packet");
  }

  private void sendPacket(Channel channel, Packet packet) {
    Main.getMain().getServerBootstrap().sendPacket(channel, packet);
  }
}

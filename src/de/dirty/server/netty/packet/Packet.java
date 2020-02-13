/*
 * Developed by DasDirt on 1/4/20, 12:33 AM.
 * Copyright (c) 2020, for Wrapper by DasDirt
 * All rights reserved.
 */

package de.dirty.server.netty.packet;

import de.dirty.server.netty.packet.handler.PacketHandler;

import io.netty.channel.Channel;

public abstract class Packet {

    public abstract void read(PacketSerializer packetSerializer) throws Exception;

    public abstract void write(PacketSerializer packetSerializer) throws Exception;

    public abstract void handle(PacketHandler packetHandler, Channel channel) throws Exception;
}

/*
 * Developed by DasDirt on 1/12/20, 3:42 PM.
 * Copyright (c) 2020, for Wrapper by DasDirt
 * All rights reserved.
 */

package de.dirty.server.netty.packet.packets;

import de.dirty.server.netty.packet.Packet;
import de.dirty.server.netty.packet.PacketSerializer;
import de.dirty.server.netty.packet.handler.PacketHandler;
import io.netty.channel.Channel;

public class PacketStartConnection extends Packet {

    public PacketStartConnection() {
        /*
         * This constructor is needed
         */
    }

    @Override
    public void read(PacketSerializer packetSerializer) throws Exception {
    }

    @Override
    public void write(PacketSerializer packetSerializer) throws Exception {
    }

    @Override
    public void handle(PacketHandler packetHandler, Channel channel) throws Exception {
        packetHandler.handle(this, channel);
    }
}

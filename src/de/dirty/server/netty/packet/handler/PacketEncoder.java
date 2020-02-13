/*
 * Developed by DasDirt on 1/4/20, 12:33 AM.
 * Copyright (c) 2020, for Wrapper by DasDirt
 * All rights reserved.
 */

package de.dirty.server.netty.packet.handler;

import de.dirty.server.netty.packet.Packet;
import de.dirty.server.netty.packet.PacketSerializer;
import de.dirty.server.netty.packet.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        PacketSerializer packetSerializer = new PacketSerializer(byteBuf);
        packetSerializer.writeIntToBuffer(Protocol.getProtocol().getPacketId(packet));
        packet.write(packetSerializer);
    }

}

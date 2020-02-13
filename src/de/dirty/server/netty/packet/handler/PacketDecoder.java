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
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.IOException;
import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> objects) throws Exception {
        PacketSerializer packetSerializer = new PacketSerializer(byteBuf);
        if (packetSerializer.getByteBuf().readableBytes() >= 1) {
            int id = packetSerializer.readIntFromBuffer();
            Packet packet = Protocol.getProtocol().getPacket(id);

            packet.read(packetSerializer);

            if (packetSerializer.getByteBuf().readableBytes() > 0)
                throw new IOException("Packet  (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + packetSerializer.getByteBuf().readableBytes() + " bytes extra whilst reading packet " + packet);
            else
                objects.add(packet);

        }
    }

}

/*
 * Developed by DasDirt on 1/4/20, 12:33 AM.
 * Copyright (c) 2020, for Wrapper by DasDirt
 * All rights reserved.
 */

package de.dirty.server.netty.packet.handler;

import de.dirty.server.netty.packet.PacketSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketSplitter extends MessageToByteEncoder<ByteBuf> {

    protected void encode(ChannelHandlerContext ctx, ByteBuf buffer, ByteBuf byteBuf) {
        int i = buffer.readableBytes();
        int j = PacketSerializer.getIntSize(i);
        if (j > 3) {
            throw new IllegalArgumentException("unable to fit " + i + " into " + 3);
        } else {
            PacketSerializer packetSerializer = new PacketSerializer(byteBuf);
            packetSerializer.getByteBuf().ensureWritable(j + i);
            packetSerializer.writeIntToBuffer(i);
            packetSerializer.getByteBuf().writeBytes(buffer, buffer.readerIndex(), i);
        }
    }

}

/*
 * Developed by DasDirt on 1/4/20, 12:33 AM.
 * Copyright (c) 2020, for Wrapper by DasDirt
 * All rights reserved.
 */

package de.dirty.server.netty.packet.handler;

import de.dirty.server.netty.packet.PacketSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/*
 * @author DasDirt aka Sebastian W.
 * @project PacketServer
 * Fische sind geil!
 */
public class PacketPrepender extends ByteToMessageDecoder {

    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> objects) {
        buffer.markReaderIndex();
        byte[] abyte = new byte[3];
        for (int i = 0; i < abyte.length; ++i) {
            if (!buffer.isReadable()) {
                buffer.resetReaderIndex();
                return;
            }
            abyte[i] = buffer.readByte();
            if (abyte[i] >= 0) {
                PacketSerializer packetSerializer = new PacketSerializer(Unpooled.wrappedBuffer(abyte));
                try {
                    int j = packetSerializer.readIntFromBuffer();

                    if (buffer.readableBytes() >= j) {
                        objects.add(buffer.readBytes(j));
                        return;
                    }
                    buffer.resetReaderIndex();
                } finally {
                    packetSerializer.getByteBuf().release();
                }
                return;
            }
        }
        throw new RuntimeException("length wider than 21-bit");
    }

}
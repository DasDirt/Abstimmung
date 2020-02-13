/*
 * Developed by DasDirt on 1/4/20, 12:33 AM.
 * Copyright (c) 2020, for Wrapper by DasDirt
 * All rights reserved.
 */

package de.dirty.server.netty.packet;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public class PacketSerializer {

    private ByteBuf byteBuf;

    public PacketSerializer(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    public void writeString(String string) {
        byteBuf.writeInt(string.getBytes(StandardCharsets.UTF_8).length);
        byteBuf.writeBytes(string.getBytes(StandardCharsets.UTF_8));
    }

    public String readString() {
        byte[] aByte = new byte[byteBuf.readInt()];

        for (int i = 0; i < aByte.length; ++i) {
            aByte[i] = byteBuf.readByte();
        }

        return new String(aByte, StandardCharsets.UTF_8);
    }

    public static int getIntSize(int input) {
        for (int i = 1; i < 5; ++i) {
            if ((input & -1 << i * 7) == 0) {
                return i;
            }
        }
        return 5;
    }

    public void writeIntToBuffer(int input) {
        while ((input & -128) != 0) {
            byteBuf.writeByte(input & 127 | 128);
            input >>>= 7;
        }
        byteBuf.writeByte(input);
    }

    public int readIntFromBuffer() {
        int i = 0;
        int j = 0;
        while (true) {
            byte aByte = byteBuf.readByte();
            i |= (aByte & 127) << j++ * 7;
            if (j > 5) {
                throw new RuntimeException("int too big");
            }
            if ((aByte & 128) != 128) {
                break;
            }
        }
        return i;
    }


    public ByteBuf getByteBuf() {
        return byteBuf;
    }
}

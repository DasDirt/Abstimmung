/*
 * Developed by DasDirt on 1/4/20, 12:33 AM.
 * Copyright (c) 2020, for Wrapper by DasDirt
 * All rights reserved.
 */

package de.dirty.server.netty.packet;

import de.dirty.server.netty.packet.packets.PacketStartConnection;

import java.util.HashMap;
import java.util.Map;

public class Protocol {
    private static Protocol protocol;
    private Map<Integer, Class<? extends Packet>> packets = new HashMap<>();

    public Protocol() {
        protocol = this;
        register(1, PacketStartConnection.class);
    }

    private void register(int id, Class<? extends Packet> clazz) {
        try {
            packets.put(id, clazz.newInstance().getClass());
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Class " + clazz.getSimpleName() + " does not contain a default Constructor, this might break the game :/");
        }
    }

    public int getPacketId(Packet packet) {
        for (Map.Entry<Integer, Class<? extends Packet>> entry : packets.entrySet()) {
            Class<? extends Packet> clazz = entry.getValue();
            if (clazz.isInstance(packet)) {
                return entry.getKey();
            }
        }

        throw new RuntimeException("Packet " + packet + " is not registered.");
    }

    public Packet getPacket(int id) throws IllegalAccessException, InstantiationException {
        if (!packets.containsKey(id)) {
            throw new RuntimeException("Packet with id " + id + " is not registered.");
        } else {
            return packets.get(id).newInstance();
        }
    }


    public static Protocol getProtocol() {
        return protocol;
    }
}

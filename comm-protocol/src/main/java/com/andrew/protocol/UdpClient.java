package com.andrew.protocol;

import io.netty.buffer.Unpooled;
import io.netty.util.internal.StringUtil;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @author andrew
 * @since 2023/3/25
 */
public class UdpClient {
    public static void main(String[] args) throws Exception {
        int portToSend = 7015;
        DatagramSocket socket = new DatagramSocket(new InetSocketAddress(8088));
        socket.connect(new InetSocketAddress(portToSend));

        //   0c 00 7f 00 00 00 02 00 11 ff 00 9d
        DatagramPacket packet = new DatagramPacket(
                new byte[]{
                        (byte)0x0c,
                        (byte)0x00,
                        (byte)0x7f,
                        (byte)0x00,
                        (byte)0x00,
                        (byte)0x00,
                        (byte)0x02,
                        (byte)0x00,
                        (byte)0x11,
                        (byte)0xff,
                        (byte)0x00,
                        (byte)0x9d
                }, 12, new InetSocketAddress(portToSend)
        );
        for (int i = 0; i < 100000; i++) {
            socket.send(packet);
            Thread.sleep(3000L);
        }

    }
}

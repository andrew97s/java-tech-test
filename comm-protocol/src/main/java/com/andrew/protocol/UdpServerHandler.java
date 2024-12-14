package com.andrew.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author andrew
 * @since 2023/3/13
 */

@Slf4j
public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private final static String HEX = "0123456789abcdef";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) {
        ByteBuf byteBuf = packet.content();
        InetSocketAddress sender = packet.sender();

        // 获取可读取字节长度
        log.info("New msg(length:{}) arrived !", byteBuf.readableBytes());

        // 一次读取packet内所有字节进行
        byte[] receivedBytes = new byte[packet.content().writerIndex()];
        packet.content().readBytes(receivedBytes);


        System.out.println("服务端接收到消息：" + byteToHex(receivedBytes));

        ctx.writeAndFlush(new DatagramPacket(
                Unpooled.copiedBuffer(
                        new byte[]{
                                8,
                                // 主机编号
                                1,
                                // 设备编号
                                1,
                                // 以下三位为预留位 默认为0
                                0,
                                0,
                                0,
                                (byte) 129,
                                // 校验和
                                (byte) 229
                        }
                ),
                packet.sender()
                )
        );
    }

    public static String byteToHex(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuilder result = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            result.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
        }
        return result.toString();
    }
}

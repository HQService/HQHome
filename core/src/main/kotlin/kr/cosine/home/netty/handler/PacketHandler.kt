package kr.cosine.home.netty.handler

import kr.cosine.home.netty.packet.TeleportToHomePacket
import kr.hqservice.framework.netty.channel.ChannelWrapper

interface PacketHandler {

    fun initialize()

    fun onPacketReceived(packet: TeleportToHomePacket, wrapper: ChannelWrapper)
}
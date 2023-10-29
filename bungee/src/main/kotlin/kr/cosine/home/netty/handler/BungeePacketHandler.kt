package kr.cosine.home.netty.handler

import kr.cosine.home.netty.handler.PacketHandler
import kr.cosine.home.netty.packet.TeleportToHomePacket
import kr.cosine.home.netty.service.BungeeTeleportService
import kr.hqservice.framework.global.core.component.Bean
import kr.hqservice.framework.netty.api.NettyServer
import kr.hqservice.framework.netty.channel.ChannelWrapper

@Bean
class BungeePacketHandler(
    private val nettyServer: NettyServer,
    private val bungeeTeleportService: BungeeTeleportService
) : PacketHandler {

    override fun initialize() {
        nettyServer.registerOuterPacket(TeleportToHomePacket::class)
        nettyServer.registerInnerPacket(TeleportToHomePacket::class, this::onPacketReceived)
    }

    override fun onPacketReceived(packet: TeleportToHomePacket, wrapper: ChannelWrapper) {
        bungeeTeleportService.teleport(packet.uniqueId, packet.homeLocation)
    }
}
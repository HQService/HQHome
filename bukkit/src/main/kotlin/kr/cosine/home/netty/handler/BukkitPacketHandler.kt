package kr.cosine.home.netty.handler

import kr.cosine.home.netty.packet.TeleportToHomePacket
import kr.cosine.home.service.HomeSerivce
import kr.hqservice.framework.global.core.component.Bean
import kr.hqservice.framework.netty.api.NettyServer
import kr.hqservice.framework.netty.channel.ChannelWrapper
import org.bukkit.Location
import org.bukkit.Server

@Bean
class BukkitPacketHandler(
    private val nettyServer: NettyServer,
    private val server: Server,
    private val homeSerivce: HomeSerivce
) : PacketHandler {

    override fun initialize() {
        nettyServer.registerOuterPacket(TeleportToHomePacket::class)
        nettyServer.registerInnerPacket(TeleportToHomePacket::class, this::onPacketReceived)
    }

    override fun onPacketReceived(packet: TeleportToHomePacket, wrapper: ChannelWrapper) {
        val homeLocation = packet.homeLocation
        val world = server.getWorld(homeLocation.world) ?: return
        val location = Location(world, homeLocation.x, homeLocation.y, homeLocation.z, homeLocation.yaw, homeLocation.pitch)
        homeSerivce.teleport(packet.uniqueId, location)
    }
}
package kr.cosine.home.netty.service

import kr.cosine.home.location.HomeLocation
import kr.cosine.home.netty.packet.TeleportToHomePacket
import kr.hqservice.framework.global.core.component.Service
import kr.hqservice.framework.netty.api.PacketSender
import net.md_5.bungee.api.ProxyServer
import java.util.*

@Service
class BungeeTeleportService(
    private val proxyServer: ProxyServer,
    private val packetSender: PacketSender,
) {

    fun teleport(uniqueId: UUID, targetHomeLocation: HomeLocation) {
        val player = proxyServer.getPlayer(uniqueId) ?: return
        if (player.isConnected) {
            val targetChannel = targetHomeLocation.channel
            val targetChannelPort = targetChannel.getPort()

            val playerServerInfo = player.server.info
            val targetServerInfo = proxyServer.servers.values.firstOrNull { it.address.port == targetChannelPort } ?: return

            val teleportToHomePacket = TeleportToHomePacket(uniqueId, targetHomeLocation)
            packetSender.sendPacket(targetChannelPort, teleportToHomePacket)

            if (playerServerInfo != targetServerInfo) {
                player.connect(targetServerInfo)
            }
        }
    }
}
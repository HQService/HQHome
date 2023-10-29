package kr.cosine.home.service

import kotlinx.coroutines.*
import kr.cosine.home.database.dto.HomeDTO
import kr.cosine.home.database.repository.HomeRepository
import kr.cosine.home.location.impl.HomeLocationImpl
import kr.cosine.home.netty.packet.TeleportToHomePacket
import kr.cosine.home.registry.SettingRegistry
import kr.cosine.home.registry.SettingRegistry.Companion.prefix
import kr.hqservice.framework.bukkit.core.HQBukkitPlugin
import kr.hqservice.framework.bukkit.core.coroutine.extension.BukkitMain
import kr.hqservice.framework.global.core.component.Service
import kr.hqservice.framework.netty.api.NettyServer
import kr.hqservice.framework.netty.api.PacketSender
import org.bukkit.Location
import org.bukkit.Server
import org.bukkit.entity.Player
import java.util.UUID

@Service
class HomeSerivce(
    private val plugin: HQBukkitPlugin,
    private val server: Server,
    private val packetSender: PacketSender,
    private val nettyServer: NettyServer,
    private val homeRepository: HomeRepository,
    private val countDownService: CountDownService
) {

    suspend fun setHome(player: Player) {
        val playerUniqueId = player.uniqueId
        val port = nettyServer.getPlayer(playerUniqueId)?.getChannel()?.getPort() ?: run {
            player.sendMessage("$prefix 현재 채널의 포트를 불러오지 못했습니다.")
            return
        }
        val playerLocation = player.location
        val homeDTO = HomeDTO(
            port,
            player.world.name,
            playerLocation.x,
            playerLocation.y,
            playerLocation.z,
            playerLocation.yaw,
            playerLocation.pitch
        )
        if (homeRepository.existsByUniqueId(playerUniqueId)) {
            homeRepository.update(playerUniqueId, homeDTO)
        } else {
            homeRepository.create(playerUniqueId, homeDTO)
        }
        player.sendMessage("$prefix 현재 위치를 홈으로 설정하였습니다.")
    }

    suspend fun home(player: Player) {
        val playerUniqueId = player.uniqueId
        val homeDTO = homeRepository.findByUniqueId(playerUniqueId) ?: run {
            player.sendMessage("$prefix 설정된 홈이 없습니다.")
            return
        }
        val channel = nettyServer.getChannel(homeDTO.port) ?: run {
            player.sendMessage("$prefix 채널을 불러오지 못했습니다.")
            return
        }
        countDownService.countDown(player) {
            val homeLocation = HomeLocationImpl(
                channel,
                homeDTO.world,
                homeDTO.x,
                homeDTO.y,
                homeDTO.z,
                homeDTO.yaw,
                homeDTO.pitch
            )
            val teleportToHomePacket = TeleportToHomePacket(playerUniqueId, homeLocation)
            packetSender.sendPacketToProxy(teleportToHomePacket)
        }
    }

    fun teleport(uniqueId: UUID, location: Location) {
        plugin.launch {
            repeat(20) {
                val player = server.getPlayer(uniqueId)
                if (player != null) {
                    withContext(Dispatchers.BukkitMain) {
                        player.teleport(location)
                    }
                    return@launch
                }
                delay(250)
            }
        }
    }
}
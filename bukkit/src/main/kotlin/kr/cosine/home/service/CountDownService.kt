package kr.cosine.home.service

import kotlinx.coroutines.delay
import kr.cosine.home.enums.Announce
import kr.cosine.home.registry.AnnounceRegistry
import kr.cosine.home.registry.SettingRegistry
import kr.hqservice.framework.global.core.component.Service
import org.bukkit.entity.Player

@Service
class CountDownService(
    private val settingRegistry: SettingRegistry,
    private val announceRegistry: AnnounceRegistry
) {

    suspend fun countDown(player: Player, actionFunction: () -> Unit) {
        if (settingRegistry.isCountDownTeleportEnabled) {
            val playerStartLocation = player.location
            val startX = playerStartLocation.x
            val startY = playerStartLocation.y
            val startZ = playerStartLocation.z
            (settingRegistry.countDownTime downTo 1).forEach { time ->
                val playerLocation = player.location
                if (playerLocation.x !in (startX - 0.5)..(startX + 0.5) ||
                    playerLocation.y !in (startY - 0.5)..(startY + 0.5) ||
                    playerLocation.z !in (startZ - 0.5)..(startZ + 0.5)
                ) {
                    announceRegistry.getTeleportAnnounce(Announce.DETECT_MOVING).announce(player)
                    return
                }
                announceRegistry.getTeleportAnnounce(Announce.COUNT_DOWN).announce(player) {
                    it.replace("%time%", time.toString())
                }
                delay(1000)
            }
        }
        actionFunction()
    }
}
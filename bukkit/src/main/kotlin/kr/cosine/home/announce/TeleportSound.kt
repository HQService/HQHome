package kr.cosine.home.announce

import org.bukkit.entity.Player

data class TeleportSound(
    private val isEnabled: Boolean,
    private val sound: String,
    private val volume: Float,
    private val pitch: Float
) {

    fun playSound(player: Player) {
        if (isEnabled) {
            player.playSound(player.location, sound, volume, pitch)
        }
    }
}
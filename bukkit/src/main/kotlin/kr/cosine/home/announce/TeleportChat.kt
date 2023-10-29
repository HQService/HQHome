package kr.cosine.home.announce

import org.bukkit.entity.Player

data class TeleportChat(
    private val isEnabled: Boolean,
    private val isBroadcast: Boolean,
    private val message: String
) {

    fun sendMessage(player: Player, replaceFunction: (String) -> String = { it }) {
        if (isEnabled) {
            val replacedMessage = replaceFunction(message)
            if (isBroadcast) {
                player.server.broadcastMessage(replacedMessage)
            } else {
                player.sendMessage(replacedMessage)
            }
        }
    }
}
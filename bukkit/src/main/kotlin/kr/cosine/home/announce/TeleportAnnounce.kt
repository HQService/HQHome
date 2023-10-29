package kr.cosine.home.announce

import org.bukkit.entity.Player

data class TeleportAnnounce(
    val sound: TeleportSound,
    val chat: TeleportChat,
    val title: TeleportTitle
) {

    fun announce(player: Player, replaceFunction: (String) -> String = { it }) {
        sound.playSound(player)
        chat.sendMessage(player, replaceFunction)
        title.sendTitle(player, replaceFunction)
    }
}
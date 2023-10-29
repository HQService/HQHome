package kr.cosine.home.command

import kotlinx.coroutines.launch
import kr.hqservice.framework.bukkit.core.HQBukkitPlugin
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

abstract class AbstractHomeCommand(
    private val plugin: HQBukkitPlugin
) : CommandExecutor {

    private val debounces = ConcurrentHashMap.newKeySet<UUID>()

    abstract suspend fun execute(player: Player)

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§c콘솔에서 사용할 수 없는 명령어입니다.")
            return true
        }
        val playerUniqueId = sender.uniqueId
        if (debounces.contains(playerUniqueId)) return true
        debounces.add(playerUniqueId)
        plugin.launch {
            execute(sender)
        }.invokeOnCompletion {
            debounces.remove(playerUniqueId)
        }
        return true
    }
}
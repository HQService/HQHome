package kr.cosine.home.command

import kr.cosine.home.config.SettingConfig
import kr.cosine.home.registry.SettingRegistry.Companion.prefix
import kr.hqservice.framework.command.Command
import kr.hqservice.framework.command.CommandExecutor
import org.bukkit.entity.Player

@Command(label = "셋홈관리", isOp = true)
class AdminCommand(
    private val settingConfig: SettingConfig
) {

    @CommandExecutor("리로드", "config.yml을 리로드합니다.")
    fun reload(player: Player) {
        settingConfig.reload()
        player.sendMessage("$prefix config.yml을 리로드하였습니다.")
    }
}
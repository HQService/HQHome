package kr.cosine.home

import kr.cosine.home.command.impl.HomeCommand
import kr.cosine.home.command.impl.SetHomeCommand
import kr.cosine.home.config.SettingConfig
import kr.cosine.home.netty.handler.PacketHandler
import kr.hqservice.framework.bukkit.core.HQBukkitPlugin
import kr.hqservice.framework.global.core.component.Component
import kr.hqservice.framework.global.core.component.HQModule

@Component
class HQHomeModule(
    private val plugin: HQBukkitPlugin,
    private val settingConfig: SettingConfig,
    private val packetHandler: PacketHandler,
    private val setHomeCommand: SetHomeCommand,
    private val homeCommand: HomeCommand,
) : HQModule {

    override fun onEnable() {
        settingConfig.load()
        packetHandler.initialize()
        plugin.getCommand("셋홈")?.setExecutor(setHomeCommand)
        plugin.getCommand("홈")?.setExecutor(homeCommand)
    }
}
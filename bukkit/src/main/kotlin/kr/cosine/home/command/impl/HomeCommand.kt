package kr.cosine.home.command.impl

import kr.cosine.home.command.AbstractHomeCommand
import kr.cosine.home.service.HomeSerivce
import kr.hqservice.framework.bukkit.core.HQBukkitPlugin
import kr.hqservice.framework.global.core.component.Bean
import org.bukkit.entity.Player

@Bean
class HomeCommand(
    plugin: HQBukkitPlugin,
    private val homeSerivce: HomeSerivce
) : AbstractHomeCommand(plugin) {

    override suspend fun execute(player: Player) {
        homeSerivce.home(player)
    }
}
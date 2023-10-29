package kr.cosine.home.config

import kr.cosine.home.announce.TeleportAnnounce
import kr.cosine.home.announce.TeleportChat
import kr.cosine.home.announce.TeleportSound
import kr.cosine.home.announce.TeleportTitle
import kr.cosine.home.enums.Announce
import kr.cosine.home.registry.AnnounceRegistry
import kr.cosine.home.registry.SettingRegistry
import kr.cosine.home.registry.SettingRegistry.Companion.prefix
import kr.hqservice.framework.bukkit.core.extension.colorize
import kr.hqservice.framework.global.core.component.Bean
import kr.hqservice.framework.yaml.config.HQYamlConfiguration
import java.util.logging.Logger

@Bean
class SettingConfig(
    private val logger: Logger,
    private val config: HQYamlConfiguration,
    private val settingRegistry: SettingRegistry,
    private val announceRegistry: AnnounceRegistry
) {

    fun load() {
        loadSettingSection()
        loadAnnounceSection()
    }

    private fun loadSettingSection() {
        val settingSectionKey = "setting"
        config.getSection(settingSectionKey)?.apply {
            prefix = getString("prefix", "<g:f1f50a>[Home]</g:f59105>§f").colorize()
            getSection("count-down-teleport")?.apply {
                val isCountDownTeleportEnabled = getBoolean("enabled")
                val time = getInt("time")
                settingRegistry.apply {
                    setCountDownTeleportEnabled(isCountDownTeleportEnabled)
                    setCountDownTime(time)
                }
            }
        }
    }

    private fun loadAnnounceSection() {
        val announceSectionKey = "announce"
        config.getSection(announceSectionKey)?.apply {
            getKeys().forEach { announceText ->
                val announce = Announce.getAnnounce(announceText) ?: run {
                    logger.warning("$announceSectionKey 섹션에 ${announceText}은(는) 존재하지 않는 Announce입니다.")
                    return@forEach
                }
                getSection(announceText)?.apply {
                    val soundSectionKey = "sound"
                    val soundSection = getSection(soundSectionKey) ?: run {
                        logger.warning("$announceSectionKey.$announceText 섹션에 $soundSectionKey 섹션이 존재하지 않습니다.")
                        return
                    }
                    val chatSectionKey = "chat"
                    val chatSection = getSection(chatSectionKey) ?: run {
                        logger.warning("$announceSectionKey.$announceText 섹션에 $chatSectionKey 섹션이 존재하지 않습니다.")
                        return
                    }
                    val titleSectionKey = "title"
                    val titleSection = getSection(titleSectionKey) ?: run {
                        logger.warning("$announceSectionKey.$announceText 섹션에 $titleSectionKey 섹션이 존재하지 않습니다.")
                        return
                    }
                    val teleportSound = soundSection.let {
                        val isEnabled = it.getBoolean("enabled")
                        val sound = it.getString("name")
                        val volume = it.getDouble("volume").toFloat()
                        val pitch = it.getDouble("pitch").toFloat()
                        TeleportSound(isEnabled, sound, volume, pitch)
                    }
                    val teleportChat = chatSection.let {
                        val isEnabled = it.getBoolean("enabled")
                        val isBroadcast = it.getBoolean("broadcast")
                        val message = it.getString("message").applyPrefix()
                        TeleportChat(isEnabled, isBroadcast, message)
                    }
                    val teleportTitle = titleSection.let {
                        val isEnabled = it.getBoolean("enabled")
                        val title = it.getString("title").applyPrefix()
                        val subTitle = it.getString("sub-title").applyPrefix()
                        val fadeIn = it.getInt("fade-in")
                        val duration = it.getInt("duration")
                        val fadeOut = it.getInt("fade-out")
                        TeleportTitle(isEnabled, title, subTitle, fadeIn, duration, fadeOut)
                    }
                    val teleportAnnounce = TeleportAnnounce(teleportSound, teleportChat, teleportTitle)
                    announceRegistry.setTeleportAnnounce(announce, teleportAnnounce)
                }
            }
        }
    }

    private fun String.applyPrefix(): String {
        return colorize().replace("%prefix%", prefix)
    }

    fun reload() {
        config.reload()
        announceRegistry.clear()
        load()
    }
}
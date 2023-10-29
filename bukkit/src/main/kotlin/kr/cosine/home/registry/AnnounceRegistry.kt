package kr.cosine.home.registry

import kr.cosine.home.announce.TeleportAnnounce
import kr.cosine.home.enums.Announce
import kr.hqservice.framework.global.core.component.Bean

@Bean
class AnnounceRegistry {

    private val announceMap = mutableMapOf<Announce, TeleportAnnounce>()

    fun findTeleportAnnounce(announce: Announce): TeleportAnnounce? = announceMap[announce]

    fun getTeleportAnnounce(announce: Announce): TeleportAnnounce = findTeleportAnnounce(announce) ?: throw IllegalArgumentException()

    fun setTeleportAnnounce(announce: Announce, teleportAnnounce: TeleportAnnounce) {
        announceMap[announce] = teleportAnnounce
    }

    internal fun clear() {
        announceMap.clear()
    }
}
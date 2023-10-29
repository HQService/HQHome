package kr.cosine.home

import kr.cosine.home.netty.handler.PacketHandler
import kr.hqservice.framework.global.core.component.Component
import kr.hqservice.framework.global.core.component.HQModule

@Component
class HQHomeModule(
    private val packetHandler: PacketHandler
) : HQModule {

    override fun onEnable() {
        packetHandler.initialize()
    }
}
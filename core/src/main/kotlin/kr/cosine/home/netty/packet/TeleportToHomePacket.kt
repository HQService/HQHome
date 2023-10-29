package kr.cosine.home.netty.packet

import io.netty.buffer.ByteBuf
import kr.cosine.home.location.HomeLocation
import kr.cosine.home.location.impl.readHomeLocation
import kr.cosine.home.location.impl.writeHomeLocation
import kr.hqservice.framework.netty.packet.Packet
import kr.hqservice.framework.netty.packet.extension.readString
import kr.hqservice.framework.netty.packet.extension.readUUID
import kr.hqservice.framework.netty.packet.extension.writeString
import kr.hqservice.framework.netty.packet.extension.writeUUID
import java.util.UUID

data class TeleportToHomePacket(
    var uniqueId: UUID,
    var homeLocation: HomeLocation
) : Packet() {

    override fun read(buf: ByteBuf) {
        uniqueId = buf.readUUID()
        homeLocation = buf.readHomeLocation()
    }

    override fun write(buf: ByteBuf) {
        buf.writeUUID(uniqueId)
        buf.writeHomeLocation(homeLocation)
    }
}
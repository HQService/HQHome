package kr.cosine.home.location.impl

import io.netty.buffer.ByteBuf
import kr.cosine.home.location.HomeLocation
import kr.hqservice.framework.netty.api.NettyChannel
import kr.hqservice.framework.netty.packet.extension.readChannel
import kr.hqservice.framework.netty.packet.extension.readString
import kr.hqservice.framework.netty.packet.extension.writeChannel
import kr.hqservice.framework.netty.packet.extension.writeString

data class HomeLocationImpl(
    override val channel: NettyChannel,
    override val world: String,
    override val x: Double,
    override val y: Double,
    override val z: Double,
    override val yaw: Float,
    override val pitch: Float
) : HomeLocation

fun ByteBuf.writeHomeLocation(homeLocation: HomeLocation) {
    homeLocation.apply {
        writeChannel(channel)
        writeString(world)
        writeDouble(x)
        writeDouble(y)
        writeDouble(z)
        writeFloat(yaw)
        writeFloat(pitch)
    }
}

fun ByteBuf.readHomeLocation(): HomeLocation {
    val channel = readChannel()!!
    val world = readString()
    val x = readDouble()
    val y = readDouble()
    val z = readDouble()
    val yaw = readFloat()
    val pitch = readFloat()
    return HomeLocationImpl(channel, world, x, y, z, yaw, pitch)
}
package kr.cosine.home.database.entity

import kr.cosine.home.database.dto.HomeDTO
import kr.hqservice.framework.database.component.Table
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

@Table
object HomeTable : UUIDTable("hqhome_home", "unique_id") {
    val port = integer("port")
    val world = text("world")
    val x = double("x")
    val y = double("y")
    val z = double("z")
    val yaw = float("yaw")
    val pitch = float("pitch")
}

class HomeEntity(id: EntityID<UUID>) : UUIDEntity(id) {

    companion object : UUIDEntityClass<HomeEntity>(HomeTable)

    var port by HomeTable.port
    var world by HomeTable.world
    var x by HomeTable.x
    var y by HomeTable.y
    var z by HomeTable.z
    var yaw by HomeTable.yaw
    var pitch by HomeTable.pitch

    fun mapping(): HomeDTO = HomeDTO(port, world, x, y, z, yaw, pitch)
}
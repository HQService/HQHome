package kr.cosine.home.database.dto

data class HomeDTO(
    val port: Int,
    val world: String,
    val x: Double,
    val y: Double,
    val z: Double,
    val yaw: Float,
    val pitch: Float
)
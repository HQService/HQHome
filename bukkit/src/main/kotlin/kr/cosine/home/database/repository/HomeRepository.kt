package kr.cosine.home.database.repository

import kr.cosine.home.database.dto.HomeDTO
import kr.cosine.home.database.entity.HomeEntity
import kr.cosine.home.database.entity.HomeTable
import kr.hqservice.framework.database.extension.findByIdForUpdate
import kr.hqservice.framework.database.repository.Repository
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

@Repository
class HomeRepository {

    suspend fun create(uniqueId: UUID, homeDTO: HomeDTO) {
        newSuspendedTransaction {
            HomeEntity.new(uniqueId) {
                this.port = homeDTO.port
                this.world = homeDTO.world
                this.x = homeDTO.x
                this.y = homeDTO.y
                this.z = homeDTO.z
                this.yaw = homeDTO.yaw
                this.pitch = homeDTO.pitch
            }
        }
    }

    suspend fun existsByUniqueId(uniqueId: UUID): Boolean {
        return findByUniqueId(uniqueId) != null
    }

    suspend fun findByUniqueId(uniqueId: UUID): HomeDTO? {
        return newSuspendedTransaction {
            HomeEntity.find {
                HomeTable.id eq uniqueId
            }.firstOrNull()?.mapping()
        }
    }

    suspend fun update(uniqueId: UUID, homeDTO: HomeDTO) {
        return newSuspendedTransaction {
            HomeEntity.findByIdForUpdate(uniqueId)?.apply {
                this.port = homeDTO.port
                this.world = homeDTO.world
                this.x = homeDTO.x
                this.y = homeDTO.y
                this.z = homeDTO.z
                this.yaw = homeDTO.yaw
                this.pitch = homeDTO.pitch
            }
        }
    }
}
package ro.upt.ac.chiuitter.data.database

import ro.upt.ac.chiuitter.domain.ChiuitRepository
import ro.upt.ac.chiuitter.domain.Chiuit

class ChiuitDbStore(private val appDatabase: AppDatabase) : ChiuitRepository {

    override fun getAll(): List<Chiuit> {
        return appDatabase.chiuitDao().getAll().map { it.toDomainModel() }
    }

    override fun addChiuit(chiuit: Chiuit) {
        appDatabase.chiuitDao().insertChiuit(chiuit.toDbModel())
    }

    override fun removeChiuit(chiuit: Chiuit) {
        appDatabase.chiuitDao().deleteChiuit(chiuit.toDbModel())
    }


    private fun Chiuit.toDbModel() = ChiuitEntity(timestamp, description)

    private fun ChiuitEntity.toDomainModel() = Chiuit(timestamp, description)

}
package bohdan.varchenko.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import bohdan.varchenko.data.local.AppDatabase.Companion.TABLE_SEARCH_QUERY

@Entity(
    tableName = TABLE_SEARCH_QUERY
)
internal data class SearchQueryEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = ID) val id: Long = 0,
    @ColumnInfo(name = TEXT) val text: String,
    @ColumnInfo(name = ORDER_POSITION) val orderPosition: Int

) {
    companion object {
        const val ID = "${TABLE_SEARCH_QUERY}_id"
        const val TEXT = "${TABLE_SEARCH_QUERY}_text"
        const val ORDER_POSITION = "${TABLE_SEARCH_QUERY}_order_position"
    }
}
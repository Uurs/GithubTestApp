package bohdan.varchenko.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import bohdan.varchenko.data.local.AppDatabase.Companion.TABLE_REPOSITORIES

@Entity(
    tableName = TABLE_REPOSITORIES
)
internal data class RepositoryEntity(
    @PrimaryKey @ColumnInfo(name = ID) val id: Long,
    @ColumnInfo(name = NAME) val name: String,
    @ColumnInfo(name = DESCRIPTION) val description: String?,
    @ColumnInfo(name = HTML_URL) val htmlUrl: String,
    @ColumnInfo(name = OWNER_ID) val ownerId: Long,
    @ColumnInfo(name = OWNER_NAME) val ownerName: String,
    @ColumnInfo(name = OWNER_AVATAR_URL) val ownerAvatarUrl: String,
    @ColumnInfo(name = IS_VIEWED) val isViewed: Boolean,
    @ColumnInfo(name = STARS) val stars: Long
) {
    companion object {
        internal const val ID = "${TABLE_REPOSITORIES}_id"
        internal const val NAME = "${TABLE_REPOSITORIES}_name"
        internal const val DESCRIPTION = "${TABLE_REPOSITORIES}_description"
        internal const val HTML_URL = "${TABLE_REPOSITORIES}_html_url"
        internal const val OWNER_ID = "${TABLE_REPOSITORIES}_ownerId"
        internal const val OWNER_NAME = "${TABLE_REPOSITORIES}_ownerName"
        internal const val OWNER_AVATAR_URL = "${TABLE_REPOSITORIES}_ownerAvatarUrl"
        internal const val IS_VIEWED = "${TABLE_REPOSITORIES}_is_viewed"
        internal const val STARS = "${TABLE_REPOSITORIES}_stars"
    }
}
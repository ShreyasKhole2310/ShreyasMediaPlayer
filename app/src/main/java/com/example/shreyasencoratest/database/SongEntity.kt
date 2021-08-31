package com.example.shreyasencoratest.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shreyasencoratest.beanclasses.Entry
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "song")
@Parcelize
class SongEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "title") var strTitle: String?,
    @ColumnInfo(name = "author") var strAuthor: String?,
    @ColumnInfo(name = "imageUrl") var strImagURL: String?,
    @ColumnInfo(name = "audioLink") var strAudioLink: String?,
    @ColumnInfo(name = "content") var strContent: String?
) : Parcelable

//extension function to covert model to DTO and change image path
fun Entry.mapDto() = SongEntity(
    strTitle = "$title",strAuthor = artist,
    strImagURL = "$imageUrl",
    strAudioLink = "${link.audioLink}",
    strContent = content
)
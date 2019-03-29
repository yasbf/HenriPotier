package com.ahmedbenfadhel.henripotier.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "books")
data class Book(@PrimaryKey val isbn: String,
                @field:SerializedName("title") val title: String,
                @field:SerializedName("price") val price: Int,
                @field:SerializedName("cover") val cover: String,
                @field:SerializedName("synopsis") val synopsis: List<String>,
                @field:SerializedName("nbInBasket") var nbInBasket: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(isbn)
        parcel.writeString(title)
        parcel.writeInt(price)
        parcel.writeString(cover)
        parcel.writeStringList(synopsis)
        parcel.writeInt(nbInBasket)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}
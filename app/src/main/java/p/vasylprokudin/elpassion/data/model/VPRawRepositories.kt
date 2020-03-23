package p.vasylprokudin.elpassion.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VPRawRepositories(
    val total_count: Int,
    val items: ArrayList<VPRawItem>
) : Parcelable {
    @Parcelize
    data class VPRawItem(
        val id: Int,
        val name: String,
        val full_name: String,
        val description: String,
        val language: String,
        val watchers: Int,
        val owner: VPRawOwner
    ) : Parcelable {
        @Parcelize
        data class VPRawOwner(
            val login: String,
            val id: Int,
            val avatar_url: String
        ) : Parcelable
    }
}
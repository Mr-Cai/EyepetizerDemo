package open.movie.mvp.model.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class VideoBean(
        var feed: String?,
        var title: String?,
        var description: String?,
        var duration: Long?,
        var playUrl: String?,
        var category: String?,
        var blurred: String?,
        var collect: Int?,
        var share: Int?,
        var reply: Int?,
        var time: Long
) : Parcelable, Serializable
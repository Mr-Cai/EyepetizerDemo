package open.movie.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by lvruheng on 2017/7/6.
 */
class ImageLoadUtils {
    companion object {

        /*


          .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image_loading)
                    .error(R.drawable.ic_empty_picture)
                    .crossFade()



        *  .asBitmap()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image_loading)
                    .error(R.drawable.ic_empty_picture)
        *
        * */
        fun display(context: Context, imageView: ImageView?, url: String) {
            if (imageView == null) {
                throw IllegalArgumentException("argument error")
            }
            Glide.with(context).load(url)
                    .into(imageView)
        }

        fun displayHigh(context: Context, imageView: ImageView?, url: String) {
            if (imageView == null) {
                throw IllegalArgumentException("argument error")
            }

            Glide.with(context).load(url)
                    .into(imageView)
        }
    }

}
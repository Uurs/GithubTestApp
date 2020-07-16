package bohdan.varchenko.gittestproject.core.imageloader

import android.widget.ImageView
import androidx.annotation.DrawableRes

interface ImageLoader {

    fun loadImage(
        imageView: ImageView,
        imageUrl: String,
        @DrawableRes placeHolder: Int
    )
}
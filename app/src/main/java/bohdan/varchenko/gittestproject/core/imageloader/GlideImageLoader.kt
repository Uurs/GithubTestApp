package bohdan.varchenko.gittestproject.core.imageloader

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import javax.inject.Inject

class GlideImageLoader @Inject constructor(
    context: Context
): ImageLoader {
    private val glide = Glide.with(context)

    override fun loadImage(
        imageView: ImageView,
        imageUrl: String,
        placeHolder: Int
    ) {
        glide.load(imageUrl)
            .placeholder(placeHolder)
            .into(imageView)
    }
}
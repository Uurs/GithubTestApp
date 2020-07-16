package bohdan.varchenko.gittestproject.di.modules

import bohdan.varchenko.gittestproject.core.imageloader.GlideImageLoader
import bohdan.varchenko.gittestproject.core.imageloader.ImageLoader
import dagger.Binds
import dagger.Module

@Module
interface ImageLoaderModule {

    @Binds
    fun bindsImageLoader(
        loader: GlideImageLoader
    ): ImageLoader
}
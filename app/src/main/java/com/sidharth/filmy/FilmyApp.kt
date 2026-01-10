package com.sidharth.filmy

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FilmyApp : Application() {
//    override fun newImageLoader(context: PlatformContext): ImageLoader {
//        return ImageLoader.Builder(context)
//            .crossfade(true)
//            .build()
//    }
}
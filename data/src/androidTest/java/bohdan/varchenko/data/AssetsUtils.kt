package bohdan.varchenko.data

import android.content.Context

fun readStringFromAssets(
    context: Context,
    assetName: String
): String = context.assets.open(assetName)
    .reader()
    .buffered()
    .lineSequence()
    .joinToString(" ")
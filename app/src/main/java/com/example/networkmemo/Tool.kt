package com.example.networkmemo

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.networkmemo.data.LinkCapsule
import org.jsoup.Jsoup
import java.util.regex.Pattern

@Stable
@Composable
fun pixelToDp(px: Double): Dp {
    val density = LocalDensity.current.density.toDouble()
    return (px / density).dp
}

fun isYoutubeUrl(link: String): Boolean {
    val pattern = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+"
    return !link.isEmpty() && link.matches(pattern.toRegex())
}
fun openYoutubeIntent(youtubeURI: String): Intent {
    return Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(youtubeURI)
        setPackage("com.google.android.youtube")
    }
}
fun openLinkIntent(link: String): Intent {
    Log.e("TAG", "")
    return Intent(Intent.ACTION_VIEW, Uri.parse(link))
}

fun isNaver(link: String): Boolean = link.startsWith("https://naver.me/")
fun linkParser(link: String): LinkCapsule {
    val result = LinkCapsule()
    if (link.isNotEmpty()) {
        var linkBumper = link

        /** NAVER APP LINK 전처리 */
        if(isNaver(link)) {
            // Link from NAVER app
            val doc = Jsoup.connect(link).get()
            val metaTags = doc.select("meta")
            for (metaTag in metaTags) {
                if (metaTag.attr("property") == "al:android:url") {
                    var content = metaTag.attr("content")
                    content = content.replace("%3A", ":")
                    content = content.replace("%2F", "/")
                    content = content.replace("%3D", "=")
                    content = content.replace("%3F", "?")
                    content = content.replace("%26", "&")
                    val regex = "\\?url=([^&]*)&"
                    val pattern = Pattern.compile(regex)
                    val matcher = pattern.matcher(content)
                    if (matcher.find()) {
                        linkBumper = matcher.group(1)
                    }
                }
            }
        }

        val doc = Jsoup.connect(linkBumper).get()
        val metaTags = doc.select("meta[property^=og:]")
        var title: String? = null
        var description: String? = null
        var imageUrl: String? = null
        for (metaTag in metaTags) {
            val property = metaTag.attr("property")
            val content = metaTag.attr("content")
            when (property) {
                "og:title" -> title = content
                "og:description" -> description = content
                "og:image" -> imageUrl = content
            }
        }
        result.title = title ?: ""
        result.description = description ?: ""
        result.imageUrl = imageUrl ?: ""
    }
    return result
}




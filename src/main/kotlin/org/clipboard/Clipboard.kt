package org.clipboard

import org.clipboard.impl.LinuxClipboard
import org.clipboard.impl.MacosClipboard
import org.clipboard.impl.WinClipboard

interface Clipboard {
    fun set(text: String)
    fun get(): String

    companion object {
        @JvmStatic
        fun getClipboard(): Clipboard {
            return when (System.getProperty("os.name")) {
                "Mac OS X" -> MacosClipboard
                "Linux" -> LinuxClipboard
                "Windows" -> WinClipboard
                else -> throw RuntimeException("Unsupported OS")
            }
        }
    }


}


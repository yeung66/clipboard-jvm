package org.yeungyeah.clipboard

import org.yeungyeah.clipboard.impl.LinuxClipboard
import org.yeungyeah.clipboard.impl.MacosClipboard
import org.yeungyeah.clipboard.impl.WinClipboard

/**
 * A simple clipboard accessing interface.
 */
interface Clipboard {

    /**
     * set content to clipboard
     * @param text content
     */
    fun set(text: String)

    /**
     * get content from clipboard
     * @return content
     */
    fun get(): String

    companion object {
        /**
         * get clipboard instance based on os
         * @return clipboard instance
         */
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


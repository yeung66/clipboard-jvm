package org.yeungyeah.clipboard

fun main() {
    val clipboard = Clipboard.getClipboard()
    println(clipboard.get())
    clipboard.set("Hello World")
    println(clipboard.get())
}
import org.clipboard.Clipboard

fun main() {
    val clipboard = Clipboard.getClipboard()
    println(clipboard.get())
    clipboard.set("Hello World")
    println(clipboard.get())
}
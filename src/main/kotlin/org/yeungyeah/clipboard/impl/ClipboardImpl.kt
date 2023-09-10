package org.yeungyeah.clipboard.impl

import org.yeungyeah.clipboard.Clipboard
import org.yeungyeah.clipboard.shell.Command
import java.nio.file.Paths

object MacosClipboard : Clipboard {
    private const val COPY_COMMAND = "pbcopy"
    private const val PASTE_COMMAND = "pbpaste"
    override fun set(text: String) {
        Command(COPY_COMMAND).inputContent(text).output()
    }

    override fun get(): String {
        return Command(PASTE_COMMAND).output()
    }
}

object LinuxClipboard : Clipboard {
    private const val COMMAND_NAME = "xsel"
    private const val ARGUMENT = "--clipboard"
    private const val COPY_ARGUMENT = "--input"
    private const val PASTE_ARGUMENT = "--output"

    private val command = Paths.get(ClassLoader.getSystemResource(COMMAND_NAME).toURI()).toString()

    override fun set(text: String) {
        Command(command, listOf(ARGUMENT, COPY_ARGUMENT)).inputContent(text).output()
    }

    override fun get(): String {
        return Command(command, listOf(ARGUMENT, PASTE_ARGUMENT)).output()
    }
}

object WinClipboard : Clipboard {
    private const val COMMAND_NAME = "clipboard_x86_64.exe"
    private const val COPY_ARGUMENT = "--copy"
    private const val PASTE_ARGUMENT = "--paste"

    private val command = Paths.get(ClassLoader.getSystemResource(COMMAND_NAME).toURI()).toString()

    override fun set(text: String) {
        Command(command).args(COPY_ARGUMENT).inputContent(text).output()
    }

    override fun get(): String {
        return Command(command).args(PASTE_ARGUMENT).output()
    }
}
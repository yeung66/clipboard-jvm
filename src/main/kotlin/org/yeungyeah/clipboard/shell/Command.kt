package org.yeungyeah.clipboard.shell

import java.io.File
import java.io.InputStream
import java.util.concurrent.TimeUnit

data class Command(val command: String, var arguments: List<String> = listOf()) {
    private var workingDirectory: File? = null
    private var redirectInput: Boolean = false
    private var inputContent: String? = null
    private var output: InputStream? = null
    private var error: InputStream? = null
    private var process: Process? = null

    fun args(vararg args: String): Command {
        arguments = arguments + args
        return this
    }

    fun inputContent(content: String): Command {
        redirectInput = true
        inputContent = content

        return this
    }

    fun run(): Command {
        if (process != null) {
            throw IllegalStateException("Command has already been run")
        }

        process = processBuilder
            .command(listOf(command) + arguments)
            .directory(workingDirectory)
            .start()

        output = process!!.inputStream
        error = process!!.errorStream

        if (redirectInput) {
            process!!.outputStream.bufferedWriter().use { inputContent?.let { it1 -> it.write(it1) } }
        }

        return this
    }

    fun wait(): Boolean? {
        return process?.waitFor(WAIT_MINUTES, TimeUnit.MINUTES)
    }

    fun output(): String {
        if (process == null) {
            run()
        }

        wait()

        val output = output?.bufferedReader()?.readText()
        val error = error?.bufferedReader()?.readText()
        if (!error.isNullOrEmpty()) {
            throw ShellRunException(process?.exitValue()?: 99, error.trim())
        }

        return output ?: ""
    }
}

const val WAIT_MINUTES = 1L
val processBuilder: ProcessBuilder = ProcessBuilder(listOf())
    .redirectInput(ProcessBuilder.Redirect.PIPE)
    .redirectOutput(ProcessBuilder.Redirect.PIPE)
    .redirectError(ProcessBuilder.Redirect.PIPE)
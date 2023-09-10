package org.yeungyeah.clipboard.shell

import java.io.File
import java.io.InputStream
import java.util.concurrent.TimeUnit

/**
 * A simple command runner.
 * @param command command to run
 * @param arguments arguments of command
 */
data class Command(val command: String, var arguments: List<String> = listOf()) {
    private var workingDirectory: File? = null
    private var redirectInput: Boolean = false
    private var inputContent: String? = null
    private var output: InputStream? = null
    private var error: InputStream? = null
    private var process: Process? = null

    /**
     * add args to command
     *
     * @param args args
     */
    fun args(vararg args: String): Command {
        arguments = arguments + args
        return this
    }

    /**
     * redirect input content to command
     * @param content input content
     */
    fun inputContent(content: String): Command {
        redirectInput = true
        inputContent = content

        return this
    }

    /**
     * run command
     */
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

    /**
     * wait for command to finish, until timeout
     */
    fun wait(): Boolean? {
        return process?.waitFor(WAIT_MINUTES, TimeUnit.MINUTES)
    }

    /**
     * get command output
     */
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
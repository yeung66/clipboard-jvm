package org.yeungyeah.clipboard.shell

data class ShellRunException(
    val exitCode: Int,
    val errorText: String,
) : RuntimeException(
    "Running shell command failed with code $exitCode and message: $errorText",
)
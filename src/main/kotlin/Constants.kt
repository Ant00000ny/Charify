package com.ant00000ny

import java.nio.file.Paths
import kotlin.math.pow
import kotlin.math.sqrt

val USER_HOME = System.getProperty("user.home")
    .let { Paths.get(it) }
    ?: throw Exception("user.home is null")


private const val ANSI_RESET = "\u001B[0m"

val colorMap = mapOf(
    Triple(0, 0, 0) to "\u001B[30m",
    Triple(255, 0, 0) to "\u001B[31m",
    Triple(0, 255, 0) to "\u001B[32m",
    Triple(255, 255, 0) to "\u001B[33m",
    Triple(0, 0, 255) to "\u001B[34m",
    Triple(255, 0, 255) to "\u001B[35m",
    Triple(0, 255, 255) to "\u001B[36m",
    Triple(255, 255, 255) to "\u001B[37m",
    // dark red
    Triple(128, 0, 0) to "\u001B[38;5;52m",
    // dark green
    Triple(0, 128, 0) to "\u001B[38;5;22m",
    // dark blue
    Triple(0, 0, 128) to "\u001B[38;5;18m",
    // dark yellow
    Triple(128, 128, 0) to "\u001B[38;5;58m",
    // dark magenta
    Triple(128, 0, 128) to "\u001B[38;5;90m",
    // dark cyan
    Triple(0, 128, 128) to "\u001B[38;5;30m",
    // dark gray
    Triple(128, 128, 128) to "\u001B[38;5;242m",
)

fun AsciiPixel.toConsoleChar(): String {
    val nearestConsoleColor = colorMap
        .map { (colorTriple, consoleColor) ->
            consoleColor to sqrt(
                (colorTriple.first - color.red).toDouble().pow(2.0) +
                        (colorTriple.second - color.green).toDouble().pow(2.0) +
                        (colorTriple.third - color.blue).toDouble().pow(2.0)
            )
        }
        .minBy { it.second }
        .first

    return "$nearestConsoleColor$char$ANSI_RESET"
}

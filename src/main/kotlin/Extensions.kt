import java.nio.file.Paths
import kotlin.math.pow
import kotlin.math.sqrt

operator fun Pair<Int, Int>.times(
    x: Double,
): Pair<Int, Int> {
    return Pair((first * x).toInt(), (second * x).toInt())
}

val USER_HOME = System.getProperty("user.home")
    .let { Paths.get(it) }
    ?: throw Exception("user.home is null")

val USER_DIR = System.getProperty("user.dir")
    .let { Paths.get(it) }
    ?: throw Exception("user.dir is null")

val asciiChars = "◙◘■▩●▦▣◚◛◕▨▧◉▤◐◒▮◍◑▼▪◤▬◗◭◖◈◎◮◊◫▰◄◯□▯▷▫▽◹△◁▸▭◅▵◌▱▹▿".reversed()


const val ANSI_RESET = "\u001B[0m"

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

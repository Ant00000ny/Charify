import java.nio.file.Paths
import java.time.Duration

fun main(
    args: Array<String>,
) {
    val videoFile = args.getOrElse(0) { USER_HOME.resolve("desktop").resolve("1.mp4").toAbsolutePath().toString() }
        .let { Paths.get(it) }
        .toAbsolutePath()
        .toFile()
        .also { require(it.exists()) { throw IllegalArgumentException("File does not exist") } }

    val frameInterval = args.getOrElse(1) { "6" }
        .toInt()
    val durationMillis = args.getOrElse(2) { "100" }
        .toLong()
    val size = args.getOrElse(3) { "50" }
        .toInt()

    AsciiConverter(
        videoFile,
        frameInterval,
        size
    ).printToWindow(Duration.ofMillis(durationMillis))
}

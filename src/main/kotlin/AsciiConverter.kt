import java.io.File
import java.time.Duration

class AsciiConverter(
    videoFile: File,
    frameInterval: Int,
    private val size: Int,
) {
    private val videoFrameExtractor: VideoFrameExtractor = VideoFrameExtractor(videoFile, frameInterval)

    fun printToConsole(
        interval: Duration,
    ) {
        while (videoFrameExtractor.hasNextFrame()) {
            val asciiString = videoFrameExtractor.nextFrame()
                .getAsciiString(size)
            println(asciiString)
            Thread.sleep(interval.toMillis())
        }
    }
}

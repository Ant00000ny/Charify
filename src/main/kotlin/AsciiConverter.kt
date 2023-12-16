import java.awt.Color
import java.awt.Font
import java.io.File
import java.time.Duration
import javax.swing.JFrame
import javax.swing.JLabel

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

    fun printToWindow(
        interval: Duration,
    ) {
        while (videoFrameExtractor.hasNextFrame()) {
            val jLabel = JLabel("").apply {
                font = Font("Hack Nerd Font MONO", Font.ITALIC, 12)
                background = Color.black
                isOpaque = true;
            }

            val jFrame = JFrame().apply {
                add(jLabel)
            }

            while (videoFrameExtractor.hasNextFrame()) {
                jLabel.text = videoFrameExtractor.nextFrame()
                    .getAsciiString(size, true)
                Thread.sleep(interval.toMillis())
                jFrame.pack()
                jFrame.isVisible = true
            }
        }
    }
}

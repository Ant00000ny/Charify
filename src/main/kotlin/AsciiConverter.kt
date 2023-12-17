import java.awt.Color
import java.awt.Dimension
import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Path
import java.time.Duration
import java.util.concurrent.atomic.AtomicInteger
import javax.imageio.ImageIO
import javax.swing.JLabel


class AsciiConverter(
    videoFile: File,
    frameInterval: Int,
    private val size: Int,
) {
    private val videoFrameExtractor: VideoFrameExtractor = VideoFrameExtractor(videoFile, frameInterval)
    private var count = AtomicInteger(0);

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

    fun saveFrameImages(
        targetDirPath: Path = USER_DIR.resolve("images"),
    ) {

        while (videoFrameExtractor.hasNextFrame()) {
            val frame = videoFrameExtractor.nextFrame()
            val asciiString = frame.getAsciiString(size, true)

            //            CompletableFuture.supplyAsync(
            //                {
                val thisCount = count.getAndIncrement()
                println("Rendering image $thisCount")

                val jLabel = JLabel().apply {
                    text = asciiString
                    font = hackNerdMonoFont
                    background = Color.black
                    isOpaque = true;
                }

                val renderedImage = BufferedImage(2560, 1664, BufferedImage.TYPE_INT_RGB)
                    .also {
                        jLabel.apply {
                            size = Dimension(2560, 1664)
                            print(it.createGraphics())
                        }
                    }

                println("Writing image $thisCount")
                renderedImage
                    .let {
                        ImageIO.write(
                            it,
                            "png",
                            targetDirPath
                                .resolve((thisCount).toString() + ".png")
                                .toFile()
                                .apply {
                                    if (!exists()) {
                                        mkdirs()
                                        createNewFile()
                                    }
                                }
                        )
                    }
            //                },
            //                executor
            //            )
        }
    }
}

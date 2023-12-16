import java.awt.Color
import java.awt.Font
import java.io.File
import java.time.Duration
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
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
        val jLabelQueue = ArrayBlockingQueue<JLabel>(1000)
        val executor = Executors.newFixedThreadPool(
            Runtime.getRuntime()
                .availableProcessors() - 2
        )

        CompletableFuture.runAsync(
            {
                while (videoFrameExtractor.hasNextFrame()) {
                    val asciiString = videoFrameExtractor.nextFrame()
                        .getAsciiString(size, true)

                    val jLabel = JLabel().apply {
                        text = asciiString
                        font = Font("Hack Nerd Font MONO", Font.BOLD, 12)
                        background = Color.black
                        isOpaque = true;
                    }

                    jLabelQueue.offer(jLabel)
                }
            },
            executor
        )

        val jFrame = JFrame().apply {
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            isVisible = true
        }
        while (true) {
            jFrame.apply {
                components.filterIsInstance<JLabel>()
                    .forEach { remove(it) }
                add(jLabelQueue.poll(60, TimeUnit.SECONDS))
                pack()
            }
        }


        //        val jFrame = JFrame().apply {
        //            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        //        }
        //        CompletableFuture.runAsync(
        //            {
        //                while (jLabelQueue.isNotEmpty() || videoFrameExtractor.hasNextFrame()) {
        //                    val jLabel = jLabelQueue.poll(1000, TimeUnit.SECONDS)
        //                    jFrame.apply {
        //                        removeAll()
        //                        add(jLabel)
        //                        pack()
        //                        isVisible = true
        //                    }
        //                    Thread.sleep(interval.toMillis())
        //                }
        //            },
        //            executor
        //        )
    }
}

import org.bytedeco.ffmpeg.global.avutil
import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.Java2DFrameConverter
import java.awt.image.BufferedImage
import java.io.File

class VideoFrameExtractor(
    videoFile: File,
    private val interval: Int,
) {
    init {
        // no logging
        avutil.av_log_set_level(avutil.AV_LOG_QUIET)
    }

    private val converter = Java2DFrameConverter()
    private val grabber = FFmpegFrameGrabber(videoFile).apply { start() }
    private var nextFrameIndex = 0
    private val totalFrames = grabber.lengthInVideoFrames

    fun nextFrame(): BufferedImage {
        grabber.setVideoFrameNumber(nextFrameIndex)
        nextFrameIndex += interval
        return grabber.grabImage()
            .let { converter.convert(it) }
    }

    fun hasNextFrame() = nextFrameIndex < totalFrames
}

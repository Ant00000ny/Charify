import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.min

fun BufferedImage.getAsciiString(
    size: Int,
): String {
    val (outputHeight, outputWidth) = Pair(this.height, this.width) * (size.toDouble() / min(this.height, this.width))
    val pixelMatrix = getPixels(outputWidth, outputHeight, this)

    return StringBuilder().apply {
        for (y in 0..<pixelMatrix[0].size) {
            for (x in 0..<pixelMatrix.size) {
                append(pixelMatrix[x][y].toConsoleChar())
                append(' ')
            }
            appendLine()
        }
    }
        .toString()
}

private fun getPixels(
    outputWidth: Int,
    outputHeight: Int,
    image: BufferedImage,
): Array<Array<AsciiPixel>> {
    val scaledImage = rescaleImage(image, outputWidth, outputHeight)

    val matrix = Array(outputWidth) {
        Array(outputHeight) {
            AsciiPixel(Color.BLACK, ' ')
        }
    }

    for (x in 0..<outputWidth) {
        for (y in 0..<outputHeight) {
            val color = Color(scaledImage.getRGB(x, y))
            val char = color
                .run { (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255 }
                .let { asciiChars[(it * (asciiChars.length - 1)).toInt()] }

            matrix[x][y] = AsciiPixel(color, char)
        }
    }

    return matrix
}

fun rescaleImage(
    originalImage: BufferedImage,
    targetWidth: Int,
    targetHeight: Int,
): BufferedImage {
    return BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB)
        .also {
            it.createGraphics()
                .apply {
                    drawImage(originalImage, 0, 0, targetWidth, targetHeight, null)
                    dispose()
                }
        }
}

data class AsciiPixel(
    val color: Color,
    val char: Char,
)

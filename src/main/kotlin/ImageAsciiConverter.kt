import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

fun BufferedImage.getAsciiString(
    size: Int,
    useHTML: Boolean = false,
): String {
    val (outputHeight, outputWidth) = Pair(this.height, this.width) * (size.toDouble() / min(this.height, this.width))
    val pixelMatrix = getPixels(outputWidth, outputHeight, this)

    return StringBuilder().apply {
        for (y in 0..<pixelMatrix[0].size) {
            for (x in 0..<pixelMatrix.size) {
                append(pixelMatrix[x][y].let { if (useHTML) it.toHTMLChar() else it.toConsoleChar() })
                append(if (useHTML) "&nbsp;" else ' ')
            }
            if (useHTML) append("<br/>") else appendLine()
        }
    }
        .toString()
        .let { if (useHTML) "<html>$it</html>" else it }
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

private fun rescaleImage(
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
) {
    fun toConsoleChar(): String {
        val nearestConsoleColor = colorMap
            .map { (colorTriple, consoleColor) ->
                consoleColor to sqrt(
                    (colorTriple.first - color.red).toDouble()
                        .pow(2.0) +
                            (colorTriple.second - color.green).toDouble()
                                .pow(2.0) +
                            (colorTriple.third - color.blue).toDouble()
                                .pow(2.0)
                )
            }
            .minBy { it.second }
            .first

        return "$nearestConsoleColor$char$ANSI_RESET"
    }

    fun toHTMLChar(): String {
        return "<font color=\"rgb(${color.red}, ${color.green}, ${color.blue})\">$char</font>"
    }

}

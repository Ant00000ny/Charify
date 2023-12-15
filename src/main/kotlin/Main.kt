package com.ant00000ny

import java.awt.Color
import java.io.File
import javax.imageio.ImageIO


fun main() {
    val (outputWidth, outputHeight) = Pair(16, 9) * 6
    val chars = "◙◘■▩●▦▣◚◛◕▨▧◉▤◐◒▮◍◑▼▪◤▬◗◭◖◈◎◮◊◫▰◄◯□▯▷▫▽◹△◁▸▭◅▵◌▱▹▿".reversed()
    val image = File(
        object {}.javaClass.classLoader.getResource("image.png")
            ?.toURI()
            ?: throw Exception("image not found")
    )

    printAscii(getPixels(outputWidth, outputHeight, chars, image))
}

private fun getPixels(
    outputWidth: Int,
    outputHeight: Int,
    chars: String,
    image: File
): Array<Array<AsciiPixel>> {
    val bufferedImage = image
        .let { ImageIO.read(it) }
        .let { resizeImage(it, outputWidth, outputHeight) }

    // init array
    val matrix = Array(outputWidth) {
        Array(outputHeight) {
            AsciiPixel(Color.BLACK, ' ')
        }
    }

    for (x in 0..<outputWidth) {
        for (y in 0..<outputHeight) {
            val color = Color(bufferedImage.getRGB(x, y))
            val char = color
                .run { (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255 }
                .let { chars[(it * (chars.length - 1)).toInt()] }

            matrix[x][y] = AsciiPixel(color, char)
        }
    }

    return matrix
}

private fun printAscii(
    matrix: Array<Array<AsciiPixel>>
) {
    for (y in 0..<matrix[0].size) {
        for (x in 0..<matrix.size) {
            print(matrix[x][y].char)
            print(' ')
        }
        println()
    }
}

data class AsciiPixel(
    val color: Color,
    val char: Char
)

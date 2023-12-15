package com.ant00000ny

import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFrame


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
        .let { rescaleImage(it, outputWidth, outputHeight) }

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

private fun printAscii(
    matrix: Array<Array<AsciiPixel>>
) {
    for (y in 0..<matrix[0].size) {
        for (x in 0..<matrix.size) {
            print(matrix[x][y].toConsoleChar())
            print(' ')
        }
        println()
    }
}

private fun paintAscii(
    matrix: Array<Array<AsciiPixel>>,
) {


    JFrame().apply {
        setSize(1600, 900)
        isVisible = true
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        add(object :
            javax.swing.JPanel() {
            override fun paint(
                g: java.awt.Graphics,
            ) {
                (g as Graphics2D)
                    .run {
                        setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
                        font = Font("Serif", Font.PLAIN, 96);
                        TODO()
                    }

                for (y in 0..<matrix[0].size) {
                    for (x in 0..<matrix.size) {
                        g.color = matrix[x][y].color
                        g.drawString(matrix[x][y].char.toString(), x * 10, y * 10)
                    }
                }
            }
        })
    }
}

data class AsciiPixel(
    val color: Color,
    val char: Char
)

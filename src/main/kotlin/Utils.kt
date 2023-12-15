package com.ant00000ny

import java.awt.image.BufferedImage


fun resizeImage(
    originalImage: BufferedImage,
    targetWidth: Int,
    targetHeight: Int
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

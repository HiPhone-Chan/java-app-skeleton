package tech.hiphone.cms.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class ImageUtil {

    public static String COMPRESS_SUFFIX = "-mini";
    public static String OUTPUT_FORMAT = "jpg";

    public static void compressFile(File file) {
        compressFiles(file);
    }

    public static void compressFiles(File... files) {
        try {
            Thumbnails.of(files).size(100, 50).outputFormat(OUTPUT_FORMAT).toFiles(new Rename() {
                @Override
                public String apply(String fileName, ThumbnailParameter param) {
                    return appendSuffix(fileName, COMPRESS_SUFFIX);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage getBinaryImage(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage binaryImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgba = image.getRGB(i, j);
                if ((rgba >>> 24) > 0) { // 不透明
                    binaryImage.setRGB(i, j, rgba | 0xff000000);
                } else {
                    binaryImage.setRGB(i, j, 0x00ffffff);
                }
            }
        }

        return binaryImage;
    }

    public static BufferedImage getBinaryImage(int[][] data) throws IOException {
        int width = data.length;
        int height = data[0].length;
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                newImage.setRGB(i, j, data[i][j]);
            }
        }

        return newImage;
    }

    public static int[][] getData(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] data = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                data[i][j] = image.getRGB(i, j);
            }
        }
        return data;
    }

    public static BufferedImage getGrayImage(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);

        int width = image.getWidth();
        int height = image.getHeight();

        // 重点，技巧在这个参数BufferedImage.TYPE_BYTE_GRAY
        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgba = image.getRGB(i, j);
                if ((rgba >>> 24) > 0) { // 不透明
                    grayImage.setRGB(i, j, rgba | 0xff000000);
                } else {
                    grayImage.setRGB(i, j, 0x00ffffff);
                }
            }
        }
        return grayImage;
    }

}

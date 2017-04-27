package app.modules;

import app.utils.ds.HuffmanDistribution;
import app.utils.ds.HuffmanNode;
import app.utils.ds.HuffmanTree;
import app.utils.enums.Movement;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Decompressor
{
    private HuffmanTree tree;
    private boolean hasReachedMaxPixel;

    public Decompressor(HuffmanTree tree)
    {
        this.tree = tree;
        hasReachedMaxPixel = false;
    }

    public void generateTree(HuffmanDistribution distribution)
    {
        tree.generateTree(distribution);
    }

    public BufferedImage decompress(String source, String targetDir, String targetFilename) throws IOException
    {
        Path sourcePath = Paths.get(source);
        byte[] imageBytes = Files.readAllBytes(sourcePath);

        int width = getDimension(imageBytes, 0);
        int height = getDimension(imageBytes, 4);
        BufferedImage resultingImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Yeah, splicing will add an additional linear time to this decompression algorithm
        // (though it'll only increment this algorithm's complexity coefficient by one).
        // Soooooooooooooooo, we ain't gonna do that.

        applyColors(resultingImage, imageBytes, width, height);
    }

    private void applyColors(BufferedImage resultingImage, byte[] imageBytes, int width, int height)
    {
        hasReachedMaxPixel = false;
        Point currentPoint = new Point(0, 0);
        for (int i = 8; i < imageBytes.length; i++) {
            processByte(resultingImage, imageBytes[i], currentPoint, width, height);

            if (hasReachedMaxPixel) {
                break;
            }
        }
    }

    private void processByte(BufferedImage resultingImage, byte imageByte, Point currentPoint, int width, int height)
    {
        for (int j = 7; j >= 0; j--) {
            byte coding = (byte) ((imageByte >> j) & 1);
            Movement movement = (coding == 0) ? Movement.LEFT : Movement.RIGHT;
            HuffmanNode currentNode = tree.traverseTree(movement);

            if (currentNode.isALeaf()) {
                processLeaf(resultingImage, currentNode, currentPoint, width, height);

                if (currentPoint.x == width - 1 && currentPoint.y == height - 1) {
                    hasReachedMaxPixel = true;
                    break;
                }
            }
        }
    }

    private void processLeaf(BufferedImage resultingImage, HuffmanNode currentNode, Point currentPoint, int width, int height)
    {
        int colorValue = RGBAToARGB(currentNode.getColorValue());
        resultingImage.setRGB(currentPoint.x, currentPoint.y, colorValue);

        currentPoint.setLocation(++currentPoint.x, currentPoint.y);
        if (currentPoint.x == width) { currentPoint.setLocation(0, currentPoint.y++); }
        else { currentPoint.setLocation(currentPoint.x++, currentPoint.y); }
    }

    private int RGBAToARGB(int colorValue)
    {
        return ((colorValue >> 8) & 0x00FFFFFF) | ((colorValue << 24) & 0xFF000000);
    }

    private int getDimension(byte[] data, int start)
    {
        return ((int) data[start] << 24) | ((int) data[start + 1] << 16) |
               ((int) data[start + 2] << 8) | ((int) data[start + 3]);
    }
}

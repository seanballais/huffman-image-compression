/*
 * Huffman Image Compression
 * Copyright (C) 2017  Sean Ballais, Kenn Pulma
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package app.modules;

import app.utils.ds.HuffmanDistribution;
import app.utils.ds.HuffmanTree;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

/**
 * The compressor is responsible for converting the individual pixel values of an image
 * into their corresponding bit strings.
 *
 * The first eight bytes in the resulting file is the metadata. This specifies the width
 * and height of the image. This will be useful when decompressing the image for viewing.
 * The first 4 bytes of the eight bytes contains the width of the image, and the remaining
 * four bytes contains the height of the image. The dimensions used for the width and
 * height are all in pixels. These dimensions limits the maximum image size to
 * 2,147,483,647 pixels.
 */

public class Compressor
{
    private HuffmanDistribution distribution;
    private HuffmanTree tree;
    private HashMap<Integer, String> bitStrings;

    /**
     * Constructs a new <tt>Compressor</tt> object with an empty Huffman distribution,
     * Huffman Tree, and bit strings.
     */
    public Compressor()
    {
        distribution = new HuffmanDistribution();
        tree = new HuffmanTree();
        bitStrings = new HashMap<>();
    }

    /**
     * Generate a Huffman Distribution from a distribution file created by
     * a Huffman Trainer.
     *
     * @param  distributionFile The outputted distribution file by a Huffman Trainer.
     * @throws IOException if something went wrong while reading a file, or the file
     *                     doesn't exist.
     * @see    Trainer
     */
    public void generateDistributionFromFile(String distributionFile) throws IOException
    {
        try {
            distribution.generateDistributionFromFile(distributionFile);
        } catch (IOException iex) {
            throw new IOException("Error while reading file. Make sure it exists.");
        }

        tree.generateTree(distribution);
        bitStrings = tree.getBitStrings();
    }

    /**
     * Add a new color value or update a present color value with a specified
     * frequency. If the color value is already present, the frequency of the color
     * value is incremented by the specified frequency.
     *
     * @param colorValue The color value to be added, or updated.
     * @param frequency
     *        The frequency of the new color value, or the number to be added
     *        to the frequency of the existing color value specified.
     */
    public void updateDistribution(int colorValue, int frequency)
    {
        distribution.updateDistribution(colorValue, frequency);
        tree.generateTree(distribution);
        bitStrings = tree.getBitStrings();
    }

    /**
     * Compress a specified image and input it into an output file. The output file
     * has an extension of .pnb. No need to append a slash in the <tt>outputDirectory</tt>
     * string. The directory is automatically appended with a forward slash when saving
     * the compressed data to a file.
     *
     * @param  imageFile       The image to be compressed.
     * @param  outputDirectory The directory to store the file in.
     * @param  outputFilename  The name of the file.
     * @throws IOException     if something went wrong while reading the image, or the image
     *                         specified doesn't exist.
     */
    public void compressImage(String imageFile, String outputDirectory, String outputFilename) throws IOException
    {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(imageFile));
        } catch (IOException iex) {
            throw new IOException("Error while reading image. Make sure it exists.");
        }

        ArrayList<Byte> compressedImage = new ArrayList<>();
        setFourBytesForDimension(compressedImage, image.getWidth());
        setFourBytesForDimension(compressedImage, image.getHeight());

        processPixels(compressedImage, image);
        byteToFile(compressedImage, outputDirectory, outputFilename);
    }

    /**
     * Returns the Huffman distribution of this compressor.
     *
     * @return the Huffman distribution of this compressor.
     */
    public HuffmanDistribution getDistribution()
    {
        return distribution;
    }

    private void byteToFile(ArrayList<Byte> compressedImage, String outputDirectory, String outputFilename) throws IOException
    {
        byte[] imageBytes = new byte[compressedImage.size()];
        IntStream.range(0, compressedImage.size()).forEach(i -> imageBytes[i] = compressedImage.get(i));

        String filePath = outputDirectory + "/" + outputFilename + ".pnb";
        Files.write(new File(filePath).toPath(), imageBytes);
    }

    private void processPixels(ArrayList<Byte> compressedImage, BufferedImage image)
    {
        int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        int offsetCount = 7;
        for (int imagePixel : imagePixels) {
            Color c = new Color(imagePixel);
            int pixelColor = ((c.getRed() << 24) & 0xFF000000) |((c.getGreen() << 16) & 0x00FF0000) |
                             ((c.getBlue() << 8) & 0x0000FF00) | ((c.getAlpha()) & 0x000000FF);
            String coClorBitString = bitStrings.get(pixelColor);

            if (offsetCount == 7) {
                compressedImage.add((byte) 0);
            }

            for (int j = 0; j < colorBitString.length(); j++) {
                processBit(compressedImage, offsetCount, colorBitString, j);
                offsetCount = --offsetCount % 8;
            }
        }
    }

    private void processBit(ArrayList<Byte> compressedImage, int offset, String bitString, int bitIndex)
    {
        byte currByte = compressedImage.get(compressedImage.size());
        char bit = bitString.charAt(bitIndex);
        if (bit == '1') {
            currByte = (byte) (currByte | ((1 << offset) & 0xFF));
        }

        if (offset == 0 && bitIndex < bitString.length() - 1) {
            // Our bits have ran out! Better add a new one to the
            // array list and use it to store the additional bits in
            // the bit string.
            compressedImage.set(compressedImage.size(), currByte);
            compressedImage.add((byte) 0);
        }
    }

    private void setFourBytesForDimension(ArrayList<Byte> compressedImage, int dimension)
    {
        for (int i = 0; i < 4; i++) {
            // Remember that integers 4 bytes long.
            compressedImage.add((byte) ((dimension >> (8 * (3 - i))) & 0xFF));
        }
    }
}

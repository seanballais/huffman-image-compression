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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * The trainer is responsible for training the Huffman Tree that is used in
 * compressing and decompressing images.
 *
 * The trainer depends on an image to get data. Thus, the more images used
 * to train the Huffman distribution, the better the results. The trainer
 * may also train an existing Huffman Distribution by loading a file
 * containing an existing Huffman Distribution.
 *
 * @see HuffmanDistribution
 * @see Compressor
 * @see Decompressor
 */

public class Trainer
{
    private HuffmanDistribution distribution;

    /**
     * Constructs a new <tt>Trainer</tt> object with an empty
     * Huffman Distribution.
     */
    public Trainer()
    {
        distribution = new HuffmanDistribution();
    }

    /**
     * Save the Huffman Distribution file that can be used to compress and
     * decompress images.
     *
     * @param  target      The target file to save the distribution data.
     * @throws IOException if something went wrong while reading the file,
     *                     or the file does not exist.
     */
    public void saveToFile(String target) throws IOException
    {
        FileWriter fileWriter = new FileWriter(target);
        fileWriter.write(setFileContents());
    }

    /**
     * Train Huffman Distribution from an image.
     *
     * @param  imageFile   The image file to train the distribution from.
     * @throws IOException if something went wrong while reading the file,
     *                     or the file does not exist.
     */
    public void trainFromImage(String imageFile) throws IOException
    {
        BufferedImage image = ImageIO.read(new File(imageFile));
        int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        for (int pixel : pixels) {
            Color c = new Color(pixel);
            int pixelColor = ((c.getRed() << 24) & 0xFF000000) | ((c.getGreen() << 16) & 0x00FF0000) |
                             ((c.getBlue() << 8) & 0x0000FF00) | (c.getAlpha() & 0x000000FF);
            updateDistribution(pixelColor);
        }
    }

    /**
     * Generates the values for the <tt>HuffmanDistribution</tt> from the input
     * file.
     *
     * @param  distributionFile
     *         The file where the values for the huffman distribution is
     *         generated from.
     * @throws IOException if the file does not exist, the format is incorrect,
     *                     something went wrong with I/O.
     * @see HuffmanDistribution
     */
    public void getDistributionFromFile(String distributionFile) throws IOException
    {
        distribution.generateDistributionFromFile(distributionFile);
    }

    /**
     * Returns the <tt>HuffmanDistribution</tt> from this trainer.
     *
     * @return the Huffman Distribution from this trainer.
     * @see HuffmanDistribution
     */
    public HuffmanDistribution getDistribution()
    {
        return distribution;
    }

    private void updateDistribution(int colorValue)
    {
        distribution.updateDistribution(colorValue, 1);
    }

    private String setFileContents()
    {
        Iterator distributionIter = distribution.getDistribution().entrySet().iterator();
        StringBuilder fileContents = new StringBuilder();
        fileContents.append("// WARNING! Do not modify if you do now what you are doing.");
        while (distributionIter.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) distributionIter.next();
            createFileLine(fileContents, pair.getKey().toString(), pair.getValue().toString());

            distributionIter.remove();
        }

        return fileContents.toString();
    }

    private void createFileLine(StringBuilder stringBuilder, String key, String value)
    {
        stringBuilder.append(key);
        stringBuilder.append("-");
        stringBuilder.append(value);
        stringBuilder.append("\n");
    }
}

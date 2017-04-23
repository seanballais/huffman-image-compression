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

package app.utils.ds;

import app.utils.exceptions.FileFormatException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Contains the distribution of the color values in an image
 * or a set of images. This is used to create a Huffman tree.
 * The file that contains the distribution must be in a specific
 * format. Comments are allowed in the file by prepending
 * <tt>//</tt> in the line containing the comment. The format
 * should look like:
 *
 *     [color value] = [frequency of the color value]
 *
 * Using a file with the wrong format will raise an exception.
 *
 * @author Sean Francis N. Ballais
 * @see    HuffmanTree
 */

public class HuffmanDistribution
{
    private HashMap<Integer, Integer> distribution;

    /**
     * Constructs a new <tt>HuffmanDistribution</tt> with no color values
     * and frequencies.
     */
    public HuffmanDistribution()
    {
        distribution = new HashMap<>();
    }

    /**
     * Generates the values for the Huffman Distribution from the input
     * file.
     *
     * @param  distributionFile
     *         The file where the values for the huffman distribution is
     *         generated from.
     * @throws IOException if the file does not exist, the format is incorrect,
     *                     something went wrong with I/O.
     */
    public void generateDistributionFromFile(String distributionFile) throws IOException
    {
        parseFile(distributionFile);
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
        int colorFrequency = (distribution.get(colorValue) != null) ? distribution.get(colorValue) : 0;
        distribution.put(colorValue, frequency + colorFrequency);
    }

    /**
     * Returns a priority queue with Huffman nodes containing the color values,
     * and their respective frequencies.
     *
     * @return a priority queue with Huffman noes containing the color values,
     *         and their respective frequencies.
     */
    public PriorityQueue<HuffmanNode> getPrioritizedDistribution()
    {
        int initCapacity = distribution.size();
        return createHuffmanQueue(
            new PriorityQueue<>(initCapacity, (h1, h2) -> (int) (h1.getFrequency() - h2.getFrequency()))
        );
    }

    private PriorityQueue<HuffmanNode> createHuffmanQueue(PriorityQueue<HuffmanNode> nodes)
    {
        for (HashMap.Entry<Integer, Integer> entry : distribution.entrySet()) {
            int colorValue = entry.getKey();
            int frequency = entry.getValue();

            nodes.add(new HuffmanNode(colorValue, frequency));
        }

        return nodes;
    }

    private void parseFile(String distributionFile) throws IOException
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(distributionFile));
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        } catch (FileFormatException ffex) {
            throw ffex;
        } catch (FileNotFoundException fnfEx) {
            throw new FileNotFoundException("Distribution file does not exist.");
        } catch (IOException iex) {
            throw new IOException("Something went wrong while reading the file contents.");
        }
    }

    private void processLine(String line) throws FileFormatException
    {
        if (isCommentLine(line) || isEmptyLine(line)) {
            // Do nothing.
        } else if (isValidLine(line)) {
            String[] lineValues = line.split("=");
            int key = Integer.parseInt(lineValues[0]);
            int value = Integer.parseInt(lineValues[1]);
            distribution.put(key, value);
        } else {
            throw new FileFormatException("Invalid Huffman file. File must not contain letters or symbols except '='.");
        }
    }

    private boolean isValidLine(String line)
    {
        return line.matches("^((\\s+)?\\d+(\\s+)?=(\\s+)?\\d+(\\s+)?)$");
    }

    private boolean isCommentLine(String line) { return line.matches("^(((\\s)+)?\\/\\/.*)$"); }

    private boolean isEmptyLine(String line) { return line.matches("^(\\s*)$")}
}

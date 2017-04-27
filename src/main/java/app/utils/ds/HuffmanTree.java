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

import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Huffman Trees are commonly used in compression and decompression.
 * Each leaf in the Huffman tree contains a color value and the
 * frequency of said color value.
 *
 * The root of the Huffman Tree will always contain the total
 * frequency of all the color values in the Huffman tree.
 *
 * The edges in a Huffman tree has either 0 or 1 as its value. The
 * edge of a left child to its parent has a value of 0. The edge of a
 * right child to its parent has a value of 1. The value of the edges
 * help get the color value of a pixel in an image when decompressing,
 * and generating the corresponding bit string representation of a
 * color value.
 *
 * @author Sean Francis N. Ballais
 * @see HuffmanNode
 */

public class HuffmanTree
{
    private HuffmanNode root;
    private HashMap<Integer, String> bitStrings;

    /**
     * Constructs a new <tt>HuffmanTree</tt> with an empty root.
     */
    public HuffmanTree()
    {
        root = new HuffmanNode(0, 0);
        bitStrings = new HashMap<>();
    }

    /**
     * Returns the root of the <tt>HuffmanTree</tt>.
     *
     * @return the root of the <tt>HuffmanTree</tt>.
     */
    public HuffmanNode getRoot()
    {
        return root;
    }

    /**
     * Generates a tree based on a Huffman distribution containing the
     * color values that map to the frequency of the said color value.
     * This must be called every time the huffman distribution of the
     * color values are updated.
     *
     * @param distribution
     *        A <tt>HuffmanDistribution</tt> object that contains the
     *        color values of an image or a set of images and their
     *        respective frequencies.
     * @see   HuffmanDistribution
     */
    public void generateTree(HuffmanDistribution distribution)
    {
        PriorityQueue<HuffmanNode> nodes = distribution.getPrioritizedDistribution();
        while (!nodes.isEmpty()) {
            if (nodes.size() > 1) {
                nodes.add(createSubtree(nodes.remove(), nodes.remove()));
            } else {
                root = nodes.remove();
            }
        }

        generateBitStrings(this.bitStrings, new StringBuilder(), root);
    }

    /**
     * Returns a <tt>HashMap</tt> containing the bit string representation
     * of the color values with respect to the Huffman tree.
     *
     * @return a <tt>HashMap</tt> containing the bit string representation
     *         of the color values with respect to the Huffman tree.
     * @see    HashMap
     */
    public HashMap<Integer, String> getBitStrings()
    {
        return bitStrings;
    }

    private void generateBitStrings(HashMap<Integer, String> bitStrings, StringBuilder bitString, HuffmanNode currentNode)
    {
        if (currentNode.isALeaf()) {
            bitStrings.put(currentNode.getColorValue(), bitString.toString());
            bitString.setLength(0);
        } else {
            if (currentNode.getLeftChild() != null) {
                generateBitStrings(bitStrings, bitString.append('0'), currentNode.getLeftChild());
            }

            if (currentNode.getRightChild() != null) {
                generateBitStrings(bitStrings, bitString.append('1'), currentNode.getRightChild());
            }
        }
    }

    private HuffmanNode createSubtree(HuffmanNode node1, HuffmanNode node2)
    {
        HuffmanNode subtreeRoot = new HuffmanNode(0, 0);
        subtreeRoot.setLeftChild(node1);
        subtreeRoot.setRightChild(node2);

        return subtreeRoot;
    }
}
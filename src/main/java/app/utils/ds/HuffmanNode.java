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

/**
 * Node that is used in Huffman trees. Each Huffman node contains a
 * reference to its parent, left child, and right child, should any
 * of the three exists.
 *
 * A Huffman node can exist on its own there will always be only one
 * root node in a Huffman tree. A node can be used on its own. However,
 * its usefulness increases when using a set of nodes in a tree.
 *
 * @author Sean Francis N. Ballais.
 * @see    HuffmanTree
 */

public class HuffmanNode
{
    private HuffmanNode leftChild;
    private HuffmanNode rightChild;
    private HuffmanNode parent;
    private int colorValue;
    private int frequency;

    /**
     * Constructs a new <tt>HuffmanNode</tt> with the specified
     * initial color value, and frequency of said color value.
     *
     * The color value is a compressed version of RGBA. The first
     * byte in the color value contains the value of red, the second
     * byte specifying the value of green, the third byte specifying
     * the value of blue, and the last byte specifying the alpha
     * value.
     *
     * @param colorValue the color value of the node.
     * @param frequency  the frequency the color value appears in an image.
     */
    public HuffmanNode(int colorValue, int frequency)
    {
        this.colorValue = colorValue;
        this.frequency = frequency;
    }

    /**
     * Set the parent of this Huffman node. The value can be null.
     *
     * @param parent the parent of this node.
     */
    public void setParent(HuffmanNode parent)
    {
        this.parent = parent;
    }


    /**
     * Set the left child of this Huffman node. The value can be null.
     *
     * @param child the left child of this node.
     */
    public void setLeftChild(HuffmanNode child)
    {
        leftChild = child;
        if (leftChild != null) {
            frequency += leftChild.getFrequency();
            child.setParent(this);
        }
    }

    /**
     * Set the right child of this Huffman node. The value can be null.
     *
     * @param child the right child of this node.
     */
    public void setRightChild(HuffmanNode child)
    {
        rightChild = child;
        if (rightChild != null) {
            frequency += rightChild.getFrequency();
            child.setParent(this);
        }
    }

    /**
     * Returns the reference to the parent of this node.
     *
     * @return the reference to the parent of this node.
     */
    public HuffmanNode getParent()
    {
        return parent;
    }

    /**
     * Returns the reference to the left child of this node.
     *
     * @return the reference to the left child of this node.
     */
    public HuffmanNode getLeftChild()
    {
        return leftChild;
    }

    /**
     * Returns the reference to the right child of this node.
     *
     * @return the reference to the right child of this node.
     */
    public HuffmanNode getRightChild()
    {
        return rightChild;
    }

    /**
     * Returns the color value of this node.
     *
     * @return the color value of this node.
     */
    public int getColorValue()
    {
        return colorValue;
    }

    /**
     * Returns <tt>true</tt> if this node is a leaf. Otherwise, returns <tt>false</tt>.
     *
     * @return <tt>true</tt> if this node is a leaf. Otherwise, returns <tt>false</tt>.
     */
    public boolean isALeaf()
    {
        return (leftChild == null) && (rightChild == null);
    }

    /**
     * Returns the frequency of the color value of this node.
     *
     * @return the frequency of the color value of this node.
     */
    public int getFrequency()
    {
        return frequency;
    }
}
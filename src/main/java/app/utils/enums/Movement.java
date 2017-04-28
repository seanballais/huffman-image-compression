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

package app.utils.enums;

/**
 * A collection of constants used for <tt>HuffmanTree</tt> movement
 * usually when compressing and decompressing files.
 *
 * @author Sean Francis N. Ballais
 * @see    app.utils.ds.HuffmanTree
 * @see    app.utils.ds.HuffmanNode
 */

public enum Movement
{
    /**
     * Movement constant used to specify that the movement is to the
     * left.
     */
    LEFT,

    /**
     * Movement constant used to specify that the movement is to the
     * right.
     */
    RIGHT
}
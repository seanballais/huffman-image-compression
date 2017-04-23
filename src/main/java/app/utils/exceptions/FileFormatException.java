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

package app.utils.exceptions;

import java.io.IOException;

/**
 * Signals that there is an error in the format of
 * the file being processed.
 *
 * @author Sean Francis N. Ballais
 * @see    app.utils.ds.HuffmanDistribution
 */

public class FileFormatException extends IOException
{
    /**
     * Constructs an {@code FileFormatException} with {@code null}
     * as its error detail message.
     */
    public FileFormatException()
    {
        super();
    }

    /**
     * Constructs an {@code FileFormatException} with the specified detail message.
     *
     * @param message
     *        The detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method)
     */
    public FileFormatException(String message)
    {
        super(message);
    }

    /**
     * Constructs an {@code FileFormatException} with the specified detail
     * message and cause.
     *
     * <p> Note that the detail message associated with {@code cause} is
     * <i>not</i> automatically incorporated into this exception's detail
     * message.
     *
     * @param message
     *        The detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method)
     *
     * @param cause
     *        The cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A null value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     */
    public FileFormatException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Constructs an {@code FileFormatException} with the specified cause and a
     * detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of {@code cause}).
     * This constructor is useful for IO exceptions that are little more
     * than wrappers for other throwables.
     *
     * @param cause
     *        The cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A null value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     */
    public FileFormatException(Throwable cause)
    {
        super(cause);
    }
}
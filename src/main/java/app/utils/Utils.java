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

package app.utils;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

/**
 * A collection of utility functions that are used throughout the application.
 */

public class Utils
{
    /**
     * Checks if the file extension in a file matches with the one specified.
     *
     * @param fileExtension The specified file extension the file must have.
     * @param file          The file to be checked.
     * @return              true if the file has the specified file extension,
     *                      false otherwise.
     */
    public static boolean isFileExtensionValid(String fileExtension, String file)
    {
        PathMatcher matcher =
                FileSystems.getDefault().getPathMatcher("glob:*." + fileExtension);
        Path filePath = Paths.get(file);

        return matcher.matches(filePath);
    }
}

package app.utils.ds;

import app.utils.exceptions.FileFormatException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;

public class HuffmanDistribution
{
    private HashMap<Integer, Integer> distribution;

    public HuffmanDistribution()
    {
        distribution = new HashMap<>();
    }

    public void generateDistribution(String distributionFile) throws IOException
    {
        parseFile(distributionFile);
    }

    public PriorityQueue<HuffmanNode> getPrioritizedDistribution()
    {

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
        if (isValidLine(line)) {
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
        return line.matches("^(( +)?\\d+( +)?=( +)?\\d+( +)?)$");
    }
}

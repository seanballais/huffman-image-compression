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

public class Trainer
{
    private HuffmanDistribution distribution;

    public Trainer()
    {
        distribution = new HuffmanDistribution();
    }

    public void saveToFile(String target) throws IOException
    {
        FileWriter fileWriter = new FileWriter(target);
        fileWriter.write(setFileContents());
    }

    public void trainFromImage(String imageFile) throws IOException
    {
        BufferedImage image = ImageIO.read(new File(imageFile));
        int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        for (int pixel : pixels) {
            Color c = new Color(pixel);
            int pixelColor = ((c.getRed() << 24) & 0xFF000000) | ((c.getGreen() << 16) & 0x00FF0000) |
                             ((c.getBlue() << 8) & 0x0000FF00) | (c.getAlpha() & 0x000000FF);
            updateDistribution(pixelColor, 1);
        }
    }

    public void getDistributionFromFile(String distributionFile)
    {
        distribution.generateDistributionFromFile(distributionFile);
    }

    private void updateDistribution(int colorValue, int frequency)
    {
        distribution.updateDistribution(colorValue, frequency);
    }

    private String setFileContents()
    {
        Iterator distributionIter = distribution.getDistribution().entrySet().iterator();
        StringBuilder fileContents = new StringBuilder();
        fileContents.append("// WARNING! Do not modify if you do now what you are doing.");
        while (distributionIter.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) distributionIter.next();
            createFileLine(fileContents, pair.getKey(), pair.getValue());

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

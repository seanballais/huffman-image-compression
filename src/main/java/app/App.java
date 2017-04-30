package app;

import app.modules.Compressor;
import app.modules.Decompressor;
import app.modules.Trainer;
import app.utils.ds.HuffmanDistribution;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class App
{
    public static void main(String[] args)
    {
        long startTime = System.currentTimeMillis();
        Trainer trainer = new Trainer();
        try {
            trainer.trainFromImage("/home/seanballais/Pictures/Misc/doggo.png");
            trainer.saveToFile("/home/seanballais/Pictures/Misc/");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        /*Compressor compressor = new Compressor();
        try {
            compressor.generateDistributionFromFile("/home/seanballais/Pictures/Misc/trained_data.huff");
            compressor.compressImage(
                "/home/seanballais/Pictures/Misc/doggo.png",
                "/home/seanballais/Pictures/Misc/",
                "outputImg"
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Decompressor decompressor = new Decompressor();
        HuffmanDistribution distribution = new HuffmanDistribution();
        try {
            distribution.generateDistributionFromFile("/home/seanballais/Pictures/Misc/trained_data.huff");
            decompressor.generateTree(distribution);
            BufferedImage imageResult = decompressor.decompress("/home/seanballais/Pictures/Misc/outputImg.pnb");
            File outputFile = new File("/home/seanballais/Pictures/Misc/doggo-result.png");
            ImageIO.write(imageResult, "png", outputFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/

        System.out.println("Total execution time: " + (System.currentTimeMillis() - startTime));
    }
}


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
        /*Trainer trainer = new Trainer();
        try {
            for (int i = 1; i < 27; i++) {
                trainer.trainFromImage("/home/seanballais/Documents/School/UPVTC/2nd Year - Second Sem/CMSC 123/Machine Problems/huffman-image-compression/test_grounds/" + i + ".png");
            }

            trainer.saveToFile("/home/seanballais/Documents/School/UPVTC/2nd Year - Second Sem/CMSC 123/Machine Problems/huffman-image-compression/test_grounds/");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Compressor compressor = new Compressor();
        try {
            compressor.generateDistributionFromFile("/home/seanballais/Documents/School/UPVTC/2nd Year - Second Sem/CMSC 123/Machine Problems/huffman-image-compression/test_grounds/trained_data.huff");
            compressor.compressImage(
                "/home/seanballais/Documents/School/UPVTC/2nd Year - Second Sem/CMSC 123/Machine Problems/huffman-image-compression/test_grounds/2.png",
                "/home/seanballais/Documents/School/UPVTC/2nd Year - Second Sem/CMSC 123/Machine Problems/huffman-image-compression/test_grounds/",
                "outputImg-2"
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/

        Decompressor decompressor = new Decompressor();
        HuffmanDistribution distribution = new HuffmanDistribution();
        try {
            distribution.generateDistributionFromFile("/home/seanballais/Documents/School/UPVTC/2nd Year - Second Sem/CMSC 123/Machine Problems/huffman-image-compression/test_grounds/trained_data.huff");
            decompressor.generateTree(distribution);
            BufferedImage imageResult = decompressor.decompress("/home/seanballais/Documents/School/UPVTC/2nd Year - Second Sem/CMSC 123/Machine Problems/huffman-image-compression/test_grounds/outputImg-2.pnb");
            File outputFile = new File("/home/seanballais/Documents/School/UPVTC/2nd Year - Second Sem/CMSC 123/Machine Problems/huffman-image-compression/test_grounds/output-2.png");
            ImageIO.write(imageResult, "png", outputFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Total execution time: " + (System.currentTimeMillis() - startTime));
    }
}


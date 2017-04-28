package app.modules;

import app.utils.ds.HuffmanDistribution;

public class Trainer
{
    private HuffmanDistribution distribution;

    public Trainer();
    public void outputToFile(String target);
    public void trainFromImage(String imagePath);
    public void getDistributionFromFile(String distributionFile);
    private void updateDistribution(int colorValue, int frequency);
}

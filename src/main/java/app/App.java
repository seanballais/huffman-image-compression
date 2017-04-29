package app;

import app.modules.Trainer;

public class App
{
    public static void main(String[] args)
    {
        long startTime = System.currentTimeMillis();
        Trainer trainer = new Trainer();
        try {
            trainer.trainFromImage("/home/seanballais/Pictures/Nature/IMG_8520_png.PNG");
            trainer.saveToFile("/home/seanballais/");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Total execution time: " + (System.currentTimeMillis() - startTime));
    }
}

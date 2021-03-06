package app;

import app.modules.Compressor;
import app.modules.Decompressor;
import app.modules.Trainer;
import app.utils.ds.HuffmanDistribution;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class App {
    private JPanel guiPanel;
    private JPanel originalImagePanel;
    private JPanel compressedImagePanel;
    private JPanel imagePanel;
    private JPanel buttonPanel;
    private JScrollPane origImageScroll;
    private JScrollPane compressedImageScroll;
    private JButton openImageButton;
    private JButton openHuffmanCodedImageButton;
    private JButton compressImageButton;
    private JButton trainNewHuffmanTreeButton;
    private JLabel originalImage;
    private JLabel compressedImage;
    private JButton trainExistingHuffmanTreeButton;
    private JPanel statusPanel;
    private JLabel statusLabel;
    private Compressor huffmanCompressor;
    private Decompressor huffmanDecompressor;
    private Trainer huffmanTrainer;
    private File currentFile;

    public App() {
        huffmanCompressor = new Compressor();
        huffmanDecompressor = new Decompressor();
        huffmanTrainer = new Trainer();

        openImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter(".PNG files", "png", "PNG"));
            fileChooser.setDialogTitle("Open a PNG image");
            fileChooser.showOpenDialog(null);
            currentFile = fileChooser.getSelectedFile();
            if (currentFile != null) {
                originalImage.setIcon(new ImageIcon(currentFile.getAbsolutePath()));
                statusLabel.setText("Opened image '" + currentFile.getAbsolutePath() + "'.");
                trainNewHuffmanTreeButton.setEnabled(true);
                trainExistingHuffmanTreeButton.setEnabled(true);
            }
        });
        trainNewHuffmanTreeButton.addActionListener(e -> {
            huffmanTrainer = new Trainer();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setDialogTitle("Choose directory to store the trained huffman file");
            fileChooser.showOpenDialog(null);
            File currentDir = fileChooser.getSelectedFile();
            long startTime = System.currentTimeMillis();
            if (currentDir != null) {
                statusLabel.setText("Training data from pre-selected image.");
                try {
                    huffmanTrainer.trainFromImage(currentFile.getAbsolutePath());
                    huffmanTrainer.saveToFile(currentDir.getAbsolutePath() + "/");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            ex.getMessage(),
                            "An error occurred while training data",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                statusLabel.setText("Data trained successfully in " + ((System.currentTimeMillis() - startTime) / 1000) + " seconds.");
                compressImageButton.setEnabled(true);
                openHuffmanCodedImageButton.setEnabled(true);
            }
        });
        trainExistingHuffmanTreeButton.addActionListener(e -> {
            huffmanTrainer = new Trainer();
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogTitle("Choose existing Huffman file");
            fileChooser.setFileFilter(null);
            fileChooser.showOpenDialog(null);
            File currentHuffFile = fileChooser.getSelectedFile();
            long startTime = System.currentTimeMillis();
            if (currentHuffFile != null) {
                try {
                    statusLabel.setText("Getting distribution from " + currentHuffFile.getAbsolutePath() + ".");
                    huffmanTrainer.getDistributionFromFile(currentHuffFile.getAbsolutePath());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            ex.getMessage(),
                            "An error occurred while reading selected file",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }

            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setDialogTitle("Choose directory to store the trained huffman file");
            long midTime = System.currentTimeMillis();
            fileChooser.showOpenDialog(null);
            File currentDir = fileChooser.getSelectedFile();
            midTime = System.currentTimeMillis() - midTime;
            startTime -= midTime;
            if (currentDir != null) {
                statusLabel.setText("Training data from pre-selected image.");
                try {
                    huffmanTrainer.trainFromImage(currentFile.getAbsolutePath());
                    huffmanTrainer.saveToFile(currentDir.getAbsolutePath() + "/");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            ex.getMessage(),
                            "An error occurred while training data",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                statusLabel.setText("Data trained successfully in " + ((System.currentTimeMillis() - startTime) / 1000) + " seconds.");
                compressImageButton.setEnabled(true);
                openHuffmanCodedImageButton.setEnabled(true);
            }
        });
        compressImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();

            try {
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setDialogTitle("Choose existing Huffman file");
                fileChooser.setFileFilter(new FileNameExtensionFilter(".HUFF files", "huff", "HUFF"));
                fileChooser.showOpenDialog(null);
                File currentHuffFile = fileChooser.getSelectedFile();
                long startTime = System.currentTimeMillis();
                if (currentHuffFile != null) {
                    statusLabel.setText("Getting distribution from " + currentHuffFile.getAbsolutePath() + ".");
                    huffmanCompressor.generateDistributionFromFile(currentHuffFile.getAbsolutePath());

                    long midTime = System.currentTimeMillis();
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    fileChooser.setFileFilter(null);
                    fileChooser.setDialogTitle("Choose directory to save the compressed image");
                    fileChooser.showOpenDialog(null);
                    File currentDir = fileChooser.getSelectedFile();
                    midTime = System.currentTimeMillis() - midTime;
                    startTime -= midTime;
                    if (currentDir != null) {
                        statusLabel.setText("Saving compressed image in " + currentDir.getAbsolutePath() + ".");
                        huffmanCompressor.compressImage(
                                currentFile.getAbsolutePath(), currentDir.getAbsolutePath(), "output-img"
                        );
                        statusLabel.setText("Compression duration: " + ((System.currentTimeMillis() - startTime) / 1000) + " seconds. Compressed file saved in " + currentDir.getAbsolutePath() + ".");
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        ex.getMessage(),
                        "An error occurred while reading selected file",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
        openHuffmanCodedImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();

            try {
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setDialogTitle("Choose existing Huffman file");
                fileChooser.setFileFilter(new FileNameExtensionFilter(".HUFF files", "huff", "HUFF"));
                fileChooser.showOpenDialog(null);
                File currentHuffFile = fileChooser.getSelectedFile();
                long startTime = System.currentTimeMillis();
                if (currentHuffFile != null) {
                    statusLabel.setText("Getting distribution from " + currentHuffFile.getAbsolutePath() + ".");
                    huffmanCompressor.generateDistributionFromFile(currentHuffFile.getAbsolutePath());

                    long midTime = System.currentTimeMillis();
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    fileChooser.setFileFilter(new FileNameExtensionFilter(".PNB files", "pnb", "PNB"));
                    fileChooser.setDialogTitle("Choose image to decompress (.pnb)");
                    fileChooser.showOpenDialog(null);
                    File compressedFile = fileChooser.getSelectedFile();
                    midTime = System.currentTimeMillis() - midTime;
                    startTime -= midTime;
                    if (compressedFile != null) {
                        statusLabel.setText("Decompressing image " + compressedFile.getAbsolutePath() + ".");
                        HuffmanDistribution distribution = new HuffmanDistribution();
                        distribution.generateDistributionFromFile(currentHuffFile.getAbsolutePath());
                        huffmanDecompressor.generateTree(distribution);
                        BufferedImage decompressedImage = huffmanDecompressor.decompress(compressedFile.getAbsolutePath());
                        compressedImage.setIcon(new ImageIcon(decompressedImage));

                        statusLabel.setText("Decompression duration: " + ((System.currentTimeMillis() - startTime) / 1000) + " seconds. Finished decompressing file " + compressedFile.getAbsolutePath() + ".");
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        ex.getMessage(),
                        "An error occurred while reading selected file",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Huffman Image Compression");
        frame.setContentPane(new App().guiPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        guiPanel = new JPanel();
        guiPanel.setLayout(new BorderLayout(0, 0));
        imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        guiPanel.add(imagePanel, BorderLayout.CENTER);
        compressedImagePanel = new JPanel();
        compressedImagePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        imagePanel.add(compressedImagePanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(498, 500), new Dimension(498, 500), new Dimension(498, 500), 1, false));
        compressedImagePanel.setBorder(BorderFactory.createTitledBorder("Compressed Image"));
        compressedImageScroll = new JScrollPane();
        compressedImagePanel.add(compressedImageScroll, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        compressedImage = new JLabel();
        compressedImage.setText("");
        compressedImageScroll.setViewportView(compressedImage);
        originalImagePanel = new JPanel();
        originalImagePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        imagePanel.add(originalImagePanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(498, 500), new Dimension(498, 500), new Dimension(498, 500), 0, false));
        originalImagePanel.setBorder(BorderFactory.createTitledBorder("Original Image"));
        origImageScroll = new JScrollPane();
        originalImagePanel.add(origImageScroll, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        originalImage = new JLabel();
        originalImage.setText("");
        origImageScroll.setViewportView(originalImage);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        guiPanel.add(buttonPanel, BorderLayout.NORTH);
        openHuffmanCodedImageButton = new JButton();
        openHuffmanCodedImageButton.setEnabled(true);
        openHuffmanCodedImageButton.setText("Open Huffman Coded Image");
        buttonPanel.add(openHuffmanCodedImageButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        compressImageButton = new JButton();
        compressImageButton.setEnabled(false);
        compressImageButton.setText("Compress Image");
        buttonPanel.add(compressImageButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        trainNewHuffmanTreeButton = new JButton();
        trainNewHuffmanTreeButton.setEnabled(false);
        trainNewHuffmanTreeButton.setText("Train New Huffman Tree");
        buttonPanel.add(trainNewHuffmanTreeButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        trainExistingHuffmanTreeButton = new JButton();
        trainExistingHuffmanTreeButton.setEnabled(false);
        trainExistingHuffmanTreeButton.setText("Train Existing Huffman Tree");
        buttonPanel.add(trainExistingHuffmanTreeButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openImageButton = new JButton();
        openImageButton.setText("Open PNG Image");
        buttonPanel.add(openImageButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        guiPanel.add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), null));
        statusLabel = new JLabel();
        statusLabel.setText("Nothing particularly interesting right now, unless realizing that I am merely a series of bits is fun.");
        statusPanel.add(statusLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 24), new Dimension(-1, 24), new Dimension(-1, 24), 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return guiPanel;
    }
}

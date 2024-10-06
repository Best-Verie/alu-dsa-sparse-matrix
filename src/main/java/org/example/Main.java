package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add two matrices");
            System.out.println("2. Subtract two matrices");
            System.out.println("3. Multiply two matrices");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice == 4) {
                System.out.println("Exiting...");
                break;
            }

            SparseMatrix matrix1 = new SparseMatrix();
            SparseMatrix matrix2 = new SparseMatrix();
            SparseMatrix matrix3 = new SparseMatrix();
            SparseMatrix matrix4 = new SparseMatrix();

            SparseMatrix result;

            try {
                // Resolve paths to the sample_inputs directory for the files
                String basePath = "sample_inputs"; // Assuming sample_inputs is in the root directory
                String matrix3File = "C:\\Users\\irabe\\OneDrive\\Documents\\BSE\\DSA\\DSAHw02\\src\\main\\java\\org\\example\\sample_inputs\\easy_sample_02_1.txt";
                String matrix4File = "C:\\Users\\irabe\\OneDrive\\Documents\\BSE\\DSA\\DSAHw02\\src\\main\\java\\org\\example\\sample_inputs\\easy_sample_02_2.txt";
                String matrix1File = "C:\\Users\\irabe\\OneDrive\\Documents\\BSE\\DSA\\DSAHw02\\src\\main\\java\\org\\example\\sample_inputs\\easy_sample_01_3.txt";
                String matrix2File = "C:\\Users\\irabe\\OneDrive\\Documents\\BSE\\DSA\\DSAHw02\\src\\main\\java\\org\\example\\sample_inputs\\easy_sample_01_2.txt";

                // Read matrices from files based on user operation
                matrix1.readFromFile(matrix1File);
                matrix2.readFromFile(matrix2File);
                matrix3.readFromFile(matrix3File);
                matrix4.readFromFile(matrix4File);

                String operation = "";
                switch (choice) {
                    case 1:
                        System.out.println("Adding matrices...");
                        result = matrix3.add(matrix4);
                        operation = "add";
                        break;
                    case 2:
                        System.out.println("Subtracting matrices...");
                        result = matrix3.subtract(matrix4);
                        operation = "subtract";
                        break;
                    case 3:
                        System.out.println("Multiplying matrices...");
                        result = matrix1.multiply(matrix2);
                        operation = "multiply";
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }

                // Generate dynamic output file name
                String outputDir = "C:\\Users\\irabe\\OneDrive\\Documents\\BSE\\DSA\\DSAHw02\\src\\main\\java\\org\\example\\outputs";
                String outputFileName = generateOutputFileName(outputDir, "easy_sample_02_1.txt", operation, "easy_sample_02_2.txt");

                // Ensure output directory exists
                File outputDirectory = new File(outputDir);
                if (!outputDirectory.exists()) {
                    outputDirectory.mkdirs(); // Create directory if it doesn't exist
                }

                // Write result to the generated file name
                result.writeToFile(outputFileName);
                System.out.println("Result written to " + outputFileName);
            } catch (IOException e) {
                System.err.println("Error reading files: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    // Helper method to generate the output filename based on the input files and operation
    private static String generateOutputFileName(String outputDir, String file1, String operation, String file2) {
        // Extract just the file name without extension
        String file1Name = file1.substring(file1.lastIndexOf('/') + 1, file1.lastIndexOf('.'));
        String file2Name = file2.substring(file2.lastIndexOf('/') + 1, file2.lastIndexOf('.'));

        // Generate the output file name
        return Paths.get(outputDir, file1Name + "-" + operation + "-" + file2Name + ".txt").toString();
    }
}

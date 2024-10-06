package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SparseMatrix {
    private Map<String, Integer> matrix;
    private int rows;
    private int cols;

    public SparseMatrix() {
        this.matrix = new HashMap<>();
        this.rows = 0;
        this.cols = 0;
    }

    // Reads a sparse matrix from a file, storing only non-zero elements
    public void readFromFile(String filePath) throws IOException {
        System.out.println("Reading matrix from file: " + filePath);
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        // Parse dimensions from the first two lines
        this.rows = Integer.parseInt(br.readLine().split("=")[1].trim());
        this.cols = Integer.parseInt(br.readLine().split("=")[1].trim());
        System.out.println("Matrix dimensions: Rows=" + this.rows + ", Cols=" + this.cols);

        String line;
        while ((line = br.readLine()) != null) {
            line = line.replaceAll("[()]", "");
            String[] parts = line.split(",");
            if (parts.length == 3) {
                int row = Integer.parseInt(parts[0].trim());
                int col = Integer.parseInt(parts[1].trim());
                int value = Integer.parseInt(parts[2].trim());
                if (value != 0) {
                    this.matrix.put(row + "," + col, value);
                    System.out.println("Added non-zero entry: (" + row + ", " + col + ", " + value + ")");
                }
            }
        }
        br.close();
        System.out.println("Finished reading matrix from file.");
    }

    // Validates if dimensions match for addition or subtraction
    private void validateAddSubDimensions(SparseMatrix other) {
        System.out.println("Validating dimensions for addition/subtraction...");
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Matrix dimensions do not match for addition or subtraction.");
        }
        System.out.println("Dimensions validated successfully.");
    }

    // Validates if dimensions match for multiplication
    private void validateMultiplicationDimensions(SparseMatrix other) {
        System.out.println("Validating dimensions for multiplication...");
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Matrix multiplication is not possible: Columns of the first matrix must equal rows of the second.");
        }
        System.out.println("Dimensions validated successfully.");
    }

    // Adds two sparse matrices
    public SparseMatrix add(SparseMatrix other) {
        validateAddSubDimensions(other);

        SparseMatrix result = new SparseMatrix();
        result.rows = this.rows;
        result.cols = this.cols;

        // Add all elements from the first matrix
        for (Map.Entry<String, Integer> entry : this.matrix.entrySet()) {
            result.matrix.put(entry.getKey(), entry.getValue());
        }

        // Add elements from the second matrix
        for (Map.Entry<String, Integer> entry : other.matrix.entrySet()) {
            result.matrix.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }

        return result;
    }

    // Subtracts two sparse matrices
    public SparseMatrix subtract(SparseMatrix other) {
        validateAddSubDimensions(other);

        SparseMatrix result = new SparseMatrix();
        result.rows = this.rows;
        result.cols = this.cols;

        // Add all elements from the first matrix
        for (Map.Entry<String, Integer> entry : this.matrix.entrySet()) {
            result.matrix.put(entry.getKey(), entry.getValue());
        }

        // Subtract elements from the second matrix
        for (Map.Entry<String, Integer> entry : other.matrix.entrySet()) {
            result.matrix.merge(entry.getKey(), -entry.getValue(), Integer::sum);
        }

        return result;
    }

    // Multiplies two sparse matrices
    public SparseMatrix multiply(SparseMatrix other) {
        validateMultiplicationDimensions(other);

        SparseMatrix result = new SparseMatrix();
        result.rows = this.rows;
        result.cols = other.cols;

        for (Map.Entry<String, Integer> entry1 : this.matrix.entrySet()) {
            String[] indices1 = entry1.getKey().split(",");
            int i = Integer.parseInt(indices1[0]);
            int k = Integer.parseInt(indices1[1]);

            for (Map.Entry<String, Integer> entry2 : other.matrix.entrySet()) {
                String[] indices2 = entry2.getKey().split(",");
                int k2 = Integer.parseInt(indices2[0]);
                int j = Integer.parseInt(indices2[1]);

                if (k == k2) {
                    int value = entry1.getValue() * entry2.getValue();
                    String resultKey = i + "," + j;
                    result.matrix.merge(resultKey, value, Integer::sum);
                }
            }
        }

        return result;
    }

    // Writes the matrix to a file
    public void writeToFile(String filePath) throws IOException {
        System.out.println("Writing matrix to file: " + filePath);
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));

        bw.write("Rows=" + this.rows + "\n");
        bw.write("Cols=" + this.cols + "\n");

        for (Map.Entry<String, Integer> entry : this.matrix.entrySet()) {
            String[] indices = entry.getKey().split(",");
            bw.write("(" + indices[0] + "," + indices[1] + "," + entry.getValue() + ")\n");
        }

        bw.close();
        System.out.println("Matrix written to file successfully.");
    }
}

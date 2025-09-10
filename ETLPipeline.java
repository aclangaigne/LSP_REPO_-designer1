package org.howard.edu.lsp.assignment2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * CSCI 363/540 – Assignment 1: CSV ETL Pipeline in Java
 * Implements a simple ETL pipeline using relative paths.
 *
 * Project structure expected:
 * JavaProjectRoot/
 * ├── src/
 * │   └── org/howard/edu/lsp/assignment2/ETLPipeline.java
 * ├── data/
 * │   ├── products.csv
 * │   └── transformed_products.csv
 *
 * Run from the project root so relative paths resolve correctly.
 */
public class ETLPipeline {
    private static final String INPUT_PATH  = "data/products.csv";
    private static final String OUTPUT_PATH = "data/transformed_products.csv";

    private static class Product {
        int productId;
        String name;
        BigDecimal price;   // final price after any discount
        String category;
        String priceRange;

        Product(int productId, String name, BigDecimal price, String category) {
            this.productId = productId;
            this.name = name;
            this.price = price;
            this.category = category;
        }
    }

    public static void main(String[] args) {
        File inFile = new File(INPUT_PATH);
        File outFile = new File(OUTPUT_PATH);

        // Ensure output parent directory exists
        outFile.getParentFile().mkdirs();

        if (!inFile.exists()) {
            System.err.println("ERROR: Missing input file: " + INPUT_PATH);
            System.err.println("Ensure you run the program from the project root and that data/products.csv exists.");
            // Still create an output file with just header to be safe? Spec says on missing file: print and exit.
            return;
        }

        int rowsRead = 0;
        int rowsTransformed = 0;
        int rowsSkipped = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(inFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outFile))) {

            // Write header to output
            bw.write("ProductID,Name,Price,Category,PriceRange");
            bw.newLine();

            String header = br.readLine(); // read and ignore header from input
            if (header == null) {
                // Empty file: nothing else to do
                printSummary(rowsRead, rowsTransformed, rowsSkipped, OUTPUT_PATH);
                return;
            }

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    // blank line—skip but do not count as read
                    continue;
                }
                Product product = null;
                try {
                    product = extract(line);
                    rowsRead++;

                    transform(product);

                    // write out in the required order
                    bw.write(
                        product.productId + "," +
                        product.name + "," +
                        formatMoney(product.price) + "," +
                        product.category + "," +
                        product.priceRange
                    );
                    bw.newLine();
                    rowsTransformed++;
                } catch (Exception e) {
                    rowsSkipped++;
                    // Optionally log which line failed to parse/transform
                    // System.err.println("Skipping line due to error: " + line + " (" + e.getMessage() + ")");
                }
            }
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }

        printSummary(rowsRead, rowsTransformed, rowsSkipped, OUTPUT_PATH);
    }

    /** Extract: parse a CSV row into a Product. */
    private static Product extract(String csvLine) {
        // Simple CSV split—assignment guarantees no commas/quotes in fields.
        String[] parts = csvLine.split(",", -1);
        if (parts.length < 4) {
            throw new IllegalArgumentException("Expected 4 columns, got " + parts.length);
        }
        int productId = Integer.parseInt(parts[0].trim());
        String name = parts[1].trim();
        BigDecimal price = new BigDecimal(parts[2].trim());
        String category = parts[3].trim();
        return new Product(productId, name, price, category);
    }

    /** Transform: apply rules in the specified order. */
    private static void transform(Product p) {
        // 1) Uppercase name
        p.name = p.name.toUpperCase();

        // Keep original category for recategorization rule
        String originalCategory = p.category;

        // 2) Discount if Electronics
        if (equalsIgnoreCaseSafe(originalCategory, "Electronics")) {
            // Apply 10% off
            p.price = p.price.multiply(new BigDecimal("0.90"));
        }

        // Round price to 2 decimals, HALF_UP (do this for consistency on final price)
        p.price = p.price.setScale(2, RoundingMode.HALF_UP);

        // 3) Recategorize if final price > 500.00 and original category was Electronics
        if (equalsIgnoreCaseSafe(originalCategory, "Electronics")
                && p.price.compareTo(new BigDecimal("500.00")) > 0) {
            p.category = "Premium Electronics";
        }

        // 4) PriceRange based on FINAL price
        p.priceRange = computePriceRange(p.price);
    }

    private static String computePriceRange(BigDecimal price) {
        // 0.00–10.00 → Low
        // 10.01–100.00 → Medium
        // 100.01–500.00 → High
        // 500.01 and above → Premium
        BigDecimal ten = new BigDecimal("10.00");
        BigDecimal hundred = new BigDecimal("100.00");
        BigDecimal fiveHundred = new BigDecimal("500.00");

        if (price.compareTo(ten) <= 0) {
            return "Low";
        } else if (price.compareTo(ten) > 0 && price.compareTo(hundred) <= 0) {
            return "Medium";
        } else if (price.compareTo(hundred) > 0 && price.compareTo(fiveHundred) <= 0) {
            return "High";
        } else {
            return "Premium";
        }
    }

    private static String formatMoney(BigDecimal amount) {
        // Ensure two decimal places in output
        return amount.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    private static boolean equalsIgnoreCaseSafe(String a, String b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.equalsIgnoreCase(b);
    }

    private static void printSummary(int read, int transformed, int skipped, String outputPath) {
        System.out.println("Rows read: " + read);
        System.out.println("Rows transformed: " + transformed);
        System.out.println("Rows skipped: " + skipped);
        System.out.println("Output written to: " + outputPath);
    }
    
}

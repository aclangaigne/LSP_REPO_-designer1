
# CSCI 363/540 – Assignment 1: CSV ETL Pipeline in Java

This project implements a simple ETL (Extract–Transform–Load) pipeline in **plain Java** that reads `data/products.csv`, applies transformations, and writes `data/transformed_products.csv` using **relative paths**.

## Project Structure
```
JavaProjectRoot/
├── src/
│   └── org/howard/edu/lsp/assignment2/ETLPipeline.java
├── data/
│   ├── products.csv                 # input
│   └── transformed_products.csv     # output (generated)
└── .vscode/
    └── launch.json                  # ensures VS Code uses project root as working dir
```

## How to Run (VS Code)
1. Install **JDK 17+** (Temurin or Oracle).
2. Install the VS Code extension **Extension Pack for Java**.
3. Open this folder (`JavaProjectRoot`) in VS Code.
4. Confirm `.vscode/launch.json` exists (auto-created here) so the working directory is the project root.
5. Press **Run > Run Without Debugging** (or click the ▶️ on `ETLPipeline.main`).
6. Check `data/transformed_products.csv` for results and the console summary.

### Command Line (alternative)
From the **project root**:
```bash
# compile
javac -d out src/org/howard/edu/lsp/assignment2/ETLPipeline.java

# run
java -cp out org.howard.edu.lsp.assignment2.ETLPipeline
```

## Assumptions & Notes
- Input CSV uses comma delimiter and fields contain no commas/quotes.
- First row is header and not transformed; output always includes header.
- Transform order: UPPERCASE name → 10% discount if Electronics → recategorize if > 500.00 and original category Electronics → compute PriceRange.
- Rounding: prices are rounded to 2 decimals with HALF_UP after any discount.
- Error handling:
  - If `data/products.csv` is missing, the program prints an error and exits.
  - If input is header-only, output still contains just the header row.

## Testing
- Try the included `data/products.csv` (Case A). Compare your output to `data/transformed_products_normal.csv`.
- For the empty input case, temporarily replace `products.csv` with just the header and compare to `data/transformed_products_empty.csv`.

## AI / Internet Sources Disclosure
I used ChatGPT briefly to clarify the assignment instructions and to get a structured outline of how to approach the ETL pipeline (extract to transform to load).

Prompt I asked: Can you explain step by step how to do a CSV ETL pipeline in Java with relative paths?

Response: You’ll want to structure your program with three main methods: Extract (read CSV), Transform (apply uppercase, discount, recategorize, price range), and Load (write CSV). Use relative paths like data/products.csv and data/transformed_products.csv so your code runs from the project root.

I adjusted the actual implementation and comments myself, especially the details for rounding and price range. Then used the outline to organize my code into logical methods. I verified the final behavior by comparing my output to the provided golden files.


Overall Approach
I structured the program into three logical phases: Extract, Transform, and Load. Each phase is handled with a clear method or block of code to keep the program modular and readable. The transformations were applied in the order specified in the assignment instructions to ensure consistent results.

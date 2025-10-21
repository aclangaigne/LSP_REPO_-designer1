package org.howard.edu.lsp.midterm.question2;

public class Main {
  public static void main(String[] args) {

    // Required outputs:
    System.out.println("Circle radius 3.0 \u2192 area = " + AreaCalculator.area(3.0));
    System.out.println("Rectangle 5.0 x 2.0 \u2192 area = " + AreaCalculator.area(5.0, 2.0));
    System.out.println("Triangle base 10, height 6 \u2192 area = " + AreaCalculator.area(10, 6));
    System.out.println("Square side 4 \u2192 area = " + AreaCalculator.area(4));

    // Demonstrate exception handling
    try {
      // this should throw
      AreaCalculator.area(0);
      // If no exception thrown, print an error so behavior is visible
      System.out.println("ERROR: Exception was not thrown for invalid input!");
    } catch (IllegalArgumentException e) {
      System.out.println("Caught error (expected): " + e.getMessage());
    }
  }

  /*
   Short rationale (2-3 sentences):
   Overloading keeps a single logical operation name "area" and lets the compiler differentiate based on parameter types and counts.
   This reduces API surface and can make call sites clearer when the same conceptual operation is performed for different shapes.
  */
}

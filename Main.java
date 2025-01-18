import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(java.util.Locale.US);

        // Λήψη εισόδων από τον χρήστη
        System.out.println("Enter the area of the field (in square meters)(1 hectare = 1000 square meters ): ");
        double area = scanner.nextDouble(); // Ενημερώθηκε σε double

        System.out.println("Enter the labor cost per hour: ");
        double laborCost = scanner.nextDouble();

        System.out.println("Enter the available water for irrigation (in liters )(Average required water amount per hectare: 950000 liters): ");
        double availableWater = scanner.nextDouble();

        // Δημιουργία δεδομένων και εκκίνηση της μηχανής βελτιστοποίησης
        PlantsData plantsData = new PlantsData(area, laborCost, availableWater);
        OptimizationEngine engine = new OptimizationEngine(plantsData);

        try {
            engine.optimizeAndDisplay();
        } catch (Exception e) {
            System.out.println("An error occurred during optimization:");
            e.printStackTrace();
        }
    }
}

import org.apache.commons.math3.optim.*;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class OptimizationEngine {
    private PlantsData plants;
    private PlantsDB plantsDB = new PlantsDB();

    public OptimizationEngine(PlantsData plants) {
        this.plants = plants;
    }

    public String optimizeAndDisplay() {
        StringBuilder results = new StringBuilder();

        double[] profitCoefficients = getProfitCoefficients();
        validateArray(profitCoefficients, "Profit coefficients");
        results.append("Profit coefficients: ").append(Arrays.toString(profitCoefficients)).append("\n");

        double[] areaConstraints = getAreaConstraints();
        validateArray(areaConstraints, "Area constraints");
        double[] waterConstraints = getWaterConstraints();
        validateArray(waterConstraints, "Water constraints");

        LinearObjectiveFunction function = new LinearObjectiveFunction(profitCoefficients, 0);
        List<LinearConstraint> constraints = new ArrayList<>();
        constraints.add(new LinearConstraint(areaConstraints, Relationship.LEQ, plants.getArea()));
        constraints.add(new LinearConstraint(waterConstraints, Relationship.LEQ, plants.getAvailableIrrigation()));

        // Περιορισμός στο μέγιστο αριθμό ενός φυτού (π.χ. Apples)
        constraints.add(new LinearConstraint(new double[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, Relationship.LEQ, 10));

        // Περιορισμός για τουλάχιστον 2 διαφορετικά φυτά
        constraints.add(new LinearConstraint(new double[] { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, Relationship.GEQ, 2));

        SimplexSolver solver = new SimplexSolver();
        try {
            PointValuePair solution = solver.optimize(
                    new MaxIter(1000),
                    function,
                    new LinearConstraintSet(constraints),
                    GoalType.MAXIMIZE,
                    new NonNegativeConstraint(true));

            if (solution == null) {
                throw new RuntimeException("Optimization failed: No valid solution found.");
            }

            double[] plantQuantities = solution.getPoint();
            double totalProfit = solution.getValue();

            results.append("\nOptimal Planting Configuration:\n");
            results.append(
                    String.format("%-15s %-15s %-15s %-15s\n", "Plant", "Quantity", "Used Area (m²)", "Profit (€)"));
            double totalAreaUsed = 0.0;
            double totalWaterUsed = 0.0;

            for (int i = 0; i < plantsDB.getNumberOfPlants(); i++) {
                if (plantQuantities[i] > 0) {
                    double usedArea = plantQuantities[i] * plantsDB.plantsFixedData[i][0];
                    double usedWater = plantQuantities[i] * plantsDB.plantsFixedData[i][1];
                    double plantProfit = plantQuantities[i] * profitCoefficients[i];

                    totalAreaUsed += usedArea;
                    totalWaterUsed += usedWater;

                    results.append(String.format(Locale.US, "%-15s %-15.2f %-15.2f %-15.2f\n",
                            PlantsDB.plantsNames[i], plantQuantities[i], usedArea, plantProfit));
                }
            }

            results.append("\nConstraints Used:\n");
            results.append(String.format(Locale.US, "Total Area Used: %.2f m² (of %.2f m²)\n", totalAreaUsed,
                    plants.getArea()));
            results.append(String.format(Locale.US, "Total Water Used: %.2f liters (of %.2f liters)\n", totalWaterUsed,
                    plants.getAvailableIrrigation()));
            results.append(String.format(Locale.US, "\nTotal Profit: %.2f €\n", totalProfit));

        } catch (Exception e) {
            results.append("An error occurred during optimization: ").append(e.getMessage()).append("\n");
        }

        return results.toString();
    }

    private double[] getProfitCoefficients() {
        double[] profits = new double[plantsDB.getNumberOfPlants()];
        for (int i = 0; i < plantsDB.getNumberOfPlants(); i++) {
            double Ri = plantsDB.plantsFixedData[i][3];
            double Ci = plantsDB.plantsFixedData[i][2];
            double Cl = plants.getLaborCost();
            double Hi = plantsDB.plantsFixedData[i][4];
            double Wa = plantsDB.plantsFixedData[i][1];
            double Cw = 0.0003; // Αυξημένο κόστος νερού

            profits[i] = Ri - Ci - (Cl * Hi) - (Wa * Cw);
            if (profits[i] < 0) {
                profits[i] = 0;
            }
        }
        return profits;
    }

    private double[] getAreaConstraints() {
        return Arrays.stream(plantsDB.plantsFixedData)
                .mapToDouble(plant -> plant[0])
                .toArray();
    }

    private double[] getWaterConstraints() {
        return Arrays.stream(plantsDB.plantsFixedData)
                .mapToDouble(plant -> plant[1])
                .toArray();
    }

    private void validateArray(double[] array, String arrayName) {
        if (array == null || array.length == 0) {
            throw new RuntimeException(arrayName + " is null or empty.");
        }
        if (Arrays.stream(array).anyMatch(Double::isNaN)) {
            throw new RuntimeException(arrayName + " contains NaN values.");
        }
        if (Arrays.stream(array).anyMatch(value -> value < 0)) {
            throw new RuntimeException(arrayName + " contains negative values.");
        }
    }
}

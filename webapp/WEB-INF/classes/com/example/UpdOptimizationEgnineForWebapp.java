package com.example;

import org.apache.commons.math3.optim.*;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OptimizationEngine {
    private PlantsData plants;
    private PlantsDB plantsDB = new PlantsDB();

    public OptimizationEngine(PlantsData plants) {
        this.plants = plants;
    }

    public Map<String, Object> optimizeAndDisplay() {
        Map<String, Object> results = new HashMap<>();
        List<Map<String, Object>> plantResults = new ArrayList<>();

        try {
            double[] profitCoefficients = getProfitCoefficients();
            validateArray(profitCoefficients, "Profit coefficients");

            double[] areaConstraints = getAreaConstraints();
            validateArray(areaConstraints, "Area constraints");
            double[] waterConstraints = getWaterConstraints();
            validateArray(waterConstraints, "Water constraints");

            LinearObjectiveFunction function = new LinearObjectiveFunction(profitCoefficients, 0);
            List<LinearConstraint> constraints = new ArrayList<>();
            constraints.add(new LinearConstraint(areaConstraints, Relationship.LEQ, plants.getArea()));
            constraints.add(new LinearConstraint(waterConstraints, Relationship.LEQ, plants.getAvailableIrrigation()));

            SimplexSolver solver = new SimplexSolver();
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

            double totalAreaUsed = 0.0;
            double totalWaterUsed = 0.0;

            for (int i = 0; i < plantsDB.getNumberOfPlants(); i++) {
                if (plantQuantities[i] > 0) { // Include only plants with quantity > 0
                    int usedArea = (int) (plantQuantities[i] * plantsDB.plantsFixedData[i][0]);
                    int usedWater = (int) (plantQuantities[i] * plantsDB.plantsFixedData[i][1]);
                    int plantProfit = (int) (plantQuantities[i] * profitCoefficients[i]);

                    totalAreaUsed += usedArea;
                    totalWaterUsed += usedWater;

                    Map<String, Object> plantData = new HashMap<>();
                    plantData.put("name", PlantsDB.plantsNames[i]);
                    plantData.put("quantity", (int) plantQuantities[i]);
                    plantData.put("areaUsed", usedArea);
                    plantData.put("profit", plantProfit);

                    plantResults.add(plantData);
                }
            }

            // Add overall results
            results.put("plants", plantResults);
            results.put("quantity", plantsDB.getNumberOfPlants());
            results.put("totalAreaUsed", totalAreaUsed);
            results.put("totalProfit", totalProfit);

        } catch (Exception e) {
            results.put("error", "An error occurred during optimization: " + e.getMessage());
            e.printStackTrace();
        }

        return results;
    }

    private double[] getProfitCoefficients() {
        double[] profits = new double[plantsDB.getNumberOfPlants()];
        for (int i = 0; i < plantsDB.getNumberOfPlants(); i++) {
            double Ri = plantsDB.plantsFixedData[i][3];
            double Ci = plantsDB.plantsFixedData[i][2];
            double Cl = plants.getLaborCost();
            double Hi = plantsDB.plantsFixedData[i][4];
            double Wa = plantsDB.plantsFixedData[i][1];
            double Cw = 0.0002;

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

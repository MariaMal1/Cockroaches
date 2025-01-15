package com.example;

import java.util.Map;

public class ReportGenerator {
    private String[] plantNames;
    private double[][] plantData;

    public ReportGenerator(String[] plantNames, double[][] plantData) {
        this.plantNames = plantNames;
        this.plantData = plantData;
    }

    public void generateReport(Map<String, Integer> solution, Map<String, Double> constraints) {
        double totalLaborHours = 0;
        double totalWaterRequired = 0;
        double totalAreaUsed = 0;

        System.out.println("Optimal Planting Configuration:");

        for (Map.Entry<String, Integer> entry : solution.entrySet()) {
            String plantName = entry.getKey();
            int quantity = entry.getValue();
            int plantIndex = getPlantIndex(plantName);

            if (plantIndex != -1 && quantity > 0) {
                double waterRequired = plantData[plantIndex][0] * quantity;
                double landRequired = plantData[plantIndex][1] * quantity;
                double laborRequired = plantData[plantIndex][2] * quantity;

                totalWaterRequired += waterRequired;
                totalAreaUsed += landRequired;
                totalLaborHours += laborRequired;

                System.out.printf("%-15s: Quantity: %d, Area Used: %.2f, Water Required: %.2f, Labor Hours: %.2f\n",
                        plantName, quantity, landRequired, waterRequired, laborRequired);
            }
        }

        System.out.println("\nSummary:");
        System.out.printf("Total Area Used: %.2f m²\n", totalAreaUsed);
        System.out.printf("Total Water Required: %.2f liters\n", totalWaterRequired);
        System.out.printf("Total Labor Hours: %.2f hours\n", totalLaborHours);

        System.out.println("Constraints:");
        System.out.printf("Available Land: %.2f m²\n", constraints.get("land"));
        System.out.printf("Available Water: %.2f liters\n", constraints.get("water"));
        System.out.printf("Available Labor: %.2f hours\n", constraints.get("labor"));

        if (totalAreaUsed > constraints.get("land")) {
            System.out.println("Warning: Exceeded land constraint!");
        }
        if (totalWaterRequired > constraints.get("water")) {
            System.out.println("Warning: Exceeded water constraint!");
        }
        if (totalLaborHours > constraints.get("labor")) {
            System.out.println("Warning: Exceeded labor constraint!");
        }
    }

    private int getPlantIndex(String plantName) {
        for (int i = 0; i < plantNames.length; i++) {
            if (plantNames[i].equals(plantName)) {
                return i;
            }
        }
        return -1;
    }
}

import java.util.ArrayList;
import java.util.List;

public class PlantsDB {
    public static final String[] plantsNames = {
            "Apples", "Oranges", "Peaches", "Pears", "Grapes",
            "Kiwis", "Olives", "Figs", "Pomegranates", "Lemons"
    };
    public static final double[][] plantsFixedData = {
            // area (m²), water (liters/tree), cost (€/unit), revenue (€/unit), labor
            // (hours/unit)
            { 6.5, 80, 7, 130, 1.8 }, // Apples: 80 λίτρα ανά δέντρο
            { 3.75, 100, 8, 90, 1.5 }, // Oranges: 100 λίτρα ανά δέντρο
            { 4.50, 70, 6, 80, 1.7 }, // Peaches: 70 λίτρα ανά δέντρο
            { 5.0, 75, 6.5, 85, 1.9 }, // Pears: 75 λίτρα ανά δέντρο
            { 3.15, 50, 3, 40, 1.2 }, // Grapes: 50 λίτρα ανά δέντρο
            { 3.25, 120, 12, 70, 1.8 }, // Kiwis: 120 λίτρα ανά δέντρο
            { 6.5, 60, 9, 80, 1.6 }, // Olives: 60 λίτρα ανά δέντρο
            { 6.5, 40, 5, 110, 1.3 }, // Figs: 40 λίτρα ανά δέντρο
            { 4.5, 85, 6, 60, 1.1 }, // Pomegranates: 85 λίτρα ανά δέντρο
            { 5.0, 90, 8, 75, 1.8 } // Lemons: 90 λίτρα ανά δέντρο
    };

    public int getNumberOfPlants() {
        if (plantsNames.length != plantsFixedData.length) {
            throw new RuntimeException("Mismatch between plant names and data.");
        }
        return plantsNames.length;
    }

    public static List<double[]> getPlantData() {
        List<double[]> data = new ArrayList<>();
        for (double[] plant : plantsFixedData) {
            data.add(plant);
        }
        return data;
    }

    public static double[] getPlantDataByName(String plantName) {
        for (int i = 0; i < plantsNames.length; i++) {
            if (plantsNames[i].equalsIgnoreCase(plantName)) {
                return plantsFixedData[i];
            }
        }
        throw new RuntimeException("Plant name not found: " + plantName);
    }
}

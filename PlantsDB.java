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
            { 33, 15000 , 24.5, 95, 0.8  }, // Apples: 80 λίτρα ανά δέντρο
            { 30, 14000, 21.5, 63, 1.5 }, // Oranges: 100 λίτρα ανά δέντρο
            { 30, 16000, 24.5, 72, 1.1 }, // Peaches: 70 λίτρα ανά δέντρο
            { 27, 14000, 19.5, 93, 0.7 }, // Pears: 75 λίτρα ανά δέντρο
            { 6, 6000, 12, 40 , 0.125 }, // Grapes: 50 λίτρα ανά δέντρο
            { 30, 17000, 28.5, 100 , 1.3 }, // Kiwis: 120 λίτρα ανά δέντρο
            { 36, 13100, 16.5, 70, 0.9}, // Olives: 60 λίτρα ανά δέντρο
            { 30, 12000, 19.5, 97, 0.9 }, // Figs: 40 λίτρα ανά δέντρο
            { 30, 14000, 19.5, 87, 0.9 }, // Pomegranates: 85 λίτρα ανά δέντρο
            { 20, 14000, 21.5, 105, 0.7 } // Lemons: 90 λίτρα ανά δέντρο
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

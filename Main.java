import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    public static class OptimizationResult {
        private final String area;
        private final String laborCost;
        private final String water;
        private final String result;

        public OptimizationResult(String area, String laborCost, String water, String result) {
            this.area = area;
            this.laborCost = laborCost;
            this.water = water;
            this.result = result;
        }

        public String getArea() {
            return area;
        }

        public String getLaborCost() {
            return laborCost;
        }

        public String getWater() {
            return water;
        }

        public String getResult() {
            return result;
        }
    }

    private final ObservableList<OptimizationResult> optimizationResults = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        // Αρχική σελίδα
        Label areaLabel = new Label("Enter the area of the field (in square meters):");
        TextField areaField = new TextField();

        Label laborLabel = new Label("Enter the labor cost per hour:");
        TextField laborField = new TextField();

        Label waterLabel = new Label("Enter the available water for irrigation (in liters):");
        TextField waterField = new TextField();

        Button calculateButton = new Button("Optimize");
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefWidth(400);
        resultArea.setPrefHeight(200);

        // Ενότητα με παραδείγματα εισόδων
        Label examplesLabel = new Label("Example inputs (not applied automatically):");
        examplesLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-weight: bold;");
        TextArea examplesArea = new TextArea(
                "Example 1:\n" +
                        "Area: 10000 m²\n" +
                        "Labor Cost: 5 €/hour\n" +
                        "Water: 50000 liters\n\n" +
                        "Example 2:\n" +
                        "Area: 2000 m²\n" +
                        "Labor Cost: 10 €/hour\n" +
                        "Water: 75000 liters\n\n" +
                        "Example 3:\n" +
                        "Area: 3000 m²\n" +
                        "Labor Cost: 15 €/hour\n" +
                        "Water: 100000 liters\n");
        examplesArea.setEditable(false);
        examplesArea.setPrefHeight(150);
        examplesArea.setStyle("-fx-control-inner-background: #e8f5e9; -fx-border-color: #2e7d32;");

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(20));
        layout.setHgap(10);
        layout.setVgap(10);

        layout.add(areaLabel, 0, 0);
        layout.add(areaField, 1, 0);
        layout.add(laborLabel, 0, 1);
        layout.add(laborField, 1, 1);
        layout.add(waterLabel, 0, 2);
        layout.add(waterField, 1, 2);
        layout.add(calculateButton, 1, 3);
        layout.add(resultArea, 0, 4, 2, 1);
        layout.add(examplesLabel, 0, 5);
        layout.add(examplesArea, 0, 6, 2, 1);

        String sharedStyle = "-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-background-color: #d4f4dd;";
        layout.setStyle(sharedStyle);
        areaLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-weight: bold;");
        laborLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-weight: bold;");
        waterLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-weight: bold;");
        areaField.setStyle("-fx-background-color: #f0fff4; -fx-border-color: #2e7d32;");
        laborField.setStyle("-fx-background-color: #f0fff4; -fx-border-color: #2e7d32;");
        waterField.setStyle("-fx-background-color: #f0fff4; -fx-border-color: #2e7d32;");
        calculateButton.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white; -fx-font-weight: bold;");

        Scene originalScene = new Scene(layout, 800, 600);

        // Δεύτερη σελίδα
        TableView<OptimizationResult> resultsTable = new TableView<>(optimizationResults);
        resultsTable.setPrefWidth(1000);
        resultsTable.setPrefHeight(500);

        TableColumn<OptimizationResult, String> areaColumn = new TableColumn<>("Area");
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));

        TableColumn<OptimizationResult, String> laborCostColumn = new TableColumn<>("Labor Cost");
        laborCostColumn.setCellValueFactory(new PropertyValueFactory<>("laborCost"));

        TableColumn<OptimizationResult, String> waterColumn = new TableColumn<>("Water");
        waterColumn.setCellValueFactory(new PropertyValueFactory<>("water"));

        TableColumn<OptimizationResult, String> resultColumn = new TableColumn<>("Result");
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));

        resultsTable.getColumns().addAll(areaColumn, laborCostColumn, waterColumn, resultColumn);

        VBox newLayout = new VBox(10);
        newLayout.setPadding(new Insets(20));
        newLayout.setStyle(sharedStyle);

        Label newScreenLabel = new Label("Optimization Results:");
        newScreenLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-weight: bold;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white; -fx-font-weight: bold;");

        newLayout.getChildren().addAll(newScreenLabel, resultsTable, backButton);

        // Αύξηση των διαστάσεων της δεύτερης σκηνής
        Scene newScene = new Scene(newLayout, 1200, 800);

        // Συμπεριφορά κουμπιών
        calculateButton.setOnAction(event -> {
            try {
                double area = Double.parseDouble(areaField.getText());
                double laborCost = Double.parseDouble(laborField.getText());
                double availableWater = Double.parseDouble(waterField.getText());

                PlantsData plantsData = new PlantsData(area, laborCost, availableWater);
                OptimizationEngine engine = new OptimizationEngine(plantsData);

                String results = engine.optimizeAndDisplay();
                resultArea.setText(results);

                optimizationResults.add(new OptimizationResult(
                        areaField.getText(),
                        laborField.getText(),
                        waterField.getText(),
                        results));

                primaryStage.setScene(newScene);
                primaryStage.setWidth(1200);
                primaryStage.setHeight(800);
            } catch (NumberFormatException e) {
                resultArea.setText("Invalid input! Please enter numeric values.");
            } catch (Exception e) {
                resultArea.setText("An error occurred: " + e.getMessage());
            }
        });

        backButton.setOnAction(event -> {
            primaryStage.setScene(originalScene);
            primaryStage.setWidth(800);
            primaryStage.setHeight(600);
        });

        primaryStage.setTitle("Farm Optimization");
        primaryStage.setScene(originalScene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

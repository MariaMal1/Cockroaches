<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.*" %>
<%@ page language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Optimization Results</title>
    <style>
        /* Same styles as home.jsp for consistency */
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f9; }
        .container { width: 90%; max-width: 800px; margin: 20px auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); }
        h1 { text-align: center; }
        .results table { width: 100%; border-collapse: collapse; }
        .results table th, .results table td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        .results table th { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Optimization Results</h1>
        <div class="results">
            <% 
                String areaParam = request.getParameter("area");
                String laborCostParam = request.getParameter("laborCost");
                String waterParam = request.getParameter("availableWater");

                int quantity = 0;
                Double profit = 0.0;
                Double aread = 0.0;

                if (areaParam != null && laborCostParam != null && waterParam != null) {
                    try {
                        // Parse input values
                        int area = Integer.parseInt(areaParam);
                        double laborCost = Double.parseDouble(laborCostParam);
                        int availableWater = Integer.parseInt(waterParam);
                         

                        // Create PlantsData object and run optimization
                        PlantsData plantsData = new PlantsData(area, laborCost, availableWater);
                        OptimizationEngine engine = new OptimizationEngine(plantsData);

                        // Call optimization logic
                        Map<String, Object> results = engine.optimizeAndDisplay();
                        List<Map<String, Object>> plants = (List<Map<String, Object>>) results.get("plants");
                         aread =  (Double) results.get("totalAreaUsed"); %>
                         
                         quantity = (int) results.get("quantity");
                         profit =  (Double) results.get("totalProfit");
                        %>

                        <table>
                            <tr>
                                <th>Plant</th>
                                <th>Quantity</th>
                                <th>Area Used</th>
                                <th>Profit</th>
                            </tr>
                            <% for (Map<String, Object> plant : plants) { %>
                                <tr>
                                    <td><%= plant.get("name") %></td>
                                    <td><%= quantity %></td>
                                    <td><%= aread %></td>
                                    <td>€<%= profit %></td>
                                </tr>
                            <% } %>
                        </table>
                        <% 
                    } catch (Exception e) {
                        out.println("<p>Error during optimization: " + e.getMessage() + "</p>");
                    } finally {
                        out.println(" "); // Placeholder for finally block output
                    }
                } else {
                    out.println("<p>No input data provided. Please return to the home page and try again.</p>");
                }
            %>
        </div>
    </div>
</body>
</html>
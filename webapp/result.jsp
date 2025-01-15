//Για να τρέξει η jsp στο vscode κατέβασα την εφαρμογή tomcat

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
    <h1>Optimization Results</h1>

    <%
    PlantsData plantsData = new PlantsData(
        Double.parseDouble(request.getParameter("area")),
        Double.parseDouble(request.getParameter("laborCost")),
        Double.parseDouble(request.getParameter("availableWater")));

        OptimizationEngine engine = new OptimizationEngine(plantsData);
        Map<String, Object> results = null;

        try {
            results = engine.optimizeAndDisplay();
        } catch (Exception e) {
            out.println("<p style='color: red;'>Error during processing: " + e.getMessage() + "</p>");
        }
    %>

    <c:if test="${results != null}">
        <table style="width: 100%; border-collapse: collapse; margin-top: 20px; font-family: Arial, sans-serif;">
            <thead>
                <tr style="background-color: #f2f2f2; text-align: left;">
                    <th style="border: 1px solid #ddd; padding: 10px;">Plant Name</th>
                    <th style="border: 1px solid #ddd; padding: 10px;">Quantity</th>
                    <th style="border: 1px solid #ddd; padding: 10px;">Used Area</th>
                    <th style="border: 1px solid #ddd; padding: 10px;">Profit (€)</th>
                </tr>
            </thead>
            <tbody>
                <% 
                if (results != null) { 
                    List<Map<String, Object>> plants = (List<Map<String, Object>>) results.get("plants");
                    for (Map<String, Object> plant : plants) { 
                %>
                <%
                String backgroundColor = "#fbeaea"; // Default color (red for quantity <= 0)
                if (plant.get("quantity") != null && (int) plant.get("quantity") > 0) {
                    backgroundColor = "#eaf8e6"; // Green for quantity > 0
                }
            %>
            <tr style="border: 1px solid #ddd; background-color: backgroundColor;">
                        <td style="border: 1px solid #ddd; padding: 10px;"><%= plant.get("name") %></td>
                        <td style="border: 1px solid #ddd; padding: 10px; text-align: center;"><%= plant.get("quantity") %></td>
                        <td style="border: 1px solid #ddd; padding: 10px; text-align: center;"><%= plant.get("areaUsed") %> m²</td>
                        <td style="border: 1px solid #ddd; padding: 10px; text-align: right;">€<%= plant.get("profit") %></td>
                    </tr>
                <% 
                    } 
                } else { 
                %>
                    <tr>
                        <td colspan="4" style="border: 1px solid #ddd; padding: 10px; text-align: center; font-style: italic;">
                            No data available.
                        </td>
                    </tr>
                <% 
                } 
                %>
            </tbody>
        </table>

        <h2>Overall Results</h2>
        <p>Total Area Used: <%= results.get("totalAreaUsed") %></p>
        <p>Total Profit: €<%= results.get("totalProfit") %></p>
    </c:if>
</body>
</html>

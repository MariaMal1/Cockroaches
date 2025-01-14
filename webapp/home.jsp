<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Plant Optimization</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
        }
        .container {
            width: 90%;
            max-width: 800px;
            margin: 20px auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"] {
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            background-color: #28a745;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Plant Optimization</h1>
        <form method="post" action="result.jsp">
            <div class="form-group">
                <label for="area">Field Area (m&sup2;):</label>
                <input type="text" id="area" name="area" required>
            </div>
            <div class="form-group">
                <label for="laborCost">Labor Cost per Hour (&euro;):</label>
                <input type="text" id="laborCost" name="laborCost" required>
            </div>
            <div class="form-group">
                <label for="availableWater">Available Water (liters):</label>
                <input type="text" id="availableWater" name="availableWater" required>
            </div>
            <button type="submit">Optimize</button>
        </form>
    </div>
</body>
</html>

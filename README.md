# Cockroaches - Farm Optimization Program

Welcome to **Cockroaches**, a Java-based program designed to help farmers optimize the use of their resources for maximum profit. This program takes input about the size of the farm, available water, and labor costs, and calculates the optimal amount of each plant to cultivate based on the given constraints.

## Features
#### User Input:

- Field size in square meters.
- Available water for irrigation in liters.
- Labor cost per hour in euros.
#### Optimization:
- Calculates the optimal number of plants for each crop type based on user input and predefined plant data.
- Considers factors like planting area, labor hours, water usage, and associated costs for each plant type.
- Maximizes total profit for the farmer.

## Technology Stack
- Programming Language: Java
- Optimization Library: Apache Commons Math3
- IDE/Tools: Compatible with IntelliJ IDEA, Eclipse, or any Java-based IDE.


## Installation Instructions
#### Prerequisites
1. Install Java JDK 8+.
2. Install Maven from (https://maven.apache.org/install.html)  
3. Add the Apache Commons Math3 library to your project.
4. Clone or download the repository:
   ```bash
   git clone https://github.com/username/cockroaches.git

5. Open the files in your favorite Java IDE (e.g., IntelliJ IDEA, Eclipse).
6. Navigate to the project directory and clean-build the project:  
   mvn clean install    
 7. Run the programm using the code bellow....
    -  mvn compile
    -  mvn dependency:copy-dependencies
    -  mvn package
    -  mvn exec:java -Dexec.mainClass="com.example.Main"  

## Project Structure   
#### Below is an overview of the repository structure and the role of each file:
cockroaches/  
├── Main.java               # Entry point of the program  
├── OptimizationEngine.java # Contains the optimization logic using Apache Commons Math3  
├── PlantsData.java         # Handles user input and stores field-specific data  
├── PlantsDB.java           # Defines fixed data for various plant types  
├── ReportGenerator.java    # Generates detailed output reports  
└── README.md               # Documentation of the project  

## Key Classes and Functions
1. Main
- Handles user input and orchestrates the execution of the optimization and reporting modules.
2. OptimizationEngine
- Core logic for calculating the optimal planting configuration using the SimplexOptimizer from Apache Commons Math3.
3. PlantsData
- Stores field-related information like area, labor cost, and water availability.
4. PlantsDB
- Stores fixed data for plant types, including spacing, labor hours, water requirements, costs, and revenues.
5. ReportGenerator
- Compiles the results of the optimization and outputs a detailed summary, including profit and usage metrics.



## Running Example:
#### Example Input:
Enter the area of the field (in square meters): 5000  
Enter the labor cost per hour: 10.5  
Enter the available water for irrigation (in liters): 25000 

#### Example Output

Optimal Planting Configuration:  
Apples: 200  
  Area: 900.0 m²  
  Labor Hours: 444.0  
  Water Required: 28000.0 liters  
  Cost: 1800.0  
  Revenue: 9100.0  
[...]

Summary:  
Total Area Used: 4500.0 m²  
Total Labor Hours: 200.0  
Total Water Required: 24000.0 liters  
Total Water Cost: 4.8  
Total Labor Cost: 2100.0  
Total Cost: 3904.8  
Total Revenue: 5000.0  
Total Profit: 1095.2  

## License
This project is licensed under the MIT License. See the LICENSE file for details.

## Acknowledgments & Contributing
Dear user thank you for your interest in our program! We’re thrilled that you’ve taken the time to explore Cockroaches and hope it helps you optimize your farming processes effectively. Your feedback and suggestions are always welcome, as they help us improve and grow. We appreciate your support and hope you enjoy using our program!"  
If you'd like to contribute, feel free to submit a pull request or open an issue.

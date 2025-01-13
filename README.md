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

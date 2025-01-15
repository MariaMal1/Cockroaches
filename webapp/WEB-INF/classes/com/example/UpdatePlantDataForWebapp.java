package com.example;

public class PlantsData {
    private double area;
    private double laborCost;
    private double availableIrrigation;

    public PlantsData(double area, double laborCost, double availableIrrigation) {
        this.area = area;
        this.laborCost = laborCost;
        this.availableIrrigation = availableIrrigation;
    }

    public double getArea() {
        return area;
    }

    public double getLaborCost() {
        return laborCost;
    }

    public double getAvailableIrrigation() {
        return availableIrrigation;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public void setLaborCost(double laborCost) {
        this.laborCost = laborCost;
    }

    public void setAvailableIrrigation(double availableIrrigation) {
        this.availableIrrigation = availableIrrigation;
    }
}

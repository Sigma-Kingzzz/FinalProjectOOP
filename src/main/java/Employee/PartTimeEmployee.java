/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Employee;

/**
 *
 * @author haziq
 */
public class PartTimeEmployee extends Employee {
    private double hourlyRate;
    private int hoursWorked;

    // Constructor
    public PartTimeEmployee(String employeeID, String name, double hourlyRate, String status,int hoursWorked) {
        // Pass 0 for basicSalary since part-timers are paid hourly
        super(employeeID, name, 0.0, status); 
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    // Getters and Setters
    public double getHourlyRate() { 
        return hourlyRate; 
    }
    public void setHourlyRate(double hourlyRate) { 
        this.hourlyRate = hourlyRate; 
    }

    public int getHoursWorked() { 
        return hoursWorked; 
    }
    public void setHoursWorked(int hoursWorked) { 
        this.hoursWorked = hoursWorked; 
    }

    // Overriding the abstract method (Polymorphism)
    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }
}


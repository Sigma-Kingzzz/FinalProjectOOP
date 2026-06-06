/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Employee;

/**
 *
 * @author haziq
 */
public class FullTimeEmployee extends Employee {
    private double benefits;
    
    // Constructor
    public FullTimeEmployee(int employeeID, String name, double basicSalary, double benefits) {
        // 'super' calls the parent (Employee) constructor
        super(employeeID, name, basicSalary); 
        this.benefits = benefits;
    }

    // Getter and Setter for the unique attribute
    public double getBenefits() { 
        return benefits; 
    }
    public void setBenefits(double benefits) { 
        this.benefits = benefits; 
    }

    // Overriding the abstract method (Polymorphism)
    @Override
    public double calculateSalary() {
        // monthlySalary is the basicSalary + benefits
        return getBasicSalary() + benefits;
    }
}

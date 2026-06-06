/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Employee;

/**
 *
 * @author haziq
 */
public abstract class Employee {
    private String employeeID;
    private String name;
    private double basicSalary;
    private String status;
    
    //Constructor
    public Employee(String employeeID, String name, double basicSalary){
        this.employeeID=employeeID;
        this.name=name;
        this.basicSalary=basicSalary;
    }
    
    //Getters and Setters 
    public String getEmployeeID(){
        return employeeID;
    }
    public void setEmployeeID(String employeeID){
        this.employeeID=employeeID;
    }
    
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    
    public double getBasicSalary(){
        return basicSalary;
    }
    public void setbasicSalary(double basicSalary){
        this.basicSalary=basicSalary;
    }
    public abstract double calculateSalary();
}


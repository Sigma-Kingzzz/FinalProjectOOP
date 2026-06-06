/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Employee;

/**
 *
 * @author haziq
 */
public class TestEmp {
    public static void main(String[] args){
        // Testing FullTimeEmployee
        Employee emp1 = new FullTimeEmployee(101, "Ali", 3000.00, 500.00);
        System.out.println("Name: " + emp1.getName());
        System.out.println("Full-Time Salary: RM " + emp1.calculateSalary());

        System.out.println("--------------------");

        // Testing PartTimeEmployee
        Employee emp2 = new PartTimeEmployee(102, "Sarah", 15.00, 40);
        System.out.println("Name: " + emp2.getName());
        System.out.println("Part-Time Salary: RM " + emp2.calculateSalary());
        
    }
}

import Employee.Employee;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author muhai
 */
public class DeleteUser extends UserManagement{
    private String employeeId; // Renamed variable from staffId to match your project terminology
    
    public DeleteUser(String employeeId){
        this.employeeId = employeeId;
    }
    
    // CHANGED: Accepts an ArrayList of generic Employees
    @Override
    public void execute(ArrayList<Employee> staffList){
        for(Employee s : staffList){
            // CHANGED: Use getEmployeeID() instead of getStaffId()
            if(s.getEmployeeID().equals(employeeId)){
                staffList.remove(s); // Directly removes the found employee object
                System.out.println("Employee Deleted Successfully.");
                return; // Stop the loop immediately after deleting
            }
        }
        System.out.println("Employee ID not found.");
    }
}

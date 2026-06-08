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
public class UpdateUser extends UserManagement {
    private Employee updateStaff;
    public UpdateUser(Employee updateStaff){
        this.updateStaff = updateStaff;
    }
    @Override
    public void execute(ArrayList<Employee> staffList){
        for(int i = 0; i < staffList.size(); i++){
            if(staffList.get(i).getEmployeeID().equals(updateStaff.getEmployeeID())){
                // Swaps old FullTime/PartTime employee with the newly updated object configuration
                staffList.set(i, updateStaff); 
                System.out.println("Employee Updated Successfully.");
                return;
            }
        }
        System.out.println("Employee to update not found.");
    }
}

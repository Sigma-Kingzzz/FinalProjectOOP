
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
    private String staffId;
    private double salary;
    public UpdateUser(String staffId, double salary){
        this.staffId = staffId;
        this.salary = salary;
    }
    @Override
    public void execute(ArrayList<Staff> StaffList){
        for(Staff s : StaffList){
            if(s.getStaffId().equals(staffId)){
                s.setSalary(salary);
                return;
            }
        }
    }
}

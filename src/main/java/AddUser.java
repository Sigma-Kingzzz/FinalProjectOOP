/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.ArrayList;
/**
 *
 * @author muhai
 */
public class AddUser extends UserManagement {
    
    private Staff newStaff;
    
    public AddUser(Staff newStaff){
        this.newStaff = newStaff;
    }
    @Override
    public void execute(ArrayList<Staff> StaffList){
        for(Staff s : StaffList){
            if(s.getStaffId().equals(newStaff.getStaffId())){
                System.out.println("Staff ID " + newStaff.getStaffId() + " Already Exists. ");
                return;
            }
        }
        
        StaffList.add(newStaff);
        System.out.println("Staff Added Successfully. ");
    }
}

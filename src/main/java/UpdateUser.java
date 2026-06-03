
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
    private Staff updateStaff;
    public UpdateUser(Staff updateStaff){
        this.updateStaff = updateStaff;
    }
    @Override
    public void execute(ArrayList<Staff> StaffList){
        for(Staff s : StaffList){
            if(s.getStaffId().equals(updateStaff.getStaffId())){
                s.setStaffName(updateStaff.getStaffName());
                s.setSalary(updateStaff.getSalary());
                return;
            }
        }
    }
}

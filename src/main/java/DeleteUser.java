
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
    private String staffId;
    public DeleteUser(String staffId){
        this.staffId = staffId;
    }
    @Override
    public void execute(ArrayList<Staff> StaffList){
        int index;
        for(Staff s : StaffList){
            if(s.getStaffId().equals(staffId)){
                index = StaffList.indexOf(s);
                StaffList.remove(index);
                return;
            }
        }
        System.out.println("Delete User. ");
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.ArrayList;
/**
 *
 * @author muhai
 */
public class test {
    public static void main(String[] args){
        //UpdateUser update = new UpdateUser("D01", 1000);
        ArrayList<Staff> staffList = new ArrayList();
        staffList.add(new Staff("Haikal", "D01", 100));
        staffList.add(new Staff("Akmal", "D02", 200));
        staffList.add(new Staff("Ammar", "D03", 1000));
        
        ShowStaff(staffList);

        //update.execute(staffList);
        //ShowStaff(staffList);

    }
    
    public static void ShowStaff(ArrayList<Staff> staffList){
        for(Staff s : staffList){
            System.out.println(s.getStaffId() + " " + s.getStaffName() + " " + String.format("%.2f", s.getSalary()));
        }
    }
}

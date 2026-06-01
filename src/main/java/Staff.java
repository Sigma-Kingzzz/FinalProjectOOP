/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author muhai
 */
public class Staff {
    private String staffName;
    private String staffId;
    private double salary;
    public Staff(String staffName, String staffId, double salary){
        this.staffName = staffName;
        this.staffId = staffId;
        this.salary = salary;
    }
    
    public void setStaffName(String name){
        this.staffName = name;
    }
    
    public void setStaffId(String Id){
        this.staffId = Id;
    }
    
    public void setSalary(double salary){
        this.salary = salary;
    }
    
    public String getStaffId(){
        return staffId;
    }
    
    public String getStaffName(){
        return staffName;
    }
    
    public double getSalary(){
        return salary;
    }
}

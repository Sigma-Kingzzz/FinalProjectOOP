
import Employee.Employee;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AddUser extends UserManagement {
    
    private Employee newStaff;
    
    public AddUser(Employee newStaff){
        this.newStaff = newStaff;
    }
    @Override
     public void execute(ArrayList<Employee> staffList){
         for(Employee s : staffList){
             // CHANGED: Use getEmployeeID() instead of getStaffId()
             if(s.getEmployeeID().equals(newStaff.getEmployeeID())){
                 Alert alert = new Alert(AlertType.ERROR);
                 alert.setTitle("Error");
                 alert.setHeaderText("Duplicate Employee ID");
                 alert.setContentText("Employee ID " + newStaff.getEmployeeID() + " already exists.");
                 alert.showAndWait();
                 return;
             }
         }
        
        staffList.add(newStaff);
        System.out.println("Staff Added Successfully. ");
    }
}


import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AddUser extends UserManagement {
    
    private Staff newStaff;
    
    public AddUser(Staff newStaff){
        this.newStaff = newStaff;
    }
    @Override
    public void execute(ArrayList<Staff> StaffList){
        for(Staff s : StaffList){
            if(s.getStaffId().equals(newStaff.getStaffId())){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Duplicate Staff ID");
                alert.setContentText("Staff ID " + newStaff.getStaffId() + " already exist.");
                alert.showAndWait();
                return;
            }
        }
        
        StaffList.add(newStaff);
        System.out.println("Staff Added Successfully. ");
    }
}

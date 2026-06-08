import Employee.Employee;
import Employee.FullTimeEmployee;
import Employee.PartTimeEmployee;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class AdminPage extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        ArrayList<Employee> staffList = new ArrayList<>();
        // Ali is full-time: ID, Name, Base Salary, Benefits
        staffList.add(new FullTimeEmployee("D01", "Aiman", 2500.00, "Full-Time",500.00)); 
        staffList.add(new FullTimeEmployee("D01", "Aiman", 2500.00, "Full-Time",500.00)); 
        // Sarah is part-time: ID, Name, Hourly Rate, Hours Worked
        staffList.add(new PartTimeEmployee("D02", "Haikal", 15.00, "Part-Time",80));
        BorderPane pane = new BorderPane();
        beforeAdmin(pane, staffList);
        Scene scene = new Scene(pane, 500, 500);
        primaryStage.setTitle("Admin Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    public static void beforeAdmin(BorderPane pane, ArrayList<Employee> staffList){
        
        Image imageAdmin =  new Image("file:"+"C:\\Users\\muhai\\OneDrive\\Pictures\\administrator.png");
        Image imagePSlip = new Image("file:"+"C:\\Users\\muhai\\OneDrive\\Pictures\\payslip.png");
        
        ImageView ivPSlip = new ImageView(imagePSlip);
        ImageView ivAdmin = new ImageView(imageAdmin);
        
        ivPSlip.setFitHeight(100);
        ivPSlip.setFitWidth(100);
        ivAdmin.setFitHeight(100);
        ivAdmin.setFitWidth(100);
        
        Button paySlip = new Button("PaySlip", ivPSlip);
        Button admin = new Button("Administration", ivAdmin);
        
        paySlip.setContentDisplay(ContentDisplay.TOP);
        admin.setContentDisplay(ContentDisplay.TOP);
        
        paySlip.setGraphicTextGap(10);
        admin.setGraphicTextGap(10);
        
        HBox btnBox = new HBox(15);
        btnBox.getChildren().addAll(paySlip, admin);
        
        btnBox.setAlignment(Pos.CENTER);
        
        pane.setCenter(btnBox);
        
        admin.setOnAction(e -> {
            enterAdmin(staffList);
        });

        paySlip.setOnAction(e -> {
            enterPaySlip(staffList);
        });
    }
    
    public static void enterAdmin(ArrayList<Employee> staffList){
        BorderPane pane = new BorderPane();
        Button addBtn = new Button("Add Employee");
        pane.setStyle("-fx-background-color: #f5f5f5;");
        pane.setBottom(addBtn);
        pane.setBottom(addBtn);
        BorderPane.setMargin(addBtn, new Insets(20));
        showEmployee(staffList, pane);
        
        addBtn.setStyle(
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-size: 14px;" +
            "-fx-background-color: #4CAF50;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 8 15 8 15;"
        
        );
        addBtn.setOnAction(e -> {
            addEmployee(staffList, pane);
        });
        
        Scene scene = new Scene(pane, 500, 500);
        Stage stage = new Stage();
        stage.setTitle("Administration Page");
        stage.setScene(scene);
        stage.show();
    }
    public static void showEmployee(ArrayList<Employee> staffList, BorderPane pane) {
        ScrollPane sPane = new ScrollPane();
        ScrollPane sPane = new ScrollPane();
        VBox showStaff = new VBox(15);
        showStaff.setPadding(new Insets(20));
        // Clear previous items from the center so they don't duplicate on refresh
        pane.setCenter(null);
        
        for (Employee s : staffList) {
        // Use your updated getter methods
        Label staffId = new Label("ID : " + s.getEmployeeID());
        Label staffName = new Label("Name : " + s.getName());
        // Calls your polymorphic calculateSalary() method automatically!
        Label salary = new Label("Salary : RM " + String.format("%.2f", s.calculateSalary())); 
        Label statusLab = new Label("Status : " + s.getStatus());
        Label statusLab = new Label("Status : " + s.getStatus());
        staffId.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");
        staffName.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");
        salary.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");
        statusLab.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");
        statusLab.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");
        
        staffId.setPrefWidth(60);
        staffName.setPrefWidth(120);
        salary.setPrefWidth(140);
        statusLab.setPrefWidth(130);
        salary.setPrefWidth(140);
        statusLab.setPrefWidth(130);
        
        Button delete = new Button("Delete");
        Button update = new Button("Update");
        
        HBox btnBox = new HBox(10);
        btnBox.setAlignment(Pos.CENTER_RIGHT);
        btnBox.getChildren().addAll(update, delete);
        
        VBox staffCard = new VBox(8);
        staffCard.setPadding(new Insets(15));
        staffCard.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-border-radius: 10;" +
            "-fx-border-color: #dcdcdc;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 2);"
        );
        
        staffCard.getChildren().addAll(staffId, staffName, salary, statusLab,btnBox);
        staffCard.getChildren().addAll(staffId, staffName, salary, statusLab,btnBox);
        showStaff.getChildren().add(staffCard);
            // Pass the raw ID string directly from the object instead of the Label text
        delete.setOnAction(e -> {
            deleteEmployee(s.getEmployeeID(), staffList, pane);
        });
        
        delete.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-background-color: #f44336; -fx-text-fill: white;");
        
        update.setOnAction(e -> {
            updateEmployee(s.getEmployeeID(), staffList, pane);
        });
        
        update.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-background-color: #2196F3; -fx-text-fill: white;");
    }
    sPane.setContent(showStaff);
    sPane.setFitToWidth(true);
    pane.setCenter(sPane);
    sPane.setContent(showStaff);
    sPane.setFitToWidth(true);
    pane.setCenter(sPane);
}
    
    
    public static void addEmployee(ArrayList<Employee> staffList, BorderPane Bpane) {
    Label addId = new Label("Enter ID : ");
    TextField IdTf = new TextField();
    
    Label addName = new Label("Enter Name : ");
    TextField NameTf = new TextField();

    // NEW: Job Type Selector
    Label typeLabel = new Label("Job Type : ");
    ToggleGroup typeGroup = new ToggleGroup();
    RadioButton rbFullTime = new RadioButton("Full-Time");
    rbFullTime.setToggleGroup(typeGroup);
    rbFullTime.setSelected(true);
    RadioButton rbPartTime = new RadioButton("Part-Time");
    rbPartTime.setToggleGroup(typeGroup);
    HBox typeBox = new HBox(10, rbFullTime, rbPartTime);

    // Dynamic Extra Fields
    Label extraLabel1 = new Label("Base Salary : ");
    TextField extraTf1 = new TextField(); // Will hold Base Salary OR Hourly Rate
    Label extraLabel2 = new Label("Benefits : ");
    TextField extraTf2 = new TextField(); // Will hold Benefits OR Hours Worked
    
    // Toggle field labels dynamically
    rbFullTime.setOnAction(e -> {
        extraLabel1.setText("Base Salary : ");
        extraLabel2.setText("Benefits : ");
    });
    rbPartTime.setOnAction(e -> {
        extraLabel1.setText("Hourly Rate : ");
        extraLabel2.setText("Hours Worked : ");
    });

    GridPane grid = new GridPane();
    grid.setHgap(10); grid.setVgap(15);
    
    grid.add(addId, 0, 0);      grid.add(IdTf, 1, 0);
    grid.add(addName, 0, 1);    grid.add(NameTf, 1, 1);
    grid.add(typeLabel, 0, 2);  grid.add(typeBox, 1, 2);
    grid.add(extraLabel1, 0, 3); grid.add(extraTf1, 1, 3);
    grid.add(extraLabel2, 0, 4); grid.add(extraTf2, 1, 4);
    
    Button submit = new Button("Submit");
    Label title = new Label("Add New Staff");
    title.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 22px; -fx-font-weight: bold;");
    submit.setStyle("-fx-font-family: 'Segoe UI'; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 8 20 8 20;");
           
    VBox root = new VBox(20);
    root.setPadding(new Insets(20));
    root.getChildren().addAll(title, grid, submit);
    
    Scene scene = new Scene(root);
    Stage stage = new Stage();
    stage.setTitle("Add Staff");
    stage.setScene(scene);
    stage.show();
    
    submit.setOnAction(e -> {
        try {
            Employee newEmp;
            String id = IdTf.getText();
            String name = NameTf.getText();
            double val1 = Double.parseDouble(extraTf1.getText());
            double val2 = Double.parseDouble(extraTf2.getText());
            String status;
            String status;
            if (rbFullTime.isSelected()) {
                status = "Full-Time";
                newEmp = new FullTimeEmployee(id, name, val1, status,val2);
                status = "Full-Time";
                newEmp = new FullTimeEmployee(id, name, val1, status,val2);
            } else {
                status = "Part-Time";
                newEmp = new PartTimeEmployee(id, name, val1, status,(int) val2);
                status = "Part-Time";
                newEmp = new PartTimeEmployee(id, name, val1, status,(int) val2);
            }

            // Call the team's handler (make sure AddUser class accepts 'Employee' now instead of 'Staff')
            AddUser add = new AddUser(newEmp);
            add.execute(staffList);
            
            showEmployee(staffList, Bpane);
            stage.close();
        } catch (Exception ex) {
            System.out.println("Validation Error: Check input values.");
        }
    });
}
    
    public static void deleteEmployee(String ID, ArrayList<Employee> StaffList, BorderPane pane){
        DeleteUser del = new DeleteUser(ID);
        del.execute(StaffList);
        showEmployee(StaffList, pane);
    }
    
    public static void updateEmployee(String ID, ArrayList<Employee> staffList, BorderPane pane) {
    // Find the current employee object from the list
    Employee target = null;
    for (Employee e : staffList) {
        if (e.getEmployeeID().equals(ID)) {
            target = e;
            break;
        }
    }
    if (target == null) return;

    Label IdLabel = new Label("ID : ");
    TextField IdTf = new TextField(ID);
    IdTf.setEditable(false);
    
    Label updateName = new Label("Enter Name : ");
    TextField NameTf = new TextField(target.getName());
    
    Label extraLabel1 = new Label();
    TextField extraTf1 = new TextField();
    Label extraLabel2 = new Label();
    TextField extraTf2 = new TextField();

    // Set layout values based on what type of employee they actually are
    if (target instanceof FullTimeEmployee) {
        FullTimeEmployee ft = (FullTimeEmployee) target;
        extraLabel1.setText("Enter Base Salary : ");
        extraTf1.setText(String.valueOf(ft.getBasicSalary()));
        extraLabel2.setText("Enter Benefits : ");
        extraTf2.setText(String.valueOf(ft.getBenefits()));
    } else if (target instanceof PartTimeEmployee) {
        PartTimeEmployee pt = (PartTimeEmployee) target;
        extraLabel1.setText("Enter Hourly Rate : ");
        extraTf1.setText(String.valueOf(pt.getHourlyRate()));
        extraLabel2.setText("Enter Hours Worked : ");
        extraTf2.setText(String.valueOf(pt.getHoursWorked()));
    }

    GridPane grid = new GridPane();
    grid.setHgap(10); grid.setVgap(15);
    grid.add(IdLabel, 0, 0);     grid.add(IdTf, 1, 0);
    grid.add(updateName, 0, 1); grid.add(NameTf, 1, 1);
    grid.add(extraLabel1, 0, 2); grid.add(extraTf1, 1, 2);
    grid.add(extraLabel2, 0, 3); grid.add(extraTf2, 1, 3);
    
    Button updBtn = new Button("Update");
    Label title = new Label("Update Staff");
    title.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 22px; -fx-font-weight: bold;");
    updBtn.setStyle("-fx-font-family: 'Segoe UI'; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 8 20 8 20;");
            
    VBox root = new VBox(20);
    root.setPadding(new Insets(20));
    root.getChildren().addAll(title, grid, updBtn);
    
    Scene scene = new Scene(root);
    Stage stage = new Stage();
    stage.setTitle("Update Staff");
    stage.setScene(scene);
    stage.show();
    
    final Employee finalTarget = target;
    updBtn.setOnAction(e -> {
        try {
            Employee updatedEmp;
            String name = NameTf.getText();
            double val1 = Double.parseDouble(extraTf1.getText());
            double val2 = Double.parseDouble(extraTf2.getText());

            if (finalTarget instanceof FullTimeEmployee) {
                updatedEmp = new FullTimeEmployee(ID, name, val1, "Full-Time",val2);
                updatedEmp = new FullTimeEmployee(ID, name, val1, "Full-Time",val2);
            } else {
                updatedEmp = new PartTimeEmployee(ID, name, val1, "Part-Time",(int) val2);
                updatedEmp = new PartTimeEmployee(ID, name, val1, "Part-Time",(int) val2);
            }

            // Execute the team's Update mechanism
            UpdateUser update = new UpdateUser(updatedEmp);
            update.execute(staffList);
            
            showEmployee(staffList, pane);
            stage.close();
        } catch (Exception ex) {
            System.out.println("Update failed: Check numbers format.");
        }
    });
}
    public static void enterPaySlip(ArrayList<Employee> staffList){ //This method can be called from the PaySlip button in the main admin page
        BorderPane pane2 = new BorderPane();
        Label title2 = new Label("PaySlip Page");
        title2.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 22px; -fx-font-weight: bold;");
        pane2.setTop(title2);
        BorderPane.setAlignment(title2, Pos.CENTER);
        Scene scene2 = new Scene(pane2, 500, 500);
        Stage stage2 = new Stage();
        stage2.setScene(scene2);
        stage2.setTitle("PaySlip Page");
        stage2.show();
    }
}
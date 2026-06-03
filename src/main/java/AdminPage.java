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
import javafx.scene.layout.GridPane;

public class AdminPage extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        ArrayList<Staff> staffList = new ArrayList<>();
        BorderPane pane = new BorderPane();
        Button addBtn = new Button("Add Employee");
        staffList.add(new Staff("Aiman", "D01", 100));
        staffList.add(new Staff("Haikal", "D02", 100));
        pane.setStyle("-fx-background-color: #f5f5f5;");
        pane.setTop(addBtn);
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
        
        Scene scene = new Scene(pane);
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
    
    
    public static void showEmployee(ArrayList<Staff> staffList, BorderPane pane){
        Label idHeader = new Label("ID");
        idHeader.setPrefWidth(60);

        Label nameHeader = new Label("Name");
        nameHeader.setPrefWidth(120);

        Label salaryHeader = new Label("Salary");
        salaryHeader.setPrefWidth(80);

        HBox header = new HBox(15);
        header.getChildren().addAll(
            idHeader,
            nameHeader,
            salaryHeader
        );

        VBox showStaff = new VBox(15);
        showStaff.setPadding(new Insets(20));
        showStaff.getChildren().add(header);
        showStaff.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: lightgray;" +
            "-fx-border-width: 1;"
        );
        for(Staff s : staffList){
            Label staffId = new Label(s.getStaffId());
            Label staffName = new Label(s.getStaffName());
            Label salary = new Label(String.format("%.2f", s.getSalary()));
            
            staffId.setStyle("-fx-font-family: 'Segoe UI';" + "-fx-font-size: 14px;" );
            staffName.setStyle("-fx-font-family: 'Segoe UI';" + "-fx-font-size: 14px;" );
            salary.setStyle("-fx-font-family: 'Segoe UI';" + "-fx-font-size: 14px;" );
            
            staffId.setPrefWidth(60);
            staffName.setPrefWidth(120);
            salary.setPrefWidth(80);
            
            Button delete = new Button("Delete");
            Button update = new Button("Update");
            
            HBox staffLabel = new HBox(15);
            staffLabel.setAlignment(Pos.CENTER_LEFT);
            staffLabel.getChildren().addAll(staffId, staffName, salary, delete, update);
            showStaff.getChildren().add(staffLabel);
            
            delete.setOnAction(e -> {
                deleteEmployee(staffId.getText(), staffList, pane);
            });
            
            delete.setStyle(
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-size: 14px;" +
                "-fx-background-color: #f44336;" +
                "-fx-text-fill: white;"
            );
            
            update.setOnAction(e -> {
                updateEmployee(staffId.getText(),staffList, pane);
            });
            
            update.setStyle(
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-size: 14px;" +
                "-fx-background-color: #2196F3;" +
                "-fx-text-fill: white;"
            );
        }
        
        pane.setCenter(showStaff);
    }
    
    public static void addEmployee(ArrayList<Staff> staffList, BorderPane Bpane){
        Label addId = new Label("Enter ID : ");
        TextField IdTf = new TextField();
        
        Label addName = new Label("Enter Name : ");
        TextField NameTf = new TextField();

        
        Label addSal = new Label("Enter Salary : ");
        TextField SalTf = new TextField();
        
        addId.setPrefWidth(100);
        addName.setPrefWidth(100);
        addSal.setPrefWidth(100);
        
        IdTf.setPrefWidth(200);
        NameTf.setPrefWidth(200);
        SalTf.setPrefWidth(200);
        
        String tfStyle = "-fx-font-family: 'Segoe UI'; " + "-fx-font-size: 14px;" + "-fx-padding: 5px;";
        addId.setStyle(tfStyle);
        addName.setStyle(tfStyle);
        addSal.setStyle(tfStyle);
        
        IdTf.setStyle(tfStyle);
        NameTf.setStyle(tfStyle);
        SalTf.setStyle(tfStyle);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(15);
        
        grid.add(addId, 0, 0);
        grid.add(IdTf, 1 , 0);
        
        grid.add(addName, 0, 1);
        grid.add(NameTf, 1, 1);
        
        grid.add(addSal, 0, 2);
        grid.add(SalTf, 1, 2);
        
        Button submit = new Button("Submit");
        
        Label title = new Label("Add New Staff");

        title.setStyle(
            "-fx-font-family: 'Segoe UI'; " +
            "-fx-font-size: 22px;" +
            "-fx-font-weight: bold;"
        );
        
        submit.setStyle(
            "-fx-font-family: 'Segoe UI'; " +
            "-fx-background-color: #4CAF50;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 8 20 8 20;"
        );
               
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.getChildren().addAll( title ,grid, submit);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Add Staff");
        stage.setScene(scene);
        stage.show();
        
        submit.setOnAction(e -> {
            AddUser add = new AddUser(new Staff(NameTf.getText(), IdTf.getText(), Double.parseDouble(SalTf.getText())));
            add.execute(staffList);
            showEmployee(staffList, Bpane);
            stage.close();
        });
        
    }
    
    public static void deleteEmployee(String ID, ArrayList<Staff> StaffList, BorderPane pane){
        DeleteUser del = new DeleteUser(ID);
        del.execute(StaffList);
        showEmployee(StaffList, pane);
    }
    
    public static void updateEmployee(String ID,ArrayList<Staff> staffList, BorderPane pane){
        Label Id = new Label("Enter ID : ");
        TextField IdTf = new TextField();
        IdTf.setText(ID);
        IdTf.setEditable(false);
        
        Label updateName = new Label("Enter Name : ");
        TextField NameTf = new TextField();
        
        Label updateSal = new Label("Enter Salary : ");
        TextField SalTf = new TextField();

        Id.setPrefWidth(100);
        updateName.setPrefWidth(100);
        updateSal.setPrefWidth(100);
        
        IdTf.setPrefWidth(200);
        NameTf.setPrefWidth(200);
        SalTf.setPrefWidth(200);
        
        String tfStyle = "-fx-font-size: 14px;" + "-fx-padding: 5px;";

        Id.setStyle(tfStyle);
        updateName.setStyle(tfStyle);
        updateSal.setStyle(tfStyle);
        
        IdTf.setStyle(tfStyle);
        NameTf.setStyle(tfStyle);
        SalTf.setStyle(tfStyle);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(15);
        
        grid.add(Id, 0, 0);
        grid.add(IdTf, 1 , 0);
        
        grid.add(updateName, 0, 1);
        grid.add(NameTf, 1, 1);
        
        grid.add(updateSal, 0, 2);
        grid.add(SalTf, 1, 2);
        
        Button updBtn = new Button("Update");
        
        Label title = new Label("Update Staff");

        title.setStyle(
            "-fx-font-family: 'Segoe UI'; "+
            "-fx-font-size: 22px;" +
            "-fx-font-weight: bold;"
        );
        
        updBtn.setStyle(
            "-fx-font-family: 'Segoe UI'; "    +
            "-fx-background-color: #4CAF50;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 8 20 8 20;"
        );
               
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.getChildren().addAll( title ,grid, updBtn);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Update Staff");
        stage.setScene(scene);
        stage.show();
        
        updBtn.setOnAction(e -> {
            UpdateUser update = new UpdateUser(new Staff( NameTf.getText(), ID, Double.parseDouble(SalTf.getText())));
            update.execute(staffList);
            showEmployee(staffList, pane);
            stage.close();
        });
    }
}

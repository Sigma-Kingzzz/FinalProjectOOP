/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
/**
 *
 * @author muhai
 */
public class AdminPage extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        ArrayList<Staff> staffList = new ArrayList<>();
        BorderPane pane = new BorderPane();
        Button addBtn = new Button("Add Employee");
        staffList.add(new Staff("Aiman", "D01", 100));
        staffList.add(new Staff("Haikal", "D02", 100));
        
        pane.setTop(addBtn);
        
        showEmployee(staffList, pane);
        
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
        VBox showStaff = new VBox(10);
        for(Staff s : staffList){
            Label staffId = new Label(s.getStaffId());
            Label staffName = new Label(s.getStaffName());
            Label salary = new Label(String.format("%.2f", s.getSalary()));
            Button delete = new Button("Delete");
            HBox staffLabel = new HBox(10);
            staffLabel.getChildren().addAll(staffId, staffName, salary, delete);
            showStaff.getChildren().add(staffLabel);
            
            delete.setOnAction(e -> {
                deleteEmployee(staffId.getText(), staffList, pane);
            });
        }
        
        pane.setCenter(showStaff);
    }
    
    public static void addEmployee(ArrayList<Staff> staffList, BorderPane Bpane){
        Label addId = new Label("Enter ID : ");
        TextField IdTf = new TextField();
        HBox IdBox = new HBox(10);
        IdBox.getChildren().addAll(addId, IdTf);
        
        Label addName = new Label("Enter Name : ");
        TextField NameTf = new TextField();
        HBox nameBox = new HBox(10);
        nameBox.getChildren().addAll(addName, NameTf);
        
        Label addSal = new Label("Enter Salary : ");
        TextField SalTf = new TextField();
        HBox SalBox = new HBox(10);
        SalBox.getChildren().addAll(addSal, SalTf);
        
        VBox addSection = new VBox(10);
        addSection.getChildren().addAll(IdBox, nameBox, SalBox);
        
        
        Button submit = new Button("Submit");
        
               
        StackPane Pane = new StackPane();
        Pane.getChildren().addAll(addSection, submit);
        Scene scene = new Scene(Pane);
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
}

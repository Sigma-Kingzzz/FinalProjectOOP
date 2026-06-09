import Employee.Employee;
import Employee.FullTimeEmployee;
import Employee.PartTimeEmployee;
import java.util.ArrayList;

import javax.smartcardio.Card;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import javafx.scene.control.TextField;

public class Salary {

    private Employee currentUser;

    // Constructor now takes just ONE employee (the logged-in user)
    public Salary(Employee currentUser) 
    {
        this.currentUser = currentUser;
    }

    // calculateSalary() - returns the salary of one employee
    public double calculateSalary()
    {
        return currentUser.calculateSalary();
    }

    public boolean checkSalary() 
    {
        double minimumWage = 1500.00;
        return calculateSalary() >= minimumWage;
    }

    // getSalaryBreakdown() - opens a JavaFX window showing all salary details
    public void getSalaryBreakdown() 
    {   

        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #f5f5f5;");

        // Title at top
        Label title = new Label("My Payslip");
        title.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 22px; -fx-font-weight: bold;");
        HBox titleBox = new HBox(title);
        titleBox.setPadding(new Insets(20));
        titleBox.setAlignment(Pos.CENTER);
        pane.setTop(titleBox);

        // Basic info labels
        Label idLabel = new Label("Employee ID  : " + currentUser.getEmployeeID());
        Label nameLabel = new Label("Name         : " + currentUser.getName());
        Label statusLabel = new Label("Status       : " + currentUser.getStatus());
        IncomeZakatCalculator z = new IncomeZakatCalculator(5000.00);

        idLabel.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 16px;");
        nameLabel.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 16px;");
        statusLabel.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 16px;");
        // Extra labels depending on Full-Time or Part-Time
        Label extra1 = new Label();
        Label extra2 = new Label();
        extra1.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 16px;");
        extra2.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 16px;");

        if (currentUser instanceof FullTimeEmployee) 
        {
            FullTimeEmployee ft = (FullTimeEmployee) currentUser;
            extra1.setText("Basic Salary : RM " + String.format("%.2f", ft.getBasicSalary()));
            extra2.setText("Benefits     : RM " + String.format("%.2f", ft.getBenefits()));
            z.getIncome(ft.getBasicSalary(), ft.getBenefits());
            Label zakatLabel = new Label("Zakat : RM " + String.format("%.2f", z.calculateAnnualZakat()));
            zakatLabel.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 16px;");
        } 
        else if (currentUser instanceof PartTimeEmployee) 
        {
            PartTimeEmployee pt = (PartTimeEmployee) currentUser;
            extra1.setText("Hourly Rate  : RM " + String.format("%.2f", pt.getHourlyRate()));
            extra2.setText("Hours Worked : " + pt.getHoursWorked() + " hrs");
        }

        // Total salary label
        Label totalLabel = new Label("Total Net Salary : RM " + String.format("%.2f", calculateSalary()));
        totalLabel.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Minimum wage check label
        Label minWageLabel = new Label();
        if (checkSalary())
        {
            minWageLabel.setText("Minimum Wage : MEETS (>= RM 1500.00)");
            minWageLabel.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-text-fill: green;");
        } 
        else 
        {
            minWageLabel.setText("Minimum Wage : BELOW (< RM 1500.00)");
            minWageLabel.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-text-fill: red;");
        }
        // --- PRINT BUTTON & ALERT LOGIC ---
        Button printBtn = new Button("🖨️ Print to File (.txt)");
        printBtn.setStyle("-fx-font-family: 'Segoe UI'; -fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15 8 15; -fx-background-radius: 5;");
        printBtn.setMaxWidth(Double.MAX_VALUE); // Makes button stretch cleanly across the layout

        printBtn.setOnAction(ev -> {
            javafx.scene.control.Alert confirm = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION, 
                "Would you like to export/print this payslip to a .txt file?", 
                javafx.scene.control.ButtonType.YES, javafx.scene.control.ButtonType.NO);
            confirm.setTitle("Export Payslip");
            confirm.setHeaderText(null);
            
            confirm.showAndWait().ifPresent(response -> {
                if (response == javafx.scene.control.ButtonType.YES) 
                {
                    // FIXED: Changed this.loggedInUser to currentUser
                    AdminPage.savePayslipToTxt(currentUser); 
                }
            });
        });
            // Card layout
        VBox card = new VBox(15);
        card.setPadding(new Insets(30));
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-border-radius: 10;" +
            "-fx-border-color: #dcdcdc;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 2);"
        );

        card.getChildren().addAll(
            idLabel, nameLabel, statusLabel, 
            new Label("--------------------------------------------"), 
            extra1, extra2, 
            new Label("--------------------------------------------"), 
            totalLabel, minWageLabel, 
            new Label(""), // Small spacer line
            printBtn
        );
        
        VBox centerBox = new VBox(card);
        centerBox.setPadding(new Insets(20));
        centerBox.setAlignment(Pos.TOP_CENTER);
        
        pane.setCenter(centerBox);

        Scene scene = new Scene(pane, 400, 500);
        Stage stage = new Stage();
        stage.setTitle("Pay Slip");
        stage.setScene(scene);
        stage.show();
    }
}

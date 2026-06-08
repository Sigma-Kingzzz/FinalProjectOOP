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

    private ArrayList<Employee> employeeList;

    // Constructor
    public Salary(ArrayList<Employee> employeeList)
    {
        this.employeeList = employeeList;
    }

    // calculateSalary() - returns the salary of one employee
    public double calculateSalary(Employee e)
    {
        return e.calculateSalary();
    }

    // checkSalary() - checks if salary meets minimum wage RM 1500
    public boolean checkSalary(Employee e) 
    {
        double minimumWage = 1500.00;
        if (e.calculateSalary() >= minimumWage)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // getSalaryBreakdown() - opens a JavaFX window showing all salary details
    public void getSalaryBreakdown() 
    {   

        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #f5f5f5;");

        ScrollPane sPane = new ScrollPane();
        VBox allCards = new VBox(15);
        allCards.setPadding(new Insets(20));

        pane.setCenter(sPane);
        // Title at top
        Label title = new Label("Pay Slip");
        title.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 22px; -fx-font-weight: bold;");
        HBox titleBox = new HBox(title);
        titleBox.setPadding(new Insets(20));
        titleBox.setAlignment(Pos.CENTER_LEFT);
        pane.setTop(titleBox);

        for (int i = 0; i < employeeList.size(); i++)
        {
            Employee e = employeeList.get(i);

            // Basic info labels
            Label idLabel = new Label("Employee ID  : " + e.getEmployeeID());
            Label nameLabel = new Label("Name         : " + e.getName());
            Label statusLabel = new Label("Status       : " + e.getStatus());

            idLabel.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");
            nameLabel.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");
            statusLabel.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");

            // Extra labels depending on Full-Time or Part-Time
            Label extra1 = new Label();
            Label extra2 = new Label();
            extra1.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");
            extra2.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");

            if (e instanceof FullTimeEmployee) 
            {
                FullTimeEmployee ft = (FullTimeEmployee) e;
                extra1.setText("Basic Salary : RM " + String.format("%.2f", ft.getBasicSalary()));
                extra2.setText("Benefits     : RM " + String.format("%.2f", ft.getBenefits()));
            } 
            else if (e instanceof PartTimeEmployee) 
            {
                PartTimeEmployee pt = (PartTimeEmployee) e;
                extra1.setText("Hourly Rate  : RM " + String.format("%.2f", pt.getHourlyRate()));
                extra2.setText("Hours Worked : " + pt.getHoursWorked() + " hrs");
            }

            // Total salary label
            Label totalLabel = new Label("Total Salary : RM " + String.format("%.2f", calculateSalary(e)));
            totalLabel.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-font-weight: bold;");

            // Minimum wage check label
            Label minWageLabel = new Label();
            if (checkSalary(e))
            {
                minWageLabel.setText("Minimum Wage : MEETS (>= RM 1500.00)");
                minWageLabel.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-text-fill: green;");
            } 
            else 
            {
                minWageLabel.setText("Minimum Wage : BELOW (< RM 1500.00)");
                minWageLabel.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-text-fill: red;");
            }

            // Card layout - same style as AdminPage
            VBox card = new VBox(8);
            card.setPadding(new Insets(15));
            card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 10;" +
                "-fx-border-radius: 10;" +
                "-fx-border-color: #dcdcdc;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 2);"
            );

            card.getChildren().addAll(idLabel, nameLabel, statusLabel, extra1, extra2, totalLabel, minWageLabel);
            allCards.getChildren().add(card);
        }

        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER);
        Button generatePaySlipBtn = new Button("Generate Pay Slip");
        generatePaySlipBtn.setStyle(
            "-fx-background-color: #4CAF50;" +
            "-fx-text-fill: white;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 8 16;" +
            "-fx-background-radius: 5;"
        );

        buttonBox.getChildren().add(generatePaySlipBtn);

         generatePaySlipBtn.setOnAction(g -> {
            try {
                FileWriter fw = new FileWriter("pay_slips.txt");
                for (Employee emp : employeeList) {
                    fw.write("Employee ID: " + emp.getEmployeeID() + "\n");
                    fw.write("Name: " + emp.getName() + "\n");
                    fw.write("Status: " + emp.getStatus() + "\n");
                    if (emp instanceof FullTimeEmployee) {
                        FullTimeEmployee ft = (FullTimeEmployee) emp;
                        fw.write("Basic Salary: RM " + String.format("%.2f", ft.getBasicSalary()) + "\n");
                        fw.write("Benefits: RM " + String.format("%.2f", ft.getBenefits()) + "\n");
                    } else if (emp instanceof PartTimeEmployee) {
                        PartTimeEmployee pt = (PartTimeEmployee) emp;
                        fw.write("Hourly Rate: RM " + String.format("%.2f", pt.getHourlyRate()) + "\n");
                        fw.write("Hours Worked: " + pt.getHoursWorked() + " hrs\n");
                    }
                    fw.write("Total Salary: RM " + String.format("%.2f", calculateSalary(emp)) + "\n");
                    fw.write(checkSalary(emp) ? "Minimum Wage: MEETS (>= RM 1500.00)\n" : "Minimum Wage: BELOW (< RM 1500.00)\n");
                    fw.write("--------------------------------------------------\n");
                }
                fw.close();
                TextField successMsg = new TextField("Pay slips generated successfully in " + new File("pay_slips.txt").getAbsolutePath());
                successMsg.setEditable(false);
                successMsg.setStyle("-fx-background-color: transparent; -fx-text-fill: green; -fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");
                allCards.getChildren().add(successMsg);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

            allCards.getChildren().add(buttonBox);

        sPane.setContent(allCards);
        sPane.setFitToWidth(true);

        Scene scene = new Scene(pane, 500, 500);
        Stage stage = new Stage();
        stage.setTitle("Pay Slip Page");
        stage.setScene(scene);
        stage.show();
    }
}

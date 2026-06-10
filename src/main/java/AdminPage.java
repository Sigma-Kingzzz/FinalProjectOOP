import Employee.Employee;
import Employee.FullTimeEmployee;
import Employee.PartTimeEmployee;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminPage extends Application {
     
    // Maintain your runtime database list globally within the session lifecycle
    private static ArrayList<Employee> staffList = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        // Initialize your dummy data once
        if (staffList.isEmpty()) {
            staffList.add(new FullTimeEmployee("D01", "Aiman", 2500.00, "Full-Time", 500.00)); 
            staffList.add(new PartTimeEmployee("D02", "Haikal", 15.00, "Part-Time", 80));
        }

        // 1. Configure the entry-stage as Login 
        primaryStage.setTitle("Employee Payroll System - Login");
        primaryStage.setResizable(false);

        // 2. Load Login layout (Assuming LoginView accepts Stage or handles logic internally)
        // Note: You must wire your LoginView submit button to call AdminPage.showAdminDashboard(primaryStage);
        LoginView loginView = new LoginView(primaryStage); 
        
        Scene loginScene = new Scene(loginView.getRoot(), 900, 600);

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    /**
     * This method transitions the view from Login to the main Dashboard choice pane.
     * It is triggered automatically inside LoginView upon a successful sign-in.
     */
    public static void showAdminDashboard(Stage stage, Employee loggedInUser, User loggedInAuth) {
        stage.setResizable(true); // Allow dynamic sizing for dashboards
        stage.setTitle("PayrollPro - Dashboard");
        
        BorderPane pane = new BorderPane();
        beforeAdmin(pane, staffList, loggedInUser, loggedInAuth); // Loads selection buttons (PaySlip / Administration)
    
        Scene scene = new Scene(pane, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    public static void beforeAdmin(BorderPane pane, ArrayList<Employee> staffList, Employee loggedInUser, User loggedInAuth){
    
    Image imageAdmin = new Image("https://cdn-icons-png.freepik.com/512/6830/6830335.png");
    Image imagePSlip = new Image("https://cdn-icons-png.flaticon.com/512/1332/1332014.png");
    Image bg = new Image("https://www.pngall.com/wp-content/uploads/14/Pattern-PNG-Photos.png");
    
    ImageView ivPSlip = new ImageView(imagePSlip);
    ImageView ivAdmin = new ImageView(imageAdmin);
    ImageView ivZakat = new ImageView(new Image("https://cdn-icons-png.freepik.com/512/4392/4392102.png"));
    ImageView ivBg = new ImageView(bg);
    ivBg.setOpacity(0.10); // Set opacity for subtlety

    
    ivPSlip.setFitHeight(200);
    ivPSlip.setFitWidth(200);
    ivAdmin.setFitHeight(200);
    ivAdmin.setFitWidth(200);
    ivZakat.setFitHeight(200);
    ivZakat.setFitWidth(200);
    

    Text welcome = new Text("Welcome, " + loggedInUser.getName() + "!");
    welcome.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 50px; -fx-font-weight: bold; -fx-color: #0084ff;");
    HBox welcomeBox = new HBox(welcome);
    welcomeBox.setAlignment(Pos.CENTER);
    welcomeBox.setPadding(new Insets(50, 20, 0, 20));
    pane.setTop(welcomeBox);

    Button paySlip = new Button("PaySlip", ivPSlip);
    Button admin = new Button("Administration", ivAdmin);
    Button zakatCalculator = new Button("Zakat Calculator", ivZakat);
    Button logout = new Button("Logout");
    
    paySlip.setContentDisplay(ContentDisplay.TOP);
    admin.setContentDisplay(ContentDisplay.TOP);
    zakatCalculator.setContentDisplay(ContentDisplay.TOP);

    paySlip.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 16px; -fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20 10 20; -shadow-color: rgba(0,0,0,0.2); -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0, 0, 2);");
    admin.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20 10 20; -shadow-color: rgba(0,0,0,0.2); -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0, 0, 2);");
    zakatCalculator.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 16px; -fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20 10 20; -shadow-color: rgba(0,0,0,0.2); -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0, 0, 2);");
    
    paySlip.setGraphicTextGap(10);
    admin.setGraphicTextGap(10);
    zakatCalculator.setGraphicTextGap(10);
    
    HBox btnBox = new HBox(25);
    btnBox.setAlignment(Pos.CENTER);

    logout.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15 8 15;");
    logout.setOnAction(e -> {
        // Clear session data if needed
        staffList.clear(); // Optional: Clear staff list on logout for security
        Stage currentStage = (Stage) logout.getScene().getWindow();
        currentStage.close();
        new AdminPage().start(new Stage()); // Restart the app to show login again
    });
    
    // Only show Administration button if logged-in user is an Admin
    if (loggedInAuth instanceof Admin) {
        btnBox.getChildren().addAll(paySlip, admin, zakatCalculator, logout);
    } else {
        btnBox.getChildren().addAll(paySlip, zakatCalculator, logout);
    }
    
    pane.setCenter(btnBox);
    pane.getChildren().add(ivBg); // Add background image to the pane
    
    admin.setOnAction(e -> {
        enterAdmin(staffList);
    });

    zakatCalculator.setOnAction(e -> {
        enterZakatCalculator();
    });

    paySlip.setOnAction(e -> {
        Salary sal = new Salary(loggedInUser);
        sal.getSalaryBreakdown(); // Runs your original logic
        
        });
    }


public static void savePayslipToTxt(Employee emp) {
    // Generates a unique filename using the employee's name
    String filename = emp.getName() + "_Payslip.txt";
    
    // try-with-resources handles opening and closing the file stream safely
    try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
        writer.println("=========================================");
        writer.println("           PAYROLLPRO PAYSLIP            ");
        writer.println("=========================================");
        writer.println("Employee ID   : " + emp.getEmployeeID());
        writer.println("Name          : " + emp.getName());
        writer.println("Job Status    : " + emp.getStatus());
        writer.println("-----------------------------------------");
        
        // Check exact instances to print specialized attributes polymorphically
        if (emp instanceof FullTimeEmployee) {
            FullTimeEmployee ft = (FullTimeEmployee) emp;
            writer.printf("Base Salary   : RM %.2f\n", ft.getBasicSalary());
            writer.printf("Benefits      : RM %.2f\n", ft.getBenefits());
        } else if (emp instanceof PartTimeEmployee) {
            PartTimeEmployee pt = (PartTimeEmployee) emp;
            writer.printf("Hourly Rate   : RM %.2f\n", pt.getHourlyRate());
            writer.println("Hours Worked  : " + pt.getHoursWorked());
        }
        
        writer.println("-----------------------------------------");
        writer.printf("TOTAL NET PAY : RM %.2f\n", emp.calculateSalary());
        writer.println("=========================================");
        
        // Attaches a real execution timestamp
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        writer.println("Printed on    : " + dtf.format(LocalDateTime.now()));
        writer.println("=========================================");
        
        // Success Popup
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Payslip exported successfully!\nSaved as: " + filename);
        successAlert.showAndWait();
        
    } catch (IOException ex) {
        // Error Popup fallback if something locks the folder
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("File Error");
        errorAlert.setContentText("Failed to write the text file: " + ex.getMessage());
        errorAlert.showAndWait();
    }
}
    // Public getter so LoginView can search through registered employees during auth
    public static ArrayList<Employee> getStaffList() {
        return staffList;
    }

    public static void enterAdmin(ArrayList<Employee> staffList) {
        BorderPane pane = new BorderPane();
        Button addBtn = new Button("Add Employee");
        pane.setStyle("-fx-background-color: #f5f5f5;");
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
        
        Scene scene = new Scene(pane, 550, 550);
        Stage stage = new Stage();
        stage.setTitle("Administration Subsystem");
        stage.setScene(scene);
        stage.show();
    }

    public static void enterZakatCalculator() { // Placeholder for Zakat Calculator scene
        ZakatCalculatorScene zakatScene = new ZakatCalculatorScene();
        Scene scene = new Scene(zakatScene.getLayout(), 600, 500);
        
        Stage stage = new Stage();
        stage.setTitle("Income Zakat Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void showEmployee(ArrayList<Employee> staffList, BorderPane pane) {
        ScrollPane sPane = new ScrollPane();
        VBox showStaff = new VBox(15);
        showStaff.setPadding(new Insets(20));
        pane.setCenter(null);
        
        for (Employee s : staffList) {
            Label staffId = new Label("ID : " + s.getEmployeeID());
            Label staffName = new Label("Name : " + s.getName());
            Label salary = new Label("Salary : RM " + String.format("%.2f", s.calculateSalary())); 
            Label statusLab = new Label("Status : " + s.getStatus());
            
            staffId.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");
            staffName.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");
            salary.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");
            statusLab.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");
            
            staffId.setPrefWidth(60);
            staffName.setPrefWidth(120);
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
            
            // Fixed duplicate addition bug here
            staffCard.getChildren().addAll(staffId, staffName, salary, statusLab, btnBox);
            showStaff.getChildren().add(staffCard);

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
    }
    
    public static void addEmployee(ArrayList<Employee> staffList, BorderPane Bpane) {
        Label addId = new Label("Enter ID : ");
        TextField IdTf = new TextField();
        
        Label addName = new Label("Enter Name : ");
        TextField NameTf = new TextField();

        Label typeLabel = new Label("Job Type : ");
        ToggleGroup typeGroup = new ToggleGroup();
        RadioButton rbFullTime = new RadioButton("Full-Time");
        rbFullTime.setToggleGroup(typeGroup);
        rbFullTime.setSelected(true);
        RadioButton rbPartTime = new RadioButton("Part-Time");
        rbPartTime.setToggleGroup(typeGroup);
        HBox typeBox = new HBox(10, rbFullTime, rbPartTime);

        Label extraLabel1 = new Label("Base Salary : ");
        TextField extraTf1 = new TextField(); 
        Label extraLabel2 = new Label("Benefits : ");
        TextField extraTf2 = new TextField(); 
        
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
                
                if (rbFullTime.isSelected()) {
                    status = "Full-Time";
                    newEmp = new FullTimeEmployee(id, name, val1, status, val2);
                } else {
                    status = "Part-Time";
                    newEmp = new PartTimeEmployee(id, name, val1, status, (int) val2);
                }

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
                    updatedEmp = new FullTimeEmployee(ID, name, val1, "Full-Time", val2);
                } else {
                    updatedEmp = new PartTimeEmployee(ID, name, val1, "Part-Time", (int) val2);
                }

                UpdateUser update = new UpdateUser(updatedEmp);
                update.execute(staffList);
                
                showEmployee(staffList, pane);
                stage.close();
            } catch (Exception ex) {
                System.out.println("Update failed: Check numbers format.");
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

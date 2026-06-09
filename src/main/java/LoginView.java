import javafx.animation.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import Employee.Employee;

public class LoginView {

    private final Stage stage;
    private final AuthController auth = new AuthController();
    private BorderPane root;

    private TextField     emailField;
    private PasswordField passwordField;
    private Label         errorLabel;
    private Button        loginBtn;

    public LoginView(Stage stage) {
        this.stage = stage;
        build();
    }

    public BorderPane getRoot() { return root; }

    private void build() {
        root = new BorderPane();

        StackPane leftPanel = buildLeftPanel();
        leftPanel.setPrefWidth(360);

        VBox rightPanel = buildRightPanel();
        rightPanel.setPrefWidth(540);

        root.setLeft(leftPanel);
        root.setCenter(rightPanel);
    }

    private StackPane buildLeftPanel() {
        StackPane panel = new StackPane();
        panel.getStyleClass().add("left-panel");

        VBox content = new VBox(16);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

        Label logo = new Label("💼");
        logo.setStyle("-fx-font-size: 64px;");

        Text title = new Text("PayrollPro");
        title.getStyleClass().add("brand-title");

        Text subtitle = new Text("Employee Payroll System");
        subtitle.getStyleClass().add("brand-subtitle");

        Separator sep = new Separator();
        sep.setPrefWidth(160);
        sep.setStyle("-fx-background-color: rgba(255,255,255,0.3);");

        Text tagline = new Text("Manage salaries,\nleave, and more —\nall in one place.");
        tagline.getStyleClass().add("brand-tagline");
        tagline.setTextAlignment(TextAlignment.CENTER);

        content.getChildren().addAll(logo, title, subtitle, sep, tagline);
        panel.getChildren().add(content);
        return panel;
    }

    private VBox buildRightPanel() {
        VBox panel = new VBox(20);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(60, 70, 60, 70));
        panel.getStyleClass().add("right-panel");

        Text heading = new Text("Welcome Back");
        heading.getStyleClass().add("form-heading");

        Text sub = new Text("Sign in to your account");
        sub.getStyleClass().add("form-subheading");

        // Email field
        VBox emailBox = new VBox(6);
        Label emailLbl = new Label("Email Address");
        emailLbl.getStyleClass().add("field-label");
        emailField = new TextField();
        emailField.setPromptText("you@company.com");
        emailField.getStyleClass().add("form-field");
        emailBox.getChildren().addAll(emailLbl, emailField);

        // Password field
        VBox passBox = new VBox(6);
        Label passLbl = new Label("Password");
        passLbl.getStyleClass().add("field-label");
        passwordField = new PasswordField();
        passwordField.setPromptText("••••••••");
        passwordField.getStyleClass().add("form-field");
        passBox.getChildren().addAll(passLbl, passwordField);

        // Error label
        errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);
        errorLabel.setWrapText(true);

        // Login button
        loginBtn = new Button("Sign In");
        loginBtn.getStyleClass().add("primary-btn");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setOnAction(e -> handleLogin());

        // Enter key shortcuts
        emailField.setOnAction(e -> passwordField.requestFocus());
        passwordField.setOnAction(e -> handleLogin());

        // Divider
        HBox divider = new HBox(10);
        divider.setAlignment(Pos.CENTER);
        Separator s1 = new Separator(); HBox.setHgrow(s1, Priority.ALWAYS);
        Separator s2 = new Separator(); HBox.setHgrow(s2, Priority.ALWAYS);
        Label orLabel = new Label("OR");
        orLabel.getStyleClass().add("divider-label");
        divider.getChildren().addAll(s1, orLabel, s2);

        // Register link
        HBox registerRow = new HBox(6);
        registerRow.setAlignment(Pos.CENTER);
        Label noAccount = new Label("Don't have an account?");
        noAccount.getStyleClass().add("muted-text");
        Hyperlink registerLink = new Hyperlink("Create one now");
        registerLink.getStyleClass().add("link-text");
        registerLink.setOnAction(e -> switchToRegister());
        registerRow.getChildren().addAll(noAccount, registerLink);

        // Demo credentials box
        VBox demoBox = new VBox(4);
        demoBox.setAlignment(Pos.CENTER);
        demoBox.getStyleClass().add("demo-box");
        Label demoTitle = new Label("Demo Accounts");
        demoTitle.getStyleClass().add("demo-title");
        Label demo1 = new Label("Admin:    admin@payroll.com  /  Admin@123");
        Label demo2 = new Label("Employee: john@payroll.com   /  Employee@1");
        demo1.getStyleClass().add("demo-cred");
        demo2.getStyleClass().add("demo-cred");
        demoBox.getChildren().addAll(demoTitle, demo1, demo2);

        panel.getChildren().addAll(
                heading, sub,
                emailBox, passBox,
                errorLabel,
                loginBtn,
                divider,
                registerRow,
                demoBox
        );

        // Visual fade-in effect when panel updates
        FadeTransition ft = new FadeTransition(Duration.millis(600), panel);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        return panel;
    }

    private void handleLogin() {
        loginBtn.setText("Signing in…");
        loginBtn.setDisable(true);
        errorLabel.setVisible(false);

        try {
            // Validate credentials through the Authentication pipeline
            User user = auth.login(
                emailField.getText().trim(),
                passwordField.getText()
            );

            // ==================== MATCH USER TO EMPLOYEE ====================
            Employee loggedInUser = null;
            
            // Loop through the staff list in AdminPage to find a match.
            // (Assuming you match them by Name, or you can use an ID/Email if your User class has it)
            for (Employee emp : AdminPage.getStaffList()) { 
                if (emp.getName().equalsIgnoreCase(user.getFullName())) { 
                    loggedInUser = emp;
                    break;
                }
            }

            // Fallback safety feature: If no match is found, assign a default dummy 
            // so the system doesn't crash, or handle it as an error.
            if (loggedInUser == null && !AdminPage.getStaffList().isEmpty()) {
                loggedInUser = AdminPage.getStaffList().get(0); 
            }
            // ================================================================

            // Trigger the stage transition using your class's 'stage' variable
            AdminPage.showAdminDashboard(stage, loggedInUser);

        } catch (AuthController.AuthException ex) {
            errorLabel.setText("⚠  " + ex.getMessage());
            errorLabel.setVisible(true);

            // Shake error visual feedback animation
            TranslateTransition shake =
                new TranslateTransition(Duration.millis(60), errorLabel);
            shake.setByX(10);
            shake.setCycleCount(6);
            shake.setAutoReverse(true);
            shake.play();
        } finally {
            // Note: We only reset UI states if authentication didn't pass or exit smoothly
            loginBtn.setText("Sign In");
            loginBtn.setDisable(false);
        }
    }

    private void switchToRegister() {
        RegisterView reg = new RegisterView(stage);
        stage.getScene().setRoot(reg.getRoot());
    }
}
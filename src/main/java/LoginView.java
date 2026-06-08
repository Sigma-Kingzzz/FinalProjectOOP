import javafx.animation.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

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
            User user = auth.login(
                emailField.getText().trim(),
                passwordField.getText()
            );

        } catch (AuthController.AuthException ex) {
            errorLabel.setText("⚠  " + ex.getMessage());
            errorLabel.setVisible(true);

            TranslateTransition shake =
                new TranslateTransition(Duration.millis(60), errorLabel);
            shake.setByX(10);
            shake.setCycleCount(6);
            shake.setAutoReverse(true);
            shake.play();
        } finally {
            loginBtn.setText("Sign In");
            loginBtn.setDisable(false);
        }
    }

    private void switchToRegister() {
        RegisterView reg = new RegisterView(stage);
        stage.getScene().setRoot(reg.getRoot());
    }
}
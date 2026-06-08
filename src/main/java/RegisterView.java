import javafx.animation.FadeTransition;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RegisterView {

    private final Stage stage;
    private final AuthController auth = new AuthController();
    private BorderPane root;

    private TextField     nameField, emailField, extraField;
    private PasswordField passField, confirmField;
    private ComboBox<String> roleCombo;
    private Label         errorLabel, extraLabel;
    private Button        registerBtn;

    public RegisterView(Stage stage) {
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

        Label logo = new Label("📋");
        logo.setStyle("-fx-font-size: 64px;");

        Text title = new Text("PayrollPro");
        title.getStyleClass().add("brand-title");

        Text subtitle = new Text("New Account Setup");
        subtitle.getStyleClass().add("brand-subtitle");

        Separator sep = new Separator();
        sep.setPrefWidth(160);
        sep.setStyle("-fx-background-color: rgba(255,255,255,0.3);");

        Text tagline = new Text("Join your team and\nmanage your payroll\nwith ease.");
        tagline.getStyleClass().add("brand-tagline");
        tagline.setTextAlignment(TextAlignment.CENTER);

        content.getChildren().addAll(logo, title, subtitle, sep, tagline);
        panel.getChildren().add(content);
        return panel;
    }

    private VBox buildRightPanel() {
        VBox panel = new VBox(14);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(40, 70, 40, 70));
        panel.getStyleClass().add("right-panel");

        Text heading = new Text("Create Account");
        heading.getStyleClass().add("form-heading");

        Text sub = new Text("Fill in the details below to register");
        sub.getStyleClass().add("form-subheading");

        // Full Name
        VBox nameBox = new VBox(6);
        Label nameLbl = new Label("Full Name");
        nameLbl.getStyleClass().add("field-label");
        nameField = new TextField();
        nameField.setPromptText("Ahmad Solehuddin");
        nameField.getStyleClass().add("form-field");
        nameBox.getChildren().addAll(nameLbl, nameField);

        // Email
        VBox emailBox = new VBox(6);
        Label emailLbl = new Label("Email Address");
        emailLbl.getStyleClass().add("field-label");
        emailField = new TextField();
        emailField.setPromptText("you@company.com");
        emailField.getStyleClass().add("form-field");
        emailBox.getChildren().addAll(emailLbl, emailField);

        // Role
        VBox roleBox = new VBox(6);
        Label roleLbl = new Label("Role");
        roleLbl.getStyleClass().add("field-label");
        roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("Employee", "Admin");
        roleCombo.setValue("Employee");
        roleCombo.getStyleClass().add("form-field");
        roleCombo.setMaxWidth(Double.MAX_VALUE);
        roleBox.getChildren().addAll(roleLbl, roleCombo);

        // Dynamic extra field
        VBox extraBox = new VBox(6);
        extraLabel = new Label("Position / Job Title");
        extraLabel.getStyleClass().add("field-label");
        extraField = new TextField();
        extraField.setPromptText("e.g. Software Engineer");
        extraField.getStyleClass().add("form-field");
        extraBox.getChildren().addAll(extraLabel, extraField);

        roleCombo.setOnAction(e -> {
            boolean isAdmin = "Admin".equals(roleCombo.getValue());
            extraLabel.setText(isAdmin ? "Department" : "Position / Job Title");
            extraField.setPromptText(isAdmin
                ? "e.g. Human Resources"
                : "e.g. Software Engineer");
        });

        // Password row
        HBox passRow = new HBox(14);

        VBox passBox = new VBox(6);
        Label passLbl = new Label("Password");
        passLbl.getStyleClass().add("field-label");
        passField = new PasswordField();
        passField.setPromptText("Min. 6 characters");
        passField.getStyleClass().add("form-field");
        passBox.getChildren().addAll(passLbl, passField);
        HBox.setHgrow(passBox, Priority.ALWAYS);

        VBox confirmBox = new VBox(6);
        Label confirmLbl = new Label("Confirm Password");
        confirmLbl.getStyleClass().add("field-label");
        confirmField = new PasswordField();
        confirmField.setPromptText("Repeat password");
        confirmField.getStyleClass().add("form-field");
        confirmBox.getChildren().addAll(confirmLbl, confirmField);
        HBox.setHgrow(confirmBox, Priority.ALWAYS);

        passRow.getChildren().addAll(passBox, confirmBox);

        // Error label
        errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);
        errorLabel.setWrapText(true);

        // Register button
        registerBtn = new Button("Create Account");
        registerBtn.getStyleClass().add("primary-btn");
        registerBtn.setMaxWidth(Double.MAX_VALUE);
        registerBtn.setOnAction(e -> handleRegister());

        // Back to login
        HBox loginRow = new HBox(6);
        loginRow.setAlignment(Pos.CENTER);
        Label already = new Label("Already have an account?");
        already.getStyleClass().add("muted-text");
        Hyperlink loginLink = new Hyperlink("Sign in");
        loginLink.getStyleClass().add("link-text");
        loginLink.setOnAction(e -> switchToLogin());
        loginRow.getChildren().addAll(already, loginLink);

        panel.getChildren().addAll(
                heading, sub,
                nameBox, emailBox, roleBox, extraBox,
                passRow,
                errorLabel,
                registerBtn,
                loginRow
        );

        FadeTransition ft = new FadeTransition(Duration.millis(600), panel);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        return panel;
    }

    private void handleRegister() {
        registerBtn.setText("Creating account…");
        registerBtn.setDisable(true);
        errorLabel.setVisible(false);

        try {
            User user = auth.register(
                    nameField.getText(),
                    emailField.getText(),
                    passField.getText(),
                    confirmField.getText(),
                    roleCombo.getValue(),
                    extraField.getText()
            );

        } catch (AuthController.AuthException ex) {
            errorLabel.setText("⚠  " + ex.getMessage());
            errorLabel.setVisible(true);
        } finally {
            registerBtn.setText("Create Account");
            registerBtn.setDisable(false);
        }
    }

    private void switchToLogin() {
        LoginView login = new LoginView(stage);
        stage.getScene().setRoot(login.getRoot());
    }
}

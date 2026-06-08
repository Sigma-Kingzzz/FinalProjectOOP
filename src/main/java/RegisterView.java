
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;


public class RegisterView {

    private final Stage stage;
    private final AuthController auth = new AuthController();
    private BorderPane root;

    private TextField     nameField, emailField, extraField;
    private PasswordField passField, confirmField;
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

        // Dynamic extra field (Permanently set for Employee context)
        VBox extraBox = new VBox(6);
        extraLabel = new Label("Position / Job Title");
        extraLabel.getStyleClass().add("field-label");
        extraField = new TextField();
        extraField.setPromptText("e.g. Software Engineer");
        extraField.getStyleClass().add("form-field");
        extraBox.getChildren().addAll(extraLabel, extraField);

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
        HBox.setHgrow(confirmBox, Priority.ALWAYS

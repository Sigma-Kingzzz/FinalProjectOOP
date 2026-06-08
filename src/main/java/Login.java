import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage LoginStage) {
        primaryStage = LoginStage;
        LoginStage.setTitle("Employee Payroll System");
        LoginStage.setResizable(false);

        LoginView loginView = new LoginView(LoginStage);
        Scene scene = new Scene(loginView.getRoot(), 900, 600);

        LoginStage.setScene(scene);
        LoginStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
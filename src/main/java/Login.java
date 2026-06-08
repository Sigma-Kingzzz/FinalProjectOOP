import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        stage.setTitle("Employee Payroll System");
        stage.setResizable(false);

        LoginView loginView = new LoginView(stage);
        Scene scene = new Scene(loginView.getRoot(), 900, 600);
        scene.getStylesheets().add(
            getClass().getResource("style.css").toExternalForm()
        );

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
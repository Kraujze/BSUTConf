package isit.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768, true, SceneAntialiasing.BALANCED);
        stage.getIcons().add(new Image(Application.class.getResourceAsStream("images/app.png")));
        stage.setTitle("Конференция");
        stage.setScene(scene);
        stage.show();
        stage.show();
    }

    public static void main(String[] args) {
        System.setProperty("prism.dirtyopts", "false");
        launch(args);
    }
}

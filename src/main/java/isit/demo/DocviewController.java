package isit.demo;

import javafx.fxml.Initializable;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;



public class DocviewController implements Initializable {

    public ScrollPane scrollPane;
    public VBox content;

    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setContent(content);
        scrollPane.setFitToWidth(true);
    }
}
package isit.demo;

import javafx.scene.control.Alert;

public class ShowMessage {
    public ShowMessage(String title, String header, String message, Alert.AlertType type) {
        Alert alert = new Alert(type, message);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}

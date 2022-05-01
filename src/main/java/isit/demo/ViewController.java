package isit.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewController {
    public TextField powerField;
    public TextField airTemperatureField;
    public TextField temperatureField;
    public ImageView tumblerView;
    public Button tumblerBtn1;
    public Button tumblerBtn2;
    public Button tumblerBtn3;
    public Button tumblerBtn4;
    public Button tumblerBtn5;
    public Button tumblerBtn6;
    public Button startBtn;
    public Label label1;
    public Label label2;
    public Label label3;
    public VBox modelBox;
    public Tumbler tumbler;
    public Button editTemperatureBtn;

    @FXML
    public void initialize() {
        ModelController model = new ModelController(600, 493);
        Group group = model.buildScene();
        modelBox.getChildren().addAll(
                model.createControls(model.rotate3dGroup(group)),
                model.createScene3D(group)
        );
        modelBox.relocate(50, 502);
        tumbler = new Tumbler(
                new ArrayList<>(Arrays.asList(tumblerBtn1, tumblerBtn2, tumblerBtn3,
                    tumblerBtn4, tumblerBtn5, tumblerBtn6)),
                tumblerView
        );
        airTemperatureField.setText(String.valueOf((int) Math.ceil(Math.random()*5+18)));
        ImageView editIcon = new ImageView(new Image(this.getClass().getResourceAsStream("images/edit.png")));
        editIcon.setFitWidth(15); editIcon.setFitHeight(15);
        editTemperatureBtn.setGraphic(editIcon);
        addFieldFocusCheck(airTemperatureField);
        addFieldFocusCheck(powerField);
        startBtn.relocate(334, 500);
    }
    public void addFieldFocusCheck(TextField textField) {
        textField.focusedProperty().addListener((obs, oldVal, newVal)-> {
            if (!newVal) {
                textField.getStyleClass().removeAll("checkError", "checkSuccess");
                if (!textField.getText().matches("[0-9]*\\.?[0-9]+")) {
                    textField.getStyleClass().add("checkError");
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Поле может содержать только числа!");
                    alert.setTitle("Внимание");
                    alert.setHeaderText("Неверный формат записи");
                    alert.showAndWait();
                    textField.requestFocus();
                } else {
                    textField.getStyleClass().add("checkSuccess");
                }
            }
        });
    }
    public void start() {
        try {
            double averageTemperature = Math.pow(Double.parseDouble(powerField.getText()) / 0.1271, 0.8) +
                    Double.parseDouble(airTemperatureField.getText());
            tumbler.calculate(averageTemperature / 6);
            tumbler.setDisableForAllButtons(false);
            temperatureField.setText(String.valueOf(tumbler.getTemperatureAtPos(1)));
            tumbler.switchButtonToActive(1);
        } catch (NumberFormatException e) {
            powerField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            powerField.requestFocus();
            Alert alert = new Alert(Alert.AlertType.WARNING, "Необходимо заполнить поле \"Мощность\"!");
            alert.setTitle("Внимание");
            alert.setHeaderText("Произошла ошибка");
            alert.showAndWait();

        }
    }

    public void turnTumbler(ActionEvent actionEvent) {
        int buttonNumber = Integer.parseInt(((Button) actionEvent.getSource()).getText());
        tumbler.switchButtonToActive(buttonNumber);
        temperatureField.setText(String.valueOf(tumbler.getTemperatureAtPos(buttonNumber)));
        tumbler.setView(String.format("images/Тумблер%d.png", buttonNumber));
    }

    public void editButtonClick() {
        if (!editTemperatureBtn.getStyleClass().contains("enabled")) {
            editTemperatureBtn.getStyleClass().add("enabled");
            airTemperatureField.setEditable(true);
        } else {
            editTemperatureBtn.getStyleClass().remove("enabled");
            airTemperatureField.setEditable(false);
        }
    }
}
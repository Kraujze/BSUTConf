package isit.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ViewController {
    public double averageTemperature;
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
    public Button theoryBtn;
    public TableView<MeasureModel> tableView;
    public TableColumn<MeasureModel, String> powerCol;
    public TableColumn<MeasureModel, String> tempCol_1;
    public TableColumn<MeasureModel, String> tempCol_2;
    public TableColumn<MeasureModel, String> tempCol_3;
    public TableColumn<MeasureModel, String> tempCol_4;
    public TableColumn<MeasureModel, String> tempCol_5;
    public TableColumn<MeasureModel, String> tempCol_6;
    public TableColumn<MeasureModel, String> avgTempCol;
    public TableColumn<MeasureModel, String> airTempCol;
    public Button stopBtn;

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
        airTemperatureField.setText(String.valueOf((int) Math.ceil(Math.random()*5+17)));
        ImageView editIcon = new ImageView(new Image(this.getClass().getResourceAsStream("images/edit.png")));
        editIcon.setFitWidth(15); editIcon.setFitHeight(15);
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
                    new ShowMessage("Внимание",
                            "Неверный формат записи",
                            "Поле может содержать только числа!",
                            Alert.AlertType.WARNING
                    );
                    textField.requestFocus();
                }
                else if (Objects.equals(textField.getId(), "powerField")) {
                        if (Double.parseDouble(powerField.getText()) > 100) {
                            powerField.getStyleClass().removeAll("checkError", "checkSuccess");
                            powerField.getStyleClass().add("checkError");
                            new ShowMessage("Внимание",
                                    "Произошла ошибка",
                                    "Значение в поле \"Мощность\" должно находиться в диапазоне 0–100 Вт!",
                                    Alert.AlertType.WARNING
                            );
                    } else textField.getStyleClass().add("checkSuccess");
                } else if (Double.parseDouble(airTemperatureField.getText()) < 15 || Double.parseDouble(airTemperatureField.getText()) > 40) {
                        airTemperatureField.getStyleClass().removeAll("checkError", "checkSuccess");
                        airTemperatureField.getStyleClass().add("checkError");
                        new ShowMessage("Внимание",
                                "Произошла ошибка",
                                "Значение в поле \"Температура воздуха\" должно находиться в диапазоне 15–40 ℃!",
                                Alert.AlertType.WARNING
                        );
                    } else textField.getStyleClass().add("checkSuccess");
                }
            }
        );
    }
    public void start() {
        try {
            /*if (Double.parseDouble(powerField.getText()) > 100) {
                powerField.getStyleClass().removeAll("checkError", "checkSuccess");
                powerField.getStyleClass().add("checkError");
                new ShowMessage("Внимание",
                        "Произошла ошибка",
                        "Значение в поле \"Мощность\" должно находиться в диапазоне 20–70 Вт!",
                        Alert.AlertType.WARNING
                );
                return;
            }
            if (Double.parseDouble(airTemperatureField.getText()) < 15 || Double.parseDouble(airTemperatureField.getText()) > 40) {
                powerField.getStyleClass().removeAll("checkError", "checkSuccess");
                powerField.getStyleClass().add("checkError");
                new ShowMessage("Внимание",
                        "Произошла ошибка",
                        "Значение в поле \"Температура воздуха\" должно находиться в диапазоне 15–40 ℃!",
                        Alert.AlertType.WARNING
                );
                return;
            }*/
            averageTemperature = Math.pow(Double.parseDouble(powerField.getText()) / 0.1271, 0.8) +
                    Double.parseDouble(airTemperatureField.getText());
            tumbler.calculate(averageTemperature / 6);
            tumbler.setDisableForAllButtons(false);
            temperatureField.setText(String.valueOf(tumbler.getTemperatureAtPos(1)));
            tumbler.switchButtonToActive(1);
            initializeTable();
            stopBtn.setDisable(false);
            startBtn.setDisable(true);
        } catch (NumberFormatException e) {
            powerField.getStyleClass().removeAll("checkError", "checkSuccess");
            powerField.getStyleClass().add("checkError");
            new ShowMessage("Внимание",
                    "Произошла ошибка",
                    "Необходимо заполнить поле \"Мощность\"!",
                    Alert.AlertType.WARNING
            );
        }
    }

    public void initializeTable() {
        powerCol.setCellValueFactory(new PropertyValueFactory<>("power"));
        tempCol_1.setCellValueFactory(new PropertyValueFactory<>("temp_1"));
        tempCol_2.setCellValueFactory(new PropertyValueFactory<>("temp_2"));
        tempCol_3.setCellValueFactory(new PropertyValueFactory<>("temp_3"));
        tempCol_4.setCellValueFactory(new PropertyValueFactory<>("temp_4"));
        tempCol_5.setCellValueFactory(new PropertyValueFactory<>("temp_5"));
        tempCol_6.setCellValueFactory(new PropertyValueFactory<>("temp_6"));
        avgTempCol.setCellValueFactory(new PropertyValueFactory<>("avgTemp"));
        airTempCol.setCellValueFactory(new PropertyValueFactory<>("airTemp"));
        tableView.getItems().add(
                new MeasureModel(powerField.getText(), "", airTemperatureField.getText())
        );
        tableView.getItems().get(0).setTemp_1(String.valueOf(tumbler.getTemperatureAtPos(1)));
    }

    public void turnTumbler(ActionEvent actionEvent) {
        int buttonNumber = Integer.parseInt(((Button) actionEvent.getSource()).getText());
        tumbler.switchButtonToActive(buttonNumber);
        temperatureField.setText(String.valueOf(tumbler.getTemperatureAtPos(buttonNumber)));
        tumbler.setView(String.format("images/Тумблер%d.png", buttonNumber));
        MeasureModel newData = tableView.getItems().get(0);
        switch(buttonNumber) {
            case 1:
                newData.setTemp_1(String.valueOf(tumbler.getTemperatureAtPos(1)));
                break;
            case 2:
                newData.setTemp_2(String.valueOf(tumbler.getTemperatureAtPos(2)));
                break;
            case 3:
                newData.setTemp_3(String.valueOf(tumbler.getTemperatureAtPos(3)));
                break;
            case 4:
                newData.setTemp_4(String.valueOf(tumbler.getTemperatureAtPos(4)));
                break;
            case 5:
                newData.setTemp_5(String.valueOf(tumbler.getTemperatureAtPos(5)));
                break;
            case 6:
                newData.setTemp_6(String.valueOf(tumbler.getTemperatureAtPos(6)));
        }
        if (newData.isFull()) newData.setAvgTemp(String.valueOf(tumbler.getAverageTemperature()));
        tableView.getItems().set(0, newData);
    }

    public void gotoTheory() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/doc.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768, true, SceneAntialiasing.BALANCED);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Методические указания");
        stage.setScene(scene);
        stage.show();
    }

    public void stop() {
        tableView.getItems().clear();
        tumbler.setDisableForAllButtons(true);
        stopBtn.setDisable(true);
        startBtn.setDisable(false);
        tumbler.setView("images/Тумблер1.png");
        tumbler.switchButtonToActive(0);
        temperatureField.setText("...");
    }

    public void onAirEdit(KeyEvent keyEvent) {
        if (airTemperatureField.getLength() > 12) {
            airTemperatureField.setText(airTemperatureField.getText().substring(0, airTemperatureField.getLength() - 1));
            airTemperatureField.positionCaret(airTemperatureField.getLength());
        }
    }

    public void onPowerEdit(KeyEvent keyEvent) {
        if (powerField.getLength() > 12) {
            powerField.setText(powerField.getText().substring(0, powerField.getLength() - 1));
            powerField.positionCaret(powerField.getLength());
        }
    }
}
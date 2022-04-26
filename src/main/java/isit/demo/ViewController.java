package isit.demo;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;

import java.net.URL;
import java.util.Collection;

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
    public double[] tumblerValues = new double[7];
    public Label label1;
    public Label label2;
    public Label label3;
    public VBox modelBox;

    @FXML
    public void initialize() {
        ModelController model = new ModelController(600, 160);
        Group group = model.buildScene();
        modelBox.getChildren().addAll(
                model.createControls(model.rotate3dGroup(group)),
                model.createScene3D(group)
        );

        double tumblerViewCenterX = tumblerView.getImage().getWidth()/2;
        double tumblerViewCenterY = tumblerView.getImage().getHeight()/2;
        tumblerView.relocate(512 - tumblerViewCenterX, tumblerView.getY());
        tumblerBtn1.relocate(512 - 126, tumblerViewCenterY - 15);
        tumblerBtn2.relocate(512 - 108, tumblerViewCenterY - 75);
        tumblerBtn3.relocate(512 - 54, tumblerViewCenterY - 110);
        tumblerBtn4.relocate(512 + 18, tumblerViewCenterY - 110);
        tumblerBtn5.relocate(512 + 72, tumblerViewCenterY - 75);
        tumblerBtn6.relocate(512 + 92, tumblerViewCenterY - 15);
        airTemperatureField.setText(String.valueOf((int) Math.ceil(Math.random()*5+18)));
        startBtn.setMinSize(100,45);
    }

    public void start(ActionEvent actionEvent) {
        double averageTemperature = Math.pow(Double.parseDouble(powerField.getText()) / 0.1271, 0.8) +
                                    Double.parseDouble(airTemperatureField.getText());
        tumblerValues[0] = averageTemperature / 6;
        double at = tumblerValues[0]/1000 + Math.random() * (0.5-(-0.5) - 0.5);
        tumblerValues[1] = (tumblerValues[0] + (at/2) + (at/4)) * 6;
        tumblerValues[2] = (tumblerValues[0] + ((at/2)*4)) * 6;
        tumblerValues[3] = (tumblerValues[0] - ((at/2)*2 - 2 * at)) * 6;
        tumblerValues[4] = (tumblerValues[0] - ((at/2)*2 + 2 * at)) * 6;
        tumblerValues[5] = (tumblerValues[0] - at) * 6;
        tumblerValues[6] = (tumblerValues[0] + at/2 - (at/4)) * 6;
        tumblerBtn1.setDisable(false); tumblerBtn2.setDisable(false); tumblerBtn3.setDisable(false);
        tumblerBtn4.setDisable(false); tumblerBtn5.setDisable(false); tumblerBtn6.setDisable(false);
        temperatureField.setText(String.valueOf(tumblerValues[1]));
        tumblerBtn1.getStyleClass().add("active"); tumblerBtn2.getStyleClass().removeAll("active");
        tumblerBtn3.getStyleClass().removeAll("active"); tumblerBtn4.getStyleClass().removeAll("active");
        tumblerBtn5.getStyleClass().removeAll("active"); tumblerBtn6.getStyleClass().removeAll("active");
        tumblerView.setImage(new Image(String.format("file:/C:/Users/krauj/IdeaProjects/conf/target/classes/isit/demo/images/Тумблер%d.png", 1)));
    }

    public void turnTumbler(ActionEvent actionEvent) {
        int buttonNumber = Integer.parseInt(((Button) actionEvent.getSource()).getText());
        switch(buttonNumber) {
            case 1: tumblerBtn1.getStyleClass().add("active"); tumblerBtn2.getStyleClass().removeAll("active");
                    tumblerBtn3.getStyleClass().removeAll("active"); tumblerBtn4.getStyleClass().removeAll("active");
                    tumblerBtn5.getStyleClass().removeAll("active"); tumblerBtn6.getStyleClass().removeAll("active");
                    break;
            case 2: tumblerBtn1.getStyleClass().removeAll("active"); tumblerBtn2.getStyleClass().add("active");
                tumblerBtn3.getStyleClass().removeAll("active"); tumblerBtn4.getStyleClass().removeAll("active");
                tumblerBtn5.getStyleClass().removeAll("active"); tumblerBtn6.getStyleClass().removeAll("active");
                break;
            case 3: tumblerBtn1.getStyleClass().removeAll("active"); tumblerBtn2.getStyleClass().removeAll("active");
                tumblerBtn3.getStyleClass().add("active"); tumblerBtn4.getStyleClass().removeAll("active");
                tumblerBtn5.getStyleClass().removeAll("active"); tumblerBtn6.getStyleClass().removeAll("active");
                break;
            case 4: tumblerBtn1.getStyleClass().removeAll("active"); tumblerBtn2.getStyleClass().removeAll("active");
                tumblerBtn3.getStyleClass().removeAll("active"); tumblerBtn4.getStyleClass().add("active");
                tumblerBtn5.getStyleClass().removeAll("active"); tumblerBtn6.getStyleClass().removeAll("active");
                break;
            case 5: tumblerBtn1.getStyleClass().removeAll("active"); tumblerBtn2.getStyleClass().removeAll("active");
                tumblerBtn3.getStyleClass().removeAll("active"); tumblerBtn4.getStyleClass().removeAll("active");
                tumblerBtn5.getStyleClass().add("active"); tumblerBtn6.getStyleClass().removeAll("active");
                break;
            case 6: tumblerBtn1.getStyleClass().removeAll("active"); tumblerBtn2.getStyleClass().removeAll("active");
                tumblerBtn3.getStyleClass().removeAll("active"); tumblerBtn4.getStyleClass().removeAll("active");
                tumblerBtn5.getStyleClass().removeAll("active"); tumblerBtn6.getStyleClass().add("active");
                break;
        }
        temperatureField.setText(String.valueOf(tumblerValues[buttonNumber]));
        tumblerView.setImage(new Image(String.format("file:/C:/Users/krauj/IdeaProjects/conf/target/classes/isit/demo/images/Тумблер%d.png", buttonNumber)));
        //System.out.println(tumblerView.getImage().getUrl());
    }
}
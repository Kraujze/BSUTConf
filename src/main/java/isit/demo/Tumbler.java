package isit.demo;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Tumbler {
    private final double[] values = new double[6];
    public ArrayList<Button> buttonList= new ArrayList<>();

    public ImageView imageView;
    public Tumbler(ArrayList<Button> buttonsList, ImageView imgView) {
        imageView = imgView;
        buttonList.addAll(buttonsList);
        relocateItems();
    }
    public void calculate(double averageTemperature) {
        double at = averageTemperature/1000 + Math.random() * (0.5-(-0.5) - 0.5);
        values[0] = Math.round(((averageTemperature + (at/2) + (at/4)) * 6) * 10.0) / 10.0;
        values[1] = Math.round(((averageTemperature + ((at/2)*4)) * 6) * 10.0) / 10.0;
        values[2] = Math.round(((averageTemperature - ((at/2)*2 - 2 * at)) * 6) * 10.0) / 10.0;
        values[3] = Math.round(((averageTemperature - ((at/2)*2 + 2 * at)) * 6) * 10.0) / 10.0;
        values[4] = Math.round(((averageTemperature - at) * 6) * 10.0) / 10.0;
        values[5] = Math.round(((averageTemperature + at/2 - (at/4)) * 6) * 10.0) / 10.0;

    }
    public double getTemperatureAtPos(int position) {
        return values[position - 1];
    }
    public double getAverageTemperature() {
        double avg = 0;
        for (double v : values) {
            avg += v;
        }
        return Math.round(avg / 6 * 10.0) / 10.0;
    }
    public void setView(String imageName) {
        imageView.setImage(new Image(this.getClass().getResourceAsStream(imageName)));
    }
    private void relocateItems() {
        double tumblerViewCenterX = imageView.getImage().getWidth()/2;
        double tumblerViewCenterY = imageView.getImage().getHeight()/2;
        imageView.relocate(192 - tumblerViewCenterX, imageView.getY());
        buttonList.get(0).relocate(192 - 126, tumblerViewCenterY - 15);
        buttonList.get(1).relocate(192 - 108, tumblerViewCenterY - 75);
        buttonList.get(2).relocate(192 - 54, tumblerViewCenterY - 110);
        buttonList.get(3).relocate(192 + 18, tumblerViewCenterY - 110);
        buttonList.get(4).relocate(192 + 72, tumblerViewCenterY - 75);
        buttonList.get(5).relocate(192 + 92, tumblerViewCenterY - 15);
    }
    public void setDisableForAllButtons(boolean isDisable) {
        for (Button button : buttonList) {
            button.setDisable(isDisable);
        }
    }
    public void switchButtonToActive(int buttonNumber) {
        if (buttonNumber > 0) {
            for (Button button : buttonList) {
                if (button == buttonList.get(buttonNumber - 1)) {
                    button.getStyleClass().add("active");
                } else {
                    button.getStyleClass().removeAll("active");
                }
            }
        } else for (Button button : buttonList) button.getStyleClass().removeAll("active");
    }
}

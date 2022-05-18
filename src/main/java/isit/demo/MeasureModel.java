package isit.demo;

import javafx.beans.property.SimpleStringProperty;

public class MeasureModel {
    private SimpleStringProperty power;
    private SimpleStringProperty temp_1 = new SimpleStringProperty("");
    private SimpleStringProperty temp_2 = new SimpleStringProperty("");
    private SimpleStringProperty temp_3 = new SimpleStringProperty("");
    private SimpleStringProperty temp_4 = new SimpleStringProperty("");
    private SimpleStringProperty temp_5 = new SimpleStringProperty("");
    private SimpleStringProperty temp_6 = new SimpleStringProperty("");
    private SimpleStringProperty avgTemp;
    private SimpleStringProperty airTemp;

    public MeasureModel(String power, String avgTemp, String airTemp) {
        this.power = new SimpleStringProperty(power);
        this.avgTemp = new SimpleStringProperty(avgTemp);;
        this.airTemp = new SimpleStringProperty(airTemp);;
    }

    public String getPower() {
        return power.get();
    }
    public void setPower(String power) {
        this.power.set(power);
    }
    public void setTemp_1(String value) {
        temp_1.set(value);
    }
    public String getTemp_1() {
        return temp_1.get();
    }
    public void setTemp_2(String value) {
        temp_2.set(value);
    }
    public String getTemp_2() {
        return temp_2.get();
    }
    public void setTemp_3(String value) {
        temp_3.set(value);
    }
    public String getTemp_3() {
        return temp_3.get();
    }
    public void setTemp_4(String value) {
        temp_4.set(value);
    }
    public String getTemp_4() {
        return temp_4.get();
    }
    public void setTemp_5(String value) {
        temp_5.set(value);
    }
    public String getTemp_5() {
        return temp_5.get();
    }
    public void setTemp_6(String value) {
        temp_6.set(value);
    }
    public String getTemp_6() {
        return temp_6.get();
    }
    public String getAvgTemp() {
        return avgTemp.get();
    }
    public void setAvgTemp(String avgTemp) {
        this.avgTemp.set(avgTemp);
    }
    public String getAirTemp() {
        return airTemp.get();
    }
    public void setAirTemp(String airTemp) {
        this.airTemp.set(airTemp);
    }
    public boolean isFull() {
        return temp_1.isNotEmpty().getValue() && temp_2.isNotEmpty().getValue() && temp_3.isNotEmpty().getValue() &&
                temp_4.isNotEmpty().getValue() && temp_5.isNotEmpty().getValue() && temp_6.isNotEmpty().getValue();
    }
}

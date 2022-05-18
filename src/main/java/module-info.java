module isit.demo {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires jimObjModelImporterJFX;
    requires jdk.jsobject;


    opens isit.demo to javafx.fxml;
    exports isit.demo;
}
module isit.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires jimObjModelImporterJFX;


    opens isit.demo to javafx.fxml;
    exports isit.demo;
}
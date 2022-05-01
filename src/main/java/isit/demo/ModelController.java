package isit.demo;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.URL;

public class ModelController
{
    public static double VIEWPORT_SIZE_X = 640;
    public static double VIEWPORT_SIZE_Y = 320;
    public static final double MODEL_X_OFFSET = 0;
    public static final double MODEL_Y_OFFSET = 0;
    public static final double MODEL_Z_OFFSET = 0;
    public static double mouseX = 0;
    public static double mouseY = 0;
    public static final double scaleModifier = 10;

    public MeshView pipeMeshView;
    public MeshView heaterMeshView;
    public Rotate xRotate;
    public Rotate yRotate;
    public Rotate zRotate;

    public ModelController(double sizeX, double sizeY) {
        VIEWPORT_SIZE_X = sizeX;
        VIEWPORT_SIZE_Y = sizeY;
    }
    public MeshView prepareMeshView(MeshView meshViewData) {
        MeshView meshView = new MeshView();
        meshView.setMesh(meshViewData.getMesh());
        meshView.setMaterial(meshViewData.getMaterial());
        meshView.setTranslateX(VIEWPORT_SIZE_X / 2 + MODEL_X_OFFSET);
        meshView.setTranslateY(VIEWPORT_SIZE_Y / 2 + MODEL_Y_OFFSET);
        meshView.setTranslateZ(MODEL_Z_OFFSET);
        meshView.setScaleX(200);
        meshView.setScaleY(200);
        meshView.setScaleZ(200);
        return meshView;
    }
    public MeshView loadModel(String modelName) {
        ObjModelImporter objImporter = new ObjModelImporter();
        try {
            URL modelUrl = this.getClass().getResource(modelName);
            objImporter.read(modelUrl);
        }
        catch (ImportException e) {

            System.out.println(e.getMessage());
        }
        return objImporter.getImport()[0];
    }
     public Group buildScene() {
        pipeMeshView = prepareMeshView(loadModel("pipe.obj"));
        heaterMeshView = prepareMeshView(loadModel("heater.obj"));
        heaterMeshView.setScaleY(120);
        heaterMeshView.setTranslateY(heaterMeshView.getTranslateY() + 1);
        Group group = new Group();
        group.getChildren().addAll(pipeMeshView, heaterMeshView);
        return group;
    }

    public SubScene createScene3D(Group group) {
        SubScene scene3d = new SubScene(group, VIEWPORT_SIZE_X, VIEWPORT_SIZE_Y, true, SceneAntialiasing.BALANCED);
        Camera camera = new PerspectiveCamera();
        camera.setTranslateZ(0);
        scene3d.setFill(Color.AZURE);
        scene3d.relocate(0,0);
        xRotate = new Rotate(0, VIEWPORT_SIZE_X / 2, VIEWPORT_SIZE_Y / 2, 0, Rotate.X_AXIS);
        yRotate = new Rotate(0, VIEWPORT_SIZE_X / 2, VIEWPORT_SIZE_Y / 2, 0, Rotate.Y_AXIS);
        zRotate = new Rotate(0, VIEWPORT_SIZE_X / 2, VIEWPORT_SIZE_Y / 2, 0, Rotate.Z_AXIS);

        camera.getTransforms().addAll(xRotate, yRotate, zRotate);

        scene3d.setOnScroll(event -> {
            if (event.getDeltaY() > 0) {
                group.getChildren().forEach((e) -> {
                    e.setScaleX(e.getScaleX() + 1 * scaleModifier);
                    e.setScaleY(e.getScaleY() + 1 * scaleModifier);
                    e.setScaleZ(e.getScaleZ() + 1 * scaleModifier);
                });

            } else {
                group.getChildren().forEach((e) -> {
                    e.setScaleX(e.getScaleX() - 1 * scaleModifier);
                    e.setScaleY(e.getScaleY() - 1 * scaleModifier);
                    e.setScaleZ(e.getScaleZ() - 1 * scaleModifier);
                });
            }
        });
        scene3d.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
            }
        });
        scene3d.setOnMouseDragged(event -> {
                if (event.isPrimaryButtonDown()) {
                    double mouseXnew = event.getSceneX();
                    yRotate.setAngle(yRotate.getAngle() - (mouseX - mouseXnew));
                    mouseX = mouseXnew;
                }
        });

        scene3d.setCamera(camera);
        return scene3d;
    }

    public VBox createControls(RotateTransition rotateTransition) {
        Button reset = new Button("Reset");
        reset.setOnAction(event -> {
            xRotate.setAngle(0);
            yRotate.setAngle(0);
            zRotate.setAngle(0);
        });
        Button addY = new Button("+15 Y");
        addY.setOnAction(event -> {
            yRotate.setAngle(yRotate.getAngle() + 15);
            //System.out.printf("RotateX: %f | RotateY: %f | RotateZ: %f\n", xRotate.getAngle(), yRotate.getAngle(), zRotate.getAngle());
        });
        Button addZ = new Button("-15 Z");
        addZ.setOnAction(event -> {
            zRotate.setAngle(zRotate.getAngle() - 15);
            //System.out.printf("RotateX: %f | RotateY: %f | RotateZ: %f\n", xRotate.getAngle(), yRotate.getAngle(), zRotate.getAngle());
        });
        CheckBox cullBack      = new CheckBox("Cull Back");
        pipeMeshView.cullFaceProperty().bind(
                Bindings.when(
                                cullBack.selectedProperty())
                        .then(CullFace.BACK)
                        .otherwise(CullFace.NONE)
        );
        CheckBox cullFront      = new CheckBox("Cull Back");
        pipeMeshView.cullFaceProperty().bind(
                Bindings.when(
                                cullFront.selectedProperty())
                        .then(CullFace.FRONT)
                        .otherwise(CullFace.NONE)
        );
        CheckBox wireframe = new CheckBox("\"Прозрачная\" труба");
        pipeMeshView.drawModeProperty().bind(
                Bindings.when(
                                wireframe.selectedProperty())
                        .then(DrawMode.LINE)
                        .otherwise(DrawMode.FILL)
        );

        CheckBox rotate = new CheckBox("Rotate");
        rotate.selectedProperty().addListener(observable -> {
            if (rotate.isSelected()) {
                rotateTransition.play();
            } else {
                rotateTransition.pause();
            }
        });

        VBox controls = new VBox(10, wireframe);
        controls.setPadding(new Insets(10));
        return controls;
    }

    public RotateTransition rotate3dGroup(Group group) {
        RotateTransition rotate = new RotateTransition(Duration.seconds(10), group);
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setFromAngle(0);
        rotate.setToAngle(360);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setCycleCount(RotateTransition.INDEFINITE);

        return rotate;
    }


}

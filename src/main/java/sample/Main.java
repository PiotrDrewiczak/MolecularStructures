package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.Stage;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {
    private final Group root = new Group();
    private final Xform axisGroup = new Xform();
    private final Xform moleculeGroup = new Xform();
    private final Xform world = new Xform();
    private final PerspectiveCamera camera = new PerspectiveCamera(true);
    private final Xform cameraXform = new Xform();
    private final Xform cameraXform2 = new Xform();
    private final Xform cameraXform3 = new Xform();
    private final double distanceConnection=1.55; // VALUE FOR ATOM CONNECTION
    private Scanner file;
    private ArrayList<sample.Atom> Atoms;
    private ArrayList<sample.Nodes> Nodes;

    private static final double CAMERA_INITIAL_DISTANCE = -1;
    private static final double CAMERA_INITIAL_X_ANGLE = 70.0;
    private static final double CAMERA_INITIAL_Y_ANGLE = 320.0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;
    private static final double AXIS_LENGTH = 25;
    private static final double AXIS_VALUE = 0.05; // declarate for height, width and depth of axis
    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 600;
    private static final String STAGE_TITTLE = "Visualization of molecular structures";

    private static final double CONTROL_MULTIPLIER = 0.1;
    private static final double SHIFT_MULTIPLIER = 10.0;
    private static final double MOUSE_SPEED = 0.1;
    private static final double ROTATION_SPEED = 2.0;
    private static final double TRACK_SPEED = 0.3;

    private double mousePosX;
    private double mousePosY;
    private double mouseOldX;
    private double mouseOldY;
    private double mouseDeltaX;
    private  double mouseDeltaY;

    private void handleMouse(Scene scene, final Node root) {
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
             public void handle(MouseEvent me) {
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
             public void handle(MouseEvent me) {
                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseDeltaX = (mousePosX - mouseOldX);
                mouseDeltaY = (mousePosY - mouseOldY);

                double modifier = 1.0;

                if (me.isControlDown()) {
                    modifier = CONTROL_MULTIPLIER;
                }
                if (me.isShiftDown()) {
                    modifier = SHIFT_MULTIPLIER;
                }
                if (me.isPrimaryButtonDown()) {
                    cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX*MOUSE_SPEED*modifier*ROTATION_SPEED);
                    cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY*MOUSE_SPEED*modifier*ROTATION_SPEED);
                }
                else if (me.isSecondaryButtonDown()) {
                    double z = camera.getTranslateZ();
                    double newZ = z + mouseDeltaX*MOUSE_SPEED*modifier;
                    camera.setTranslateZ(newZ);
                }
                else if (me.isMiddleButtonDown()) {
                    cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX*MOUSE_SPEED*modifier*TRACK_SPEED);
                    cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY*MOUSE_SPEED*modifier*TRACK_SPEED);
                }
            }
        });
    }
    private void handleKeyboard(Scene scene, final Node root) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case Z:
                        cameraXform2.t.setX(0.0);
                        cameraXform2.t.setY(0.0);
                        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
                        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
                        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
                        break;
                    case X:
                        axisGroup.setVisible(!axisGroup.isVisible());
                        break;
                    case V:
                        moleculeGroup.setVisible(!moleculeGroup.isVisible());
                        break;
                }
            }
        });
    }
    private void readFile(){
        Atoms = new ArrayList<sample.Atom>();
        try {
            String QualifyClassName="sample.";
            String pathName="C:\\Users\\pinioss\\Desktop\\Git[Projects]\\MolecularStructures\\src\\main\\resources\\d-ala.xyz";
            file=new Scanner(new File(pathName));
            int numberOfAtoms=Integer.parseInt(file.next());
            Atom[] instances = new Atom[numberOfAtoms];

            for (int i = 0; i < numberOfAtoms; i++) {
                String name = file.next();
                double x = file.nextDouble();
                double y = file.nextDouble();
                double z = file.nextDouble();

                Class<?> clazz = Class.forName(QualifyClassName+name);
                Constructor<?> constructor = clazz.getConstructor(Double.TYPE,Double.TYPE,Double.TYPE);
                instances[i] = (Atom) constructor.newInstance(x,y,z);
                Atoms.add(instances[i]);
            }
        } catch(Exception e) {
            System.out.println(e);
        }

            file.close();
    }
    private void buildCamera() {
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(180.0);

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
    }
    private void buildAxes() {
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(AXIS_LENGTH, AXIS_VALUE, AXIS_VALUE);
        final Box yAxis = new Box(AXIS_VALUE, AXIS_LENGTH, AXIS_VALUE);
        final Box zAxis = new Box(AXIS_VALUE, AXIS_VALUE, AXIS_LENGTH);

        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);

        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        axisGroup.setVisible(true);
        world.getChildren().addAll(axisGroup);
    }
    private void createMolecules(){
        Xform oxygenXform = new Xform();
            for(Atom o:   Atoms) {
                oxygenXform.getChildren().add(o.createAtom());
            }
            world.getChildren().addAll(oxygenXform);
    }

    private void createNodes() {
        Nodes = new ArrayList<sample.Nodes>();
        double distance = 0;
        Xform nodesXform = new Xform();
        for (int i = 0; i < Atoms.size(); i++) {
            for (int j = 0; j < Atoms.size(); j++) {
                distance = Math.sqrt(
                                Math.pow((Atoms.get(i).getPoint3D().getX()-Atoms.get(j).getPoint3D().getX()), 2) +
                                Math.pow((Atoms.get(i).getPoint3D().getY()-Atoms.get(j).getPoint3D().getY()), 2) +
                                Math.pow((Atoms.get(i).getPoint3D().getZ()-Atoms.get(j).getPoint3D().getZ()), 2));

                if (distance <= distanceConnection && distance!=0.0) {
                    sample.Nodes node = new Nodes(Atoms.get(i).getPoint3D(), Atoms.get(j).getPoint3D());
                    Nodes.add(node);
                }
            }
        }
            for(sample.Nodes n: Nodes){
                nodesXform.getChildren().add(n.createConnection());
            }
                world.getChildren().addAll(nodesXform);
    }

    @Override
    public void start(Stage primaryStage) {
        root.getChildren().add(world);
        root.setDepthTest(DepthTest.ENABLE);
        readFile();
        buildCamera();
        buildAxes();
        createMolecules();
        createNodes();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, true);
        scene.setFill(Color.DARKGREY);
        handleKeyboard(scene, world);
        handleMouse(scene, world);
        primaryStage.setTitle(STAGE_TITTLE);
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setCamera(camera);
    }
    public static void main(String[] args) {
        launch(args);
    }

}
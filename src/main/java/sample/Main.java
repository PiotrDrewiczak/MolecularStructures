package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application implements EventHandler<ActionEvent>{
    private final Group root = new Group();
    private final Xform axisGroup = new Xform();
    private final Xform moleculeGroup = new Xform();
    private final Xform world = new Xform();

    private final Xform cameraXform = new Xform();
    private final Xform cameraXform2 = new Xform();
    private final Xform cameraXform3 = new Xform();

    private ArrayList<Atom> Atoms= new ArrayList<Atom>();
    private ArrayList<Nodes> Nodes=new ArrayList<Nodes>();

    private final PerspectiveCamera camera = new PerspectiveCamera(true);
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
    private static final String QualifyClassName="sample.";
    private static final double DISTANCE_CONNECTION=1.55; // VALUE FOR ATOM CONNECTION
    private static final double CONTROL_MULTIPLIER = 0.1;
    private static final double SHIFT_MULTIPLIER = 10.0;
    private static final double MOUSE_SPEED = 0.1;
    private static final double ROTATION_SPEED = 2.0;
    private static final double TRACK_SPEED = 0.3;
    private static final String helpAllert =
            "The text file that processes the application should contain the following structure\n" +
            "1. The number of atoms should be written in the first line.\n" +
            "2. In each subsequent atom symbol, eg \"H\" and 3 variable comma values given as X, Y, Z.\n"+
            "Applies only supports \"H, C, N and O\" atoms\n" +
            "The application automatically calculates the distances between atoms, if their distance is less than 1.65, atoms are bonded.\n\n" +
            "EXAMPLE OF FILE\n" +
            "4\n" +
            "H  0,321322    1,42141     4,321321\n" +
            "N  1,321331    2,10001     0,3212\n" +
            "O  0,012321    0,42211     1,23213\n" +
            "H  0,032132    0,03213     0,0321321";
    private static final String exceptionError="Error,Bad file structure!";
    private static final String couldNotFindAtom="Error,Unknown atom symbol in file structure!";
    private double mousePosX;
    private double mousePosY;
    private double mouseOldX;
    private double mouseOldY;
    private double mouseDeltaX;
    private double mouseDeltaY;

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
                }
            }
        });
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
    private void buildMolecule(){
            for(Atom o:   Atoms) {
                moleculeGroup.getChildren().add(o.createAtom());
            }
            world.getChildren().addAll(moleculeGroup);
    }
    private void buildNodes() {
        double distanceBetweenAtoms = 0;
        for (int i = 0; i < Atoms.size(); i++) {
            for (int j = 0; j < Atoms.size(); j++) {
                distanceBetweenAtoms = Atoms.get(i).getPoint3D().distance(Atoms.get(j).getPoint3D());
                if (distanceBetweenAtoms <= DISTANCE_CONNECTION && distanceBetweenAtoms!=0.0) {
                    Nodes node = new Nodes(Atoms.get(i).getPoint3D(), Atoms.get(j).getPoint3D());
                    Nodes.add(node);
                }
            }
        }
            for(Nodes n: Nodes){
                moleculeGroup.getChildren().add(n.createConnection());
            }
                world.getChildren().addAll(moleculeGroup);
    }
    private void processTheFile(){
        int numberOfAtoms=0;
        String symbol;
        double x,y,z;
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        try{
            Scanner scanner = new Scanner(file);
            if(scanner.hasNextInt()){
                 numberOfAtoms=Integer.parseInt(scanner.next());
            }
            else{
                throw new WrongFormatException(exceptionError);
            }
            Atom[] instances = new Atom[numberOfAtoms];

            for (int i = 0; i < numberOfAtoms; i++) {
            if(scanner.hasNext()){
                symbol = scanner.next();
            }
            else{
                throw new WrongFormatException(exceptionError);
            }
                if(scanner.hasNextDouble()){
                     x = scanner.nextDouble();
                }
                else{
                    throw new WrongFormatException(exceptionError);
                }
                if(scanner.hasNextDouble()){
                    y = scanner.nextDouble();
                }
                else{
                    throw new WrongFormatException(exceptionError);
                }
                if(scanner.hasNextDouble()){
                    z = scanner.nextDouble();
                }
                else{
                    throw new WrongFormatException(exceptionError);
                }

                try{
                    Class<?> clazz = Class.forName(QualifyClassName + symbol);
                    Constructor<?> constructor = clazz.getConstructor(Double.TYPE, Double.TYPE, Double.TYPE);
                    instances[i] = (Atom) constructor.newInstance(x, y, z);
                    Atoms.add(instances[i]);
                }catch(ClassNotFoundException e){
                    throw new WrongFormatException(couldNotFindAtom);

                }
            }
            scanner.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    private void helpInformation(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(helpAllert);

        alert.showAndWait();
    }
        private MenuBar createMenu(){
        MenuBar menuBar = new MenuBar();
        Menu menuFile= new Menu("File");
        Menu menuHelp = new Menu("Help");
        MenuItem load = new MenuItem("Import configuration...");
        MenuItem readme = new MenuItem("Readme");
        load.setOnAction(this);
        readme.setOnAction(this);
        menuHelp.getItems().add(readme);
        menuFile.getItems().add(load);
        menuBar.getMenus().add(menuFile);
        menuBar.getMenus().add(menuHelp);

        return menuBar;
    }

    @Override
    public void handle(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        String itemName = menuItem.getText();

        if("Import configuration...".equals(itemName))
        {
            processTheFile();
            buildMolecule();
            buildNodes();
        }
        if("Readme".equals(itemName)){
            helpInformation();
        }
    }


    @Override
    public void start(Stage primaryStage) {
        //3D
        root.setDepthTest(DepthTest.ENABLE);
        root.getChildren().add(world);
        SubScene subScene = new SubScene(root, 790, 600, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);

        //2D
        BorderPane bpane=new BorderPane();
        bpane.setCenter(subScene);
        bpane.setTop(createMenu());
        bpane.setPrefSize(800,10);

        buildCamera();
        buildAxes();

        Scene scene = new Scene(bpane, SCENE_WIDTH, SCENE_HEIGHT, true);
        scene.setFill(Color.DARKGREY);
        handleKeyboard(scene, world);
        handleMouse(scene, world);
        primaryStage.setTitle(STAGE_TITTLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

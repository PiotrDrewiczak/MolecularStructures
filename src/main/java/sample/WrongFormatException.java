package sample;

import javafx.scene.control.Alert;

public class WrongFormatException extends Exception {


    public WrongFormatException(String error){

        Alert alert = new Alert(Alert.AlertType.INFORMATION.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(error);

        alert.showAndWait();
    }

}

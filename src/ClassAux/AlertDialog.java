package ClassAux;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;

public class AlertDialog {

    public  Boolean alertConfirm(String titulo, String mensaje){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Maya Textil");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.setGraphic(new ImageView("/Img/question.png"));
        DialogPane dialogPane=alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/css/styleAlert.css").toExternalForm());
        Stage stage=(Stage)alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/Img/icon.png"));
        Optional<ButtonType> result=alert.showAndWait();

        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            return true;
        }else {
            return false;
        }
    }
    public Float SelectPrecion(ArrayList<Float> list){


        ChoiceDialog<Float> dialog=new ChoiceDialog<Float>(list.get(0),list.get(1),list.get(2));
        dialog.setTitle("Maya Textil");
        dialog.setHeaderText("Selecione del producto: ");
        dialog.setContentText("Precios del producto: ");
        dialog.setGraphic(new ImageView("/Img/exclamation.png"));
        DialogPane dp = dialog.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("/css/styleAlert.css").toExternalForm());
        Stage stage=(Stage)dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/Img/icon.png"));
        Optional<Float> result =dialog.showAndWait();
        if (result.isPresent()){
            return Float.parseFloat(result.get().toString());
        }else {
            return null;
        }



    }
    public  void alert(String titulo, String mensaje){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Maya Textil");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);

        alert.setGraphic(new ImageView("/Img/question.png"));
        DialogPane dialogPane=alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/css/styleAlert.css").toExternalForm());
        Stage stage=(Stage)alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/Img/icon.png"));
        Optional<ButtonType> result=alert.showAndWait();

    }
}

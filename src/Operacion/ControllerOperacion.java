package Operacion;

import ClassAux.AlertDialog;
import Operacion.Delantera.DelanteraCotrolller;

import Operacion.Estilo.DAO.Estilo;
import Operacion.Estilo.EstiloController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerOperacion  implements Initializable {
    public Button btnOperacion;
    public Button btnAtras;
    public Label lblEstilo;
    public RadioButton btnDelantera;
    public RadioButton btnTrasera;
    public RadioButton btnEnsamble;
    public BorderPane menu;
    AlertDialog alertDialog=new AlertDialog();
    private  Estilo estiloSeleccionado=null;
    private  String anterior="";
    private final String estiloBoton="Delantera" +
            "-fx-background-color:#325FD4;" +
            "-fx-text-fill:#ffffff;" +
            "-fx-background-radius:8px;" +
            "-fx-font-size:15px;"
            ;
    private final String selectBoton="" +
            "-fx-background-color:#0EC3DC;" +
            "-fx-text-fill:#ffffff;" +
            "-fx-font-size:15px;" +
            "-fx-background-radius:8px;" +
            "-fx-margin:10px 0px 10px 0px;"
            ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }




    public void SeleccionarEStilo(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Operacion/Estilo/Estilo.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            EstiloController controller=fxmlLoader.getController();
            controller.listViewEstilo.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount()==2 && event.getButton()== MouseButton.PRIMARY){
                        Estilo estilo=controller.listViewEstilo.getSelectionModel().getSelectedItem();
                        llenarEstilo(estilo);
                        Stage stage1=(Stage)controller.listViewEstilo.getScene().getWindow();
                        stage1.close();
                    }
                }
            });

            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
private void llenarEstilo(Estilo estilo){
        estiloSeleccionado=estilo;
        lblEstilo.setText(estiloSeleccionado.getNombre());

        verDelantera();
        btnDelantera.setSelected(true);
        btnEnsamble.setSelected(false);
        btnTrasera.setSelected(false);
}

    public void Atras(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Menu/Menu.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
             stage.setMaximized(true);
            stage.show();
            Stage cerrar = (Stage) btnAtras.getScene().getWindow();
            cerrar.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private  void verDelantera(){

        try {
            if (estiloSeleccionado != null){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Operacion/Delantera/Delantera.fxml"));
                Parent parent = fxmlLoader.load();
                DelanteraCotrolller delanteraCotrolller = fxmlLoader.getController();
                delanteraCotrolller.pasarEstilo(estiloSeleccionado,"Delantera");
                GridPane panelCenter=(GridPane) parent;
                panelCenter.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
               // panelCenter.setPadding(new Insets(5, 5, 5, 5));
                menu.setCenter(panelCenter);
              //  cambiarColor(btnDelantera);
                anterior = "Delantera";
            }else{
                alertDialog.alert("Aviso","Seleccione primero el estilo para poder ver la operaciones de la delantera");
                btnDelantera.setSelected(false);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void AbrirDelantera(ActionEvent actionEvent) {
       if (btnDelantera.isSelected()) {
           verDelantera();
           btnEnsamble.setSelected(false);
           btnTrasera.setSelected(false);
       }
    }

    public void AbrirTrasera(ActionEvent actionEvent) {
       if (btnTrasera.isSelected()) {
           try {
               if (estiloSeleccionado != null) {
                   FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Operacion/Delantera/Delantera.fxml"));
                   Parent parent = fxmlLoader.load();
                   DelanteraCotrolller delanteraCotrolller = fxmlLoader.getController();
                   delanteraCotrolller.pasarEstilo(estiloSeleccionado, "Trasera");
                   GridPane panelCenter=(GridPane) parent;
                   panelCenter.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                 //  panelCenter.setPadding(new Insets(10, 10, 10, 10));
                   menu.setCenter(panelCenter);
                   //  cambiarColor(btnTrasera);
                   anterior = "Trasera";
               } else {
                   alertDialog.alert("Aviso", "Seleccione primero el estilo para poder ver las operaciones de la trasera");
                   btnTrasera.setSelected(false);
               }
           } catch (IOException e) {
               e.printStackTrace();
           }
           btnDelantera.setSelected(false);
           btnEnsamble.setSelected(false);
       }
    }

    public void AbrirEnsamble(ActionEvent actionEvent) {
        if (btnEnsamble.isSelected()) {
            try {
                if (estiloSeleccionado != null) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Operacion/Delantera/Delantera.fxml"));
                    Parent parent = fxmlLoader.load();
                    DelanteraCotrolller delanteraCotrolller = fxmlLoader.getController();
                    delanteraCotrolller.pasarEstilo(estiloSeleccionado, "Ensamble");
                    GridPane panelCenter=(GridPane) parent;
                    panelCenter.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                 //   panelCenter.setPadding(new Insets(10, 10, 10, 10));
                    menu.setCenter(panelCenter);
                    // cambiarColor(btnEnsamble);
                    anterior = "Ensamble";
                } else {
                    alertDialog.alert("Aviso", "Seleccione primero el estilo para poder ver las operaciones del ensamble");
                    btnEnsamble.setSelected(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            btnDelantera.setSelected(false);
            btnTrasera.setSelected(false);
        }
    }
    public  void  cambiarColor(Button bntActual){
        switch (anterior) {
            case "Delantera":
                btnDelantera.setStyle(estiloBoton);

                break;
            case "Trasera":
                btnTrasera.setStyle(estiloBoton);

                break;
            case "Ensamble":
                btnEnsamble.setStyle(estiloBoton);

                break;

        }

        bntActual.setStyle(selectBoton);

    }

}

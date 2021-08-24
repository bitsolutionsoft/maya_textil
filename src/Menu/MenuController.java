package Menu;

import Permiso.DAO.Permiso;
import Permiso.DAO.PermisoData;
import Usuario.DAO.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    public ImageView logoimg;
    public Button btnEmpleado;
    public Button btnOperacion;
    public Button btnPago;
    public Button btnBodega;
    public Button btnInforme;
    public Button btnUsuario;
    public Button btnCorte;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logoimg.setImage(new Image("/Img/logo.png"));
    }



    public void AbrirOperacion(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Operacion/Operacion.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/Img/icon.png"));
             stage.setMaximized(true);
            stage.show();
            Stage cerrar = (Stage) btnOperacion.getScene().getWindow();
            cerrar.hide();
            stage.setOnHiding((event ->{
                cerrar.show();
            }));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void CrearUsuario(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Usuario/Usuario.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/Img/icon.png"));
             stage.setMaximized(true);
            stage.show();

            Stage cerrar = (Stage) btnUsuario.getScene().getWindow();
            cerrar.hide();
            stage.setOnHiding((event ->{
                cerrar.show();
            }));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void AbrirEmpleado(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Empleado/Empleado.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/Img/icon.png"));
            stage.show();
            stage.setMaximized(true);
            Stage cerrar = (Stage) btnOperacion.getScene().getWindow();
            cerrar.hide();
            stage.setOnHiding((event ->{
                cerrar.show();
            }));


        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public  void pasarDatosUsuario(ArrayList<Usuario> usuario){
        if(usuario!=null){
            PermisoData data=new PermisoData();
            ArrayList<Permiso>per=new ArrayList<>(data.viewPermisoActivo(new Permiso(0,usuario.get(0).getCodigo(),0,0),"viewper"));
            if (per.size()>0){
            for (int i=0;i<per.size();i++){
                permisoActivos(per.get(i));
            }
            }else{
                sinPermiso();
            }

        }
    }

    public void sinPermiso(){
        btnCorte.setDisable(true);
        btnEmpleado.setDisable(true);
        btnOperacion.setDisable(true);
        btnPago.setDisable(true);
        btnBodega.setDisable(true);
        btnInforme.setDisable(true);
        btnUsuario.setDisable(true);
    }

public void permisoActivos(Permiso pr){
        switch (pr.getNombre()){
            case "Corte":
                if ( pr.getAcceso()==1){
                    btnCorte.setDisable(false);
                }else{
                    btnCorte.setDisable(true);
                }
                break;
            case "Bodega":
                if (pr.getAcceso()==1){
                    btnBodega.setDisable(false);
                }else{
                    btnBodega.setDisable(true);
                }
                break;
            case "Empleado":
                if ( pr.getAcceso()==1){
                    btnEmpleado.setDisable(false);
                }else{
                    btnEmpleado.setDisable(true);
                }
                break;
            case "Operacion":
                if ( pr.getAcceso()==1){
                    btnOperacion.setDisable(false);
                }else{
                    btnOperacion.setDisable(true);
                }
                break;
            case "Pago":
                if (pr.getAcceso()==1){
                    btnPago.setDisable(false);
                }else{
                    btnPago.setDisable(true);
                }
                break;
            case "Informe":
                if ( pr.getAcceso()==1){
                    btnInforme.setDisable(false);
                }else{
                    btnInforme.setDisable(true);
                }
                break;
            case "Usuario":
                if ( pr.getAcceso()==1){
                    btnUsuario.setDisable(false);
                }else{
                    btnUsuario.setDisable(true);
                }
                break;

        }
    }

    public void AbrirCorte(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Corte/Corte.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/Img/icon.png"));
             stage.setMaximized(true);
            stage.show();
            Stage cerrar = (Stage) btnCorte.getScene().getWindow();
            cerrar.hide();
            stage.setOnHiding((event ->{
                cerrar.show();
            }));

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void AbrirPago(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Pago/Pago.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/Img/icon.png"));
            stage.setMaximized(true);
            stage.show();
            Stage cerrar = (Stage) btnCorte.getScene().getWindow();
            cerrar.hide();
            stage.setOnHiding((event)->{
                cerrar.show();
            });

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void AbrirBodega(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Bodega/Bodega.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/Img/icon.png"));
            stage.show();
            stage.setMaximized(true);
            Stage cerrar = (Stage) btnBodega.getScene().getWindow();
            cerrar.hide();
            stage.setOnHiding((event ->{
                cerrar.show();
            }));


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void AbrirInforme(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Informe/Informe.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/Img/icon.png"));
            stage.setMaximized(true);
            stage.show();
            Stage cerrar = (Stage) btnBodega.getScene().getWindow();
            cerrar.hide();
            stage.setOnHiding((event ->{
                cerrar.show();
            }));

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

/*

*/


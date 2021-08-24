package Usuario;

import ClassAux.Util;
import Login.encriptar;
import Usuario.DAO.DataUsuario;
import Usuario.DAO.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class UsuarioController  implements Initializable {
    public Button btnIngresarNuevo;
    public TextField txtBuscar;
    public ListView listUsuario;
    encriptar encriptar=new encriptar();
    static ObservableList<Usuario> usuario;
    static FilteredList<Usuario> usuariodata;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
initList(listUsuario);
llenarLista();
    }
public  void initList(ListView<Usuario> listView){
        DataUsuario datos=new DataUsuario();
        usuario= FXCollections.observableArrayList(datos.viewUsuario(new Usuario(), "viewall"));
        usuariodata=new FilteredList<>(usuario,s->true);
        listView.setItems(usuariodata);
        listView.setCellFactory(new Callback<ListView<Usuario>, ListCell<Usuario>>() {
            @Override
            public ListCell<Usuario> call(ListView<Usuario> usuarioListView) {
                UsuarioCell usuarioCell=new UsuarioCell();
                return usuarioCell;
            }
        });
}
public void llenarLista(){
        txtBuscar.textProperty().addListener((prop,old,text) ->{
            usuariodata.setPredicate(usuario1 -> {
                if (text ==null || text.isEmpty()){
                    return  true;
                }
                String texto=text.toLowerCase();
                if (usuario1.getNombre().toLowerCase().contains(texto)){
                    return true;
                }
                if (usuario1.getApellido().toLowerCase().contains(texto)){
                    return true;
                }
                if (usuario1.getUsuario().toLowerCase().contains(texto)){
                    return  true;
                }
                return  false;
            });
        });
}
    public void nuevoUsuario(ActionEvent actionEvent) {
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/Usuario/FormUsuario.fxml"));
            Parent parent=loader.load();
            Stage stage=new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            stage.getIcons().add(new Image("/Img/icon.png"));
            stage.setOnHiding((event ->{
                initList(listUsuario);
                listUsuario.refresh();
            }));


        }catch (IOException e){
            e.printStackTrace();

        }
    }

}

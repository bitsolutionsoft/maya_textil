package Usuario;

import ClassAux.AlertDialog;
import ClassAux.EstiloBoton;
import ClassAux.SizeColumnTable;
import ClassAux.Util;
import Login.encriptar;
import Permiso.PermisoController;
import Usuario.DAO.DataUsuario;
import Usuario.DAO.Usuario;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
    public TableView <Usuario> tblUsuario;
    public TableColumn<Usuario,String> cellCodigo;
    public TableColumn<Usuario,String> cellNombre;
    public TableColumn<Usuario,String> cellApellido;
    public TableColumn<Usuario,String> cellUsuario;
    public TableColumn<Usuario,String> cellDPI;
    public TableColumn<Usuario,String> cellOpciones;
    encriptar encriptar=new encriptar();
    static ObservableList<Usuario> usuario;
    static FilteredList<Usuario> usuariodata;
    SizeColumnTable sizeColumnTable=new SizeColumnTable();
    EstiloBoton estiloBoton=new EstiloBoton();
    AlertDialog alertDialog=new AlertDialog();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTabla();
initList();
llenarLista();
    }
    public void initTabla(){
        cellCodigo =new TableColumn<>("Código");
        cellNombre =new TableColumn<>("Nombre");
        cellApellido =new TableColumn<>("Apellido");
        cellUsuario =new TableColumn<>("Usuario");
        cellDPI =new TableColumn<>("Numero de DPI");
        cellOpciones =new TableColumn<>("Opciones");
        cellCodigo.setCellValueFactory(new PropertyValueFactory<Usuario,String>("codigo"));
        cellNombre.setCellValueFactory(new PropertyValueFactory<Usuario,String>("nombre"));
        cellApellido.setCellValueFactory(new PropertyValueFactory<Usuario,String>("apellido"));
        cellUsuario.setCellValueFactory(new PropertyValueFactory<Usuario,String>("usuario"));
        cellDPI.setCellValueFactory(new PropertyValueFactory<Usuario,String>("dpi"));
        Callback<TableColumn<Usuario,String>, TableCell<Usuario,String>> cellFactory=(TableColumn<Usuario,String> param)->{
            final TableCell<Usuario,String> cell=new TableCell<>(){
                @Override
                public  void updateItem(String item, boolean empty){
                    if (empty){
                        setGraphic(null);
                    }else{
                        ImageView editButton=new ImageView(estiloBoton.editImg());
                        editButton.setFitHeight(estiloBoton.sizeButton());
                        editButton.setFitWidth(estiloBoton.sizeButton());
                        editButton.setStyle(estiloBoton.Boton());

                        ImageView deleteButton=new ImageView(estiloBoton.deleteImg());
                        editButton.setFitHeight(estiloBoton.sizeButton());
                        editButton.setFitWidth(estiloBoton.sizeButton());
                        editButton.setStyle(estiloBoton.Boton());

                        ImageView usuarioButton=new ImageView(estiloBoton.accessImg());
                        usuarioButton.setFitHeight(estiloBoton.sizeButton());
                        usuarioButton.setFitWidth(estiloBoton.sizeButton());
                        usuarioButton.setStyle(estiloBoton.Boton());

                        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(javafx.scene.input.MouseEvent event) {
                                Usuario usuario=tblUsuario.getSelectionModel().getSelectedItem();
                                EliminarUsuario(usuario);
                            }
                        });

                        editButton.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
                            @Override
                            public void handle(javafx.scene.input.MouseEvent event) {
                                Usuario usuario=tblUsuario.getSelectionModel().getSelectedItem();
                                EditarUsuario(usuario);
                            }
                        });
                        usuarioButton.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
                            @Override
                            public void handle(javafx.scene.input.MouseEvent event) {
                                Usuario usuario=tblUsuario.getSelectionModel().getSelectedItem();
                                EditarPermiso(usuario);
                            }
                        });

                        HBox containBoton=new HBox(deleteButton,editButton,usuarioButton);
                        containBoton.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteButton,new Insets(2,10,2,2));
                        HBox.setMargin(editButton,new Insets(2,10,2,10));
                        HBox.setMargin(usuarioButton,new Insets(2,10,2,2));
                        setGraphic(containBoton);
                    }
                    setText(null);
                }
            };
            return  cell;
        };
        cellOpciones.setCellFactory(cellFactory);
        Platform.runLater(()-> sizeColumnTable.ajustarColumna(tblUsuario));
        tblUsuario.getColumns().addAll(cellCodigo,cellNombre,cellApellido,cellDPI, cellUsuario,cellOpciones);
    }

public  void initList(){
        DataUsuario datos=new DataUsuario();
        usuario= FXCollections.observableArrayList(datos.viewUsuario(new Usuario(), "viewall"));
        usuariodata=new FilteredList<>(usuario,s->true);
        tblUsuario.setItems(usuariodata);
        /*
        listView.setItems(usuariodata);
        listView.setCellFactory(new Callback<ListView<Usuario>, ListCell<Usuario>>() {
            @Override
            public ListCell<Usuario> call(ListView<Usuario> usuarioListView) {
                UsuarioCell usuarioCell=new UsuarioCell();
                return usuarioCell;
            }
        });*/
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
                initList();
                tblUsuario.refresh();
            }));


        }catch (IOException e){
            e.printStackTrace();

        }
    }

    public void EliminarUsuario(Usuario usuario){
        if (alertDialog.alertConfirm("Maya Textil","Esta seguro de eliminar este usuario")){
            DataUsuario dataUsuario=new DataUsuario();
            dataUsuario.crudUsuario(usuario,"delete");
            initList();
            tblUsuario.refresh();
        }
    }
    public void EditarUsuario(Usuario usuario){
        try {
            FXMLLoader loader1=new FXMLLoader(getClass().getResource("/Usuario/FormUsuario.fxml"));
            Parent parent=loader1.load();
            Stage stage=new Stage();
            stage.setTitle("Maya textil");
            stage.getIcons().add(new Image("/Img/icon.png"));
            stage.setScene(new Scene(parent));
            FormUsuario formUsuario=loader1.getController();
            formUsuario.pasarUsuario(usuario);
            stage.show();
            stage.setOnHiding((windowEvent ->{
               initList();
                tblUsuario.refresh();
            }));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void EditarPermiso(Usuario usuario){
        try {
            FXMLLoader loader1=new FXMLLoader(getClass().getResource("/Permiso/Permiso.fxml"));
            Parent parent=loader1.load();
            Stage stage=new Stage();
            stage.setTitle("Maya textil");
            stage.getIcons().add(new Image("/Img/icon.png"));
            stage.setScene(new Scene(parent));
            PermisoController permisoController=loader1.getController();
            permisoController.pasarUsuario(usuario);
            stage.show();
            stage.setOnHiding((windowEvent ->{
                initList();
                tblUsuario.refresh();
            }));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

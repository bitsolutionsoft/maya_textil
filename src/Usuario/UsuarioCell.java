package Usuario;

import ClassAux.AlertDialog;
import Permiso.PermisoController;
import Usuario.DAO.DataUsuario;
import Usuario.DAO.Usuario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class UsuarioCell extends ListCell<Usuario> {
    AlertDialog alertDialog=new AlertDialog();
    private Node graphic;
    private  RowUsuario rowUsuario;

    public UsuarioCell(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Usuario/RowUsuario.fxml"));
        try {
            graphic=loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        rowUsuario=loader.getController();

        rowUsuario.btnEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
             if (alertDialog.alertConfirm("Maya Textil","Esta seguro de eliminar este usuario")){
                 Usuario user=new Usuario(Integer.parseInt(rowUsuario.codigo.getText()),"","","","","");
                 DataUsuario dataUsuario=new DataUsuario();
                 dataUsuario.crudUsuario(user,"delete");
                 UsuarioController usuarioController=new UsuarioController();
                 usuarioController.initList(getListView());
                 getListView().refresh();
             }
            }
        });
        rowUsuario.btnEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Usuario usuario=new Usuario();
                for (int i=0;i<getListView().getItems().size();i++){
                    if (Integer.parseInt(rowUsuario.codigo.getText())==getListView().getItems().get(i).getCodigo()){
                        usuario.setCodigo(getListView().getItems().get(i).getCodigo());
                        usuario.setNombre(getListView().getItems().get(i).getNombre());
                        usuario.setApellido(getListView().getItems().get(i).getApellido());
                        usuario.setUsuario(getListView().getItems().get(i).getUsuario());
                        usuario.setDpi(getListView().getItems().get(i).getDpi());
                    }
                }
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
                        UsuarioController usuarioController=new UsuarioController();
                        usuarioController.initList(getListView());
                        getListView().refresh();
                    }));
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        rowUsuario.btnUsuario.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Usuario usuario=new Usuario();
                for (int i=0;i<getListView().getItems().size();i++){
                    if (Integer.parseInt(rowUsuario.codigo.getText())==getListView().getItems().get(i).getCodigo()){
                        usuario.setCodigo(getListView().getItems().get(i).getCodigo());
                        usuario.setNombre(getListView().getItems().get(i).getNombre());
                        usuario.setApellido(getListView().getItems().get(i).getApellido());
                        usuario.setUsuario(getListView().getItems().get(i).getUsuario());
                        usuario.setDpi(getListView().getItems().get(i).getDpi());
                    }
                }
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
                        UsuarioController usuarioController=new UsuarioController();
                        usuarioController.initList(getListView());
                        getListView().refresh();
                    }));
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    protected void updateItem(Usuario usuario, boolean empty){
        super.updateItem(usuario,empty);
        if (empty){
            clearContent();
        }else{
            addContent(usuario);
        }
    }
    private void clearContent(){setGraphic(null);}
    private void addContent(Usuario usuario){
        setText(null);
        rowUsuario.setCodigo(String.valueOf(usuario.getCodigo()));
        rowUsuario.setNombre(usuario.getNombre());
        rowUsuario.setApellido(usuario.getApellido());
        rowUsuario.setUsuario(usuario.getUsuario());
        rowUsuario.setDpi(usuario.getDpi());
         setGraphic(graphic);
    }

}

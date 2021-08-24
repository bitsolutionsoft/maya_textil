package Operacion.Estilo;

import ClassAux.AlertDialog;
import Operacion.Estilo.DAO.Estilo;
import Operacion.Estilo.DAO.EstiloData;
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

public class CellEstilo extends ListCell<Estilo> {
    AlertDialog alertDialog=new AlertDialog();
    private Node graphic;
    private RowEstilo rowEstilo;
    CellEstilo(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Operacion/Estilo/RowEstilo.fxml"));
        try {
            graphic=loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        rowEstilo=loader.getController();
        rowEstilo.btnEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (alertDialog.alertConfirm("Maya Textil","Esta seguro de eliminar este estilo")){
                    Estilo estilo=new Estilo(Integer.parseInt(rowEstilo.codigo.getText()),"","");
                    EstiloData estiloData=new EstiloData();
                    estiloData.crudEStilo(estilo,"delete");
                    EstiloController estiloController=new EstiloController();
                    estiloController.iniciarLista(getListView());
                    getListView().refresh();
                }
            }

        });
        rowEstilo.btnEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            Estilo estilo=new Estilo();
            for (int i=0;i<getListView().getItems().size();i++){
                if (Integer.parseInt(rowEstilo.codigo.getText())==getListView().getItems().get(i).getCodigo()){
                    estilo.setCodigo(getListView().getItems().get(i).getCodigo());
                    estilo.setNombre(getListView().getItems().get(i).getNombre());
                    estilo.setEstado(getListView().getItems().get(i).getEstado());

                }
            }
            try {
                FXMLLoader loader1=new FXMLLoader(getClass().getResource("/Operacion/Estilo/FormEstilo.fxml"));
                Parent parent=loader1.load();
                Stage stage=new Stage();
                stage.setTitle("Maya textil");
                stage.getIcons().add(new Image("/Img/icon.png"));
                stage.setScene(new Scene(parent));
                FormEstilo formEstilo=loader1.getController();
                formEstilo.pasarEStilo(estilo);
                stage.show();
                stage.setOnHiding((windowEvent ->{
                    EstiloController estiloController=new EstiloController();
                    estiloController.iniciarLista(getListView());
                    getListView().refresh();
                }));
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        });

    }
    @Override
    protected  void updateItem(Estilo estilo,boolean empty){
        super.updateItem(estilo,empty);
        if (empty){
            clearContent();
        }else{
            addContent(estilo);
        }
    }
    public void clearContent(){setGraphic(null);}
    public  void addContent(Estilo estilo){
        setText(null);
        rowEstilo.setCodigo(String.valueOf(estilo.getCodigo()));
        rowEstilo.setNombre(estilo.getNombre());
        rowEstilo.setEstado(estilo.getEstado());
        setGraphic(graphic);
    }
}

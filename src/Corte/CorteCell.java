package Corte;

import ClassAux.AlertDialog;
import Corte.DAO.Corte;
import Corte.DAO.DataCorte;
import Empleado.DAO.DataEmpleado;
import Empleado.DAO.Empleado;
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

public class CorteCell extends ListCell<Corte> {
    AlertDialog alertDialog=new AlertDialog();
    private Node graphic;
    private RowCorte rowCorte;
    // el constructor donde llamamos el el rowproducto
    public CorteCell(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Corte/RowCorte.fxml"));
        try {
            graphic=loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        rowCorte=loader.getController();
        //el evento del botono eliminar para eliminar productos
        rowCorte.btnEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (alertDialog.alertConfirm("Corte", "¿Esta seguro de desactivar este corte?")){
                    Corte pro=new Corte("x",0,0,"2021-07-31",0,"x");
                    DataCorte datos=new DataCorte();
                    datos.crudCorte(pro,"delete");
                    CorteController corteController=new CorteController();
                    corteController.initLista(getListView());
                    getListView().refresh();

                }
            }
        });
        //aquii dejas los eventos del boton donde los tenes te dar problema por el costructor
        //evento del boton modificar _______________________________________________________________________________________//

        rowCorte.btnEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Corte corte = new Corte();
                for (int i = 0; i < getListView().getItems().size(); i++) {
                    if ((rowCorte.corte.getText()) .equals( getListView().getItems().get(i).getIdcorte())) {
                        corte.setIdcorte(getListView().getItems().get(i).getIdcorte());
                        corte.setIdestilo(getListView().getItems().get(i).getIdestilo());
                        corte.setNombre(getListView().getItems().get(i).getNombre());
                        corte.setCantidad(getListView().getItems().get(i).getCantidad());
                        corte.setFecha_corte(getListView().getItems().get(i).getFecha_corte());
                        corte.setCant_rollo(getListView().getItems().get(i).getCant_rollo());
                        corte.setEstado(getListView().getItems().get(i).getEstado());


                    }
                }
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Corte/FormCorte.fxml"));
                    Parent parent = loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Modificar corte");
                    stage.getIcons().add(new Image("/Img/icon.png"));
                    stage.setScene(new Scene(parent));
                    FormCorte formCorte = loader.<FormCorte>getController();
                    formCorte.pasarRegistro(corte);
                    stage.show();
                    stage.setOnHiding((event -> {
                        CorteController corteController = new CorteController();
                        corteController.initLista(getListView());
                        getListView().refresh();
                    }));

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }

        });
        //final del evento modificar


    }
    //aqui llenas la lista con  el rowProducto
    @Override
    protected void updateItem(Corte corte, boolean empty){
        super.updateItem(corte, empty);
        if (empty){
            clearContent();
        }else{
            addContent(corte);
            //  rowProducto.pasar(getItem());
        }
    }


    //para limpiar contenido
    private void clearContent(){
        setGraphic(null);
    }
    //agregamos contenido a cada label creado en el rowproducto
    private void addContent(Corte corte){
        setText(null);
        //  rowProducto.setAncho(getListView().getWidth()-16);
        rowCorte.setCorte(corte.getIdcorte());
        rowCorte.setEstilo(corte.getIdestilo());
        rowCorte.setCantidad(corte.getCantidad());
        rowCorte.setNombreestilo(corte.getNombre());
        rowCorte.setFecha(corte.getFecha_corte());
        rowCorte.setCant_rollo(corte.getCant_rollo());
        rowCorte.setEstado(corte.getEstado());

        setGraphic(graphic);
    }



}

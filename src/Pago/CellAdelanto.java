package Pago;

import ClassAux.AlertDialog;
import Empleado.DAO.DataEmpleado;
import Empleado.DAO.Empleado;
import Empleado.RowEmpleado;
import Pago.DAO.Adelanto;
import Pago.DAO.DataAdelanto;
import Pago.DAO.Pago;
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

public class CellAdelanto extends ListCell<Adelanto> {

    AlertDialog alertDialog=new AlertDialog();
    private Node graphic;
    private RowAdelanto rowAdelanto;
    // el constructor donde llamamos el el rowproducto
    public CellAdelanto(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Pago/RowAdelanto.fxml"));
        try {
            graphic=loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        rowAdelanto=loader.getController();
        //el evento del botono eliminar para eliminar productos
        rowAdelanto.btnEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (alertDialog.alertConfirm("Adelanto", "esta seguro de elliminar adelanto")){
                    Adelanto pro=new Adelanto(Integer.parseInt(rowAdelanto.idadelanto.getText()),0,0,"x","x");
                    DataAdelanto datos=new DataAdelanto();
                    datos.crudAdelanto(pro,"delete");
                    PagoController pagoController=new PagoController();
                    Empleado emp=new Empleado(Integer.parseInt(rowAdelanto.idempleado.getText()),"","","",0,"");
                    pagoController.llenarAdelanto(getListView(),emp);
                    getListView().refresh();

                }
            }
        });
        //aquii dejas los eventos del boton donde los tenes te dar problema por el costructor
        //evento del boton modificar _______________________________________________________________________________________//

        rowAdelanto.btnEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Adelanto adelanto = new Adelanto();
                for (int i = 0; i < getListView().getItems().size(); i++) {
                    if (Integer.parseInt(rowAdelanto.idadelanto.getText()) == getListView().getItems().get(i).getIdadelanto()) {
                        adelanto.setIdadelanto(getListView().getItems().get(i).getIdadelanto());
                        adelanto.setIdempleado(getListView().getItems().get(i).getIdempleado());
                        adelanto.setCantidad(getListView().getItems().get(i).getCantidad());
                        adelanto.setConcepto(getListView().getItems().get(i).getConcepto());

                        adelanto.setEstado(getListView().getItems().get(i).getEstado());


                    }
                }
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Pago/FormAdelanto.fxml"));
                    Parent parent = loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Modificar Adelanto");
                    stage.getIcons().add(new Image("/Img/icon.png"));
                    stage.setScene(new Scene(parent));
                    FormAdelanto formAdelanto = loader.<FormAdelanto>getController();
                    formAdelanto.pasarRegistro2(adelanto);
                    stage.show();
                    stage.setOnHiding((event -> {
                        PagoController pagoController = new PagoController();
                        Empleado emp=new Empleado((adelanto.getIdempleado()),"","","",0,"");

                        pagoController.llenarAdelanto(getListView(),emp);
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
    protected void updateItem(Adelanto adelanto, boolean empty){
        super.updateItem(adelanto, empty);
        if (empty){
            clearContent();
        }else{
            addContent(adelanto);
            //  rowProducto.pasar(getItem());
        }
    }


    //para limpiar contenido
    private void clearContent(){
        setGraphic(null);
    }
    //agregamos contenido a cada label creado en el rowproducto
    private void addContent(Adelanto adelanto){
        setText(null);
        //  rowProducto.setAncho(getListView().getWidth()-16);
        rowAdelanto.setIdadelanto(adelanto.getIdadelanto());
        rowAdelanto.setIdempleado(adelanto.getIdempleado());
                rowAdelanto.setCantidad(String.valueOf(adelanto.getCantidad()));
        rowAdelanto.setConcepto(adelanto.getConcepto());
        rowAdelanto.setFecha(adelanto.getFecha());
        rowAdelanto.setEstado(adelanto.getEstado());

        setGraphic(graphic);
    }

}

package Pago;

import ClassAux.AlertDialog;
import Empleado.DAO.Empleado;
import Pago.DAO.Adelanto;
import Pago.DAO.DataAdelanto;
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

public class CellACancelado extends ListCell<Adelanto> {
    AlertDialog alertDialog=new AlertDialog();
    private Node graphic;
    private RowACancelado rowACancelado;
    // el constructor donde llamamos el el rowproducto
    public CellACancelado(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Pago/RowACancelado.fxml"));
        try {
            graphic=loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        rowACancelado=loader.getController();
        //el evento del botono eliminar para eliminar productos
        rowACancelado.btnEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (alertDialog.alertConfirm("Adelanto", "esta seguro de elliminar adelanto")){
                    Adelanto pro=new Adelanto(Integer.parseInt(rowACancelado.idadelanto.getText()),0,0,"x","x");
                    DataAdelanto datos=new DataAdelanto();
                    datos.crudAdelanto(pro,"delete");
                    PagoController pagoController=new PagoController();
                    Empleado emp=new Empleado(Integer.parseInt(rowACancelado.idempleado.getText()),"","","",0,"");
                    pagoController.llenarAdelanto(getListView(),emp);
                    getListView().refresh();

                }
            }
        });
        //aquii dejas los eventos del boton donde los tenes te dar problema por el costructor
        //evento del boton modificar _______________________________________________________________________________________//

        rowACancelado.btnEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Adelanto adelanto = new Adelanto();
                for (int i = 0; i < getListView().getItems().size(); i++) {
                    if (Integer.parseInt(rowACancelado.idadelanto.getText()) == getListView().getItems().get(i).getIdadelanto()) {
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
                    Empleado emp1=new Empleado(Integer.parseInt(rowACancelado.idempleado.getText()),"","","",0,"");
                    FormAdelanto formAdelanto = loader.<FormAdelanto>getController();
                    formAdelanto.pasarRegistro2(emp1,adelanto);
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
        rowACancelado.setIdadelanto(adelanto.getIdadelanto());
        rowACancelado.setIdempleado(adelanto.getIdempleado());
        rowACancelado.setCantidad(String.valueOf(adelanto.getCantidad()));
        rowACancelado.setConcepto(adelanto.getConcepto());
        rowACancelado.setFecha(adelanto.getFecha());
        rowACancelado.setEstado(adelanto.getEstado());

        setGraphic(graphic);
    }


}

package Operacion.Delantera;

import ClassAux.AlertDialog;
import Empleado.ControllerEmpleado;
import Empleado.DAO.Empleado;
import Empleado.FormEmpleado;
import Operacion.FormOperacion;
import Operacion.DAO.DataOperacion;
import Operacion.DAO.Operacion;
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

public class CellDelantera extends ListCell<Operacion> {
    AlertDialog alertDialog=new AlertDialog();
    private Node graphic;
    private RowDelantera rowDelantera;
    public  CellDelantera(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Operacion/Delantera/RowDelantera.fxml"));
        try{
            graphic=loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        rowDelantera=loader.getController();
        rowDelantera.btnEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (alertDialog.alertConfirm("Operación","Esta seguro de descativar esta operación")){
                    DataOperacion dataOperacion=new DataOperacion();
                    dataOperacion.crudOperacion(new Operacion(Integer.parseInt(rowDelantera.codigo.getText()),0,0,"",0,0),"delete");
                    DelanteraCotrolller delanteraCotrolller=new DelanteraCotrolller();
                    delanteraCotrolller.initLista(getListView());
                    getListView().refresh();
                }
            }
        });
        rowDelantera.btnEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Operacion operacion=new Operacion();
                for (int i = 0; i < getListView().getItems().size(); i++) {
                    if (Integer.parseInt(rowDelantera.codigo.getText()) == getListView().getItems().get(i).getCodigo()) {
                        operacion.setCodigo(getListView().getItems().get(i).getCodigo());
                        operacion.setCodigoEstilo(getListView().getItems().get(i).getCodigoEstilo());
                        operacion.setCodigoTipo(getListView().getItems().get(i).getCodigoTipo());
                        operacion.setNombre(getListView().getItems().get(i).getNombre());
                        operacion.setPrecio(getListView().getItems().get(i).getPrecio());
                        operacion.setVariacion(getListView().getItems().get(i).getVariacion());


                    }
                }
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Operacion/FormOperacion.fxml"));
                    Parent parent = loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Maya-Textil");
                    stage.getIcons().add(new Image("/Img/icon.png"));
                    stage.setScene(new Scene(parent));
                    FormOperacion formOperacion = loader.getController();
                    formOperacion.pasarDatos(operacion,"update",returnTipo(operacion.getCodigoTipo()), operacion.getCodigoTipo(),operacion.getCodigoEstilo());
                    stage.show();
                    stage.setOnHiding((event -> {
                        DelanteraCotrolller cotrolller = new DelanteraCotrolller();
                        cotrolller.codigo_estilo=operacion.getCodigoEstilo();
                        cotrolller.codigo_tipo=operacion.getCodigoTipo();
                        cotrolller.initLista(getListView());
                        getListView().refresh();
                    }));

                } catch (IOException e) {
                    e.printStackTrace();

                }

            }
        });

    }


    @Override
    protected void updateItem(Operacion operacion, boolean empty){
        super.updateItem(operacion, empty);
        if (empty){
            clearContent();
        }else{
            addContent(operacion);

        }
    }


    //para limpiar contenido
    private void clearContent(){
        setGraphic(null);
    }
    //agregamos contenido a cada label creado en el rowproducto
    private void addContent(Operacion operacion){
        setText(null);
        //rowDelantera.setAncho(getListView().getWidth());
        rowDelantera.setCodigo(String.valueOf(operacion.getCodigo()));
        rowDelantera.setNombre(operacion.getNombre());
        rowDelantera.setPrecio(String.valueOf(operacion.getPrecio()));
        rowDelantera.setVariacion(String.valueOf(operacion.getVariacion()));//este
        setGraphic(graphic);
    }
    private String returnTipo(int tipo){
        String nombre="";
        switch (tipo){
            case 1:
               nombre="Delantera";
                break;
            case 2:
                nombre="Trasera";
                break;
            case 3:
                nombre="Ensamble";
                break;
            case 4:
                nombre="Extras";
                break;

        }

        return nombre;
    }

}

package Pago.ListCorte;

import ClassAux.AlertDialog;
import ClassAux.SetBotonIcon;
import ClassAux.SizeColumnTable;
import Corte.DAO.Corte;
import Corte.DAO.DataCorte;
import Corte.FormCorte;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListCorte implements Initializable {
    public Button btnIngresarNuevo;
    public ListView<Corte> listCorte;
    public TextField txtBuscar;
    static ObservableList<Corte> cortes;
    static FilteredList<Corte> cortedata;
    public TableView<Corte> tblCorte;
    public TableColumn<Corte, String> cellCodigo;
    public TableColumn<Corte, String> cellEstilo;
    public TableColumn<Corte, String> cellFecha;
    public TableColumn<Corte, String> cellRollos;
    public TableColumn<Corte, String> cellCantidad;
    public TableColumn<Corte, String> cellEstado;
    public TableColumn<Corte, String> cellOpciones;

    SetBotonIcon setBotonIcon=new SetBotonIcon();
    SizeColumnTable sizeColumnTable=new SizeColumnTable();
    AlertDialog alertDialog=new AlertDialog();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTablaCorte();
        initLista();
        llenarListaCorte();
        /*
        listCorte.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount()==1 && event.getButton()== MouseButton.PRIMARY){
                    Corte corte=listCorte.getSelectionModel().getSelectedItem();
                    initListaTela(listViewTela,corte);
                    listViewTela.refresh();
                }
            }
        });*/

    }

    public void initTablaCorte() {
        cellCodigo = new TableColumn<>("Código");
        cellEstilo = new TableColumn<>("Estilo");
        cellFecha = new TableColumn<>("Fecha Corte");
        cellCantidad = new TableColumn<>("Cantidad");
        cellRollos = new TableColumn<>("C/Rollos");
        cellEstado = new TableColumn<>("Estado");
        cellOpciones = new TableColumn<>("Opciones");

        cellCodigo.setCellValueFactory(new PropertyValueFactory<Corte, String>("idcorte"));
        cellEstilo.setCellValueFactory(new PropertyValueFactory<Corte, String>("nombre"));
        cellFecha.setCellValueFactory(new PropertyValueFactory<Corte, String>("fecha_corte"));
        cellCantidad.setCellValueFactory(new PropertyValueFactory<Corte, String>("cantidad"));
        cellRollos.setCellValueFactory(new PropertyValueFactory<Corte, String>("cant_rollo"));
        cellEstado.setCellValueFactory(new PropertyValueFactory<Corte, String>("estado"));

        Callback<TableColumn<Corte, String>, TableCell<Corte, String>> cellFactory = (TableColumn<Corte, String> param) -> {
            final TableCell<Corte, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                    } else {

                        Button editButton=new Button();
                        editButton.setGraphic(setBotonIcon.ImgUpdate());
                        editButton.setStyle(setBotonIcon.ButtonStyle());
                        Button deleteButton=new Button();
                        deleteButton.setGraphic(setBotonIcon.ImgDelete());
                        deleteButton.setStyle(setBotonIcon.ButtonStyle());

                        editButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Corte corte=getTableView().getItems().get(getIndex());
                                EditarCorte(corte);
                            }
                        });
                        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Corte corte=getTableView().getItems().get(getIndex());
                                EliminarCorte(corte);
                            }
                        });

                        HBox containButton = new HBox(deleteButton, editButton);
                        containButton.setStyle(setBotonIcon.HboxStyle());
                        HBox.setMargin(deleteButton, new Insets(2, 10, 2, 2));
                        HBox.setMargin(editButton, new Insets(2, 2, 2, 10));
                        setGraphic(containButton);
                    }
                    setText(null);
                }

                ;
            };
            return cell;
        };
        cellOpciones.setCellFactory(cellFactory);
        tblCorte.setEditable(true);
        tblCorte.getColumns().addAll(cellCodigo, cellEstilo, cellFecha, cellCantidad, cellRollos, cellEstado, cellOpciones);
        Platform.runLater(() -> sizeColumnTable.ajustarColumna(tblCorte));

        cellEstado.setCellFactory(new Callback<TableColumn<Corte, String>, TableCell<Corte, String>>() {
            @Override
            public TableCell<Corte, String> call(TableColumn<Corte, String> tableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            if (item.equals("Activo")) {
                                setStyle(setBotonIcon.Activo());
                            } else {
                                setStyle(setBotonIcon.NoActivo());
                            }
                            setText(item);
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
    }
    public void initLista(){
        DataCorte datos=new DataCorte();
        cortes = FXCollections.observableArrayList(datos.viewCorte("viewall"));
        cortedata=new FilteredList<Corte>(cortes,s->true);
        tblCorte.setItems(cortedata);
    }

    public void llenarListaCorte(){

        //capturar el texto y filtrar
        txtBuscar.textProperty().addListener((prop,old,text) ->{
            cortedata.setPredicate(corte ->{
                if (text==null || text.isEmpty()){
                    return  true;
                }
                String texto=text.toLowerCase();
                if(String.valueOf(corte.getIdcorte()).toLowerCase().contains(texto)){
                    return true;
                }

                return false;
            });
        });
    }






    public void nuevoCorte(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/Corte/FormCorte.fxml"));
            Stage stage=new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            stage.getIcons().add(new Image("/img/icon.png"));
            stage.setOnHiding((event ->{
                initLista();
                tblCorte.refresh();
            }));


        }catch (IOException e){
            e.printStackTrace();

        }

    }
    public void EditarCorte(Corte corte){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Corte/FormCorte.fxml"));
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Modificar corte");
            stage.getIcons().add(new Image("/img/icon.png"));
            stage.setScene(new Scene(parent));
            FormCorte formCorte = loader.<FormCorte>getController();
            formCorte.pasarRegistro(corte);
            stage.show();
            stage.setOnHiding((event -> {

                initLista();
                tblCorte.refresh();
            }));

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    public void EliminarCorte(Corte corte){
        if (alertDialog.alertConfirm("Corte", "¿Esta seguro de desactivar este corte?")){
            Corte pro=new Corte("x",0,0,"2021-07-31",0,"x");
            DataCorte datos=new DataCorte();
            datos.crudCorte(pro,"delete");
            initLista();
            tblCorte.refresh();

        }
    }
}

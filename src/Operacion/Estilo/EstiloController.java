package Operacion.Estilo;


import ClassAux.AlertDialog;
import ClassAux.EstiloBoton;
import ClassAux.SizeColumnTable;
import Operacion.Estilo.DAO.Estilo;
import Operacion.Estilo.DAO.EstiloData;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;


public class EstiloController  implements Initializable {
    public Label lblTitulo;
    public Button btnNuevo;

    public TextField txtBuscar;
  public static   ObservableList<Estilo> listEstilo;
    public static FilteredList<Estilo> filtrarEstilo;
    public TableView <Estilo> tblEstilo;
    public TableColumn<Estilo,String> cellCodigo;
    public TableColumn<Estilo,String> cellNombre;
    public TableColumn<Estilo,String> cellEstado;
    public TableColumn<Estilo,String> cellOpciones;
    SizeColumnTable sizeColumnTable=new SizeColumnTable();
    AlertDialog alertDialog=new AlertDialog();
    EstiloBoton estiloBoton=new EstiloBoton();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//iniciarLista(listViewEstilo);
//llenarLista();
iniciarTabla();
llenarTabla();
    }

    private void iniciarTabla(){
        //  tblEstilo.setEditable(true);
        cellCodigo=new TableColumn<>("Codigo");
        cellNombre=new TableColumn<>("Nombre");
        cellEstado=new TableColumn<>("Estado");
        cellOpciones=new TableColumn<>("Opciones");

        cellCodigo.setCellValueFactory(new PropertyValueFactory<Estilo,String>("codigo"));
        cellNombre.setCellValueFactory(new PropertyValueFactory<Estilo,String>("nombre"));
        cellEstado.setCellValueFactory(new PropertyValueFactory<Estilo,String>("estado"));


        Callback<TableColumn<Estilo,String>,TableCell<Estilo,String>> cellFactory=(TableColumn<Estilo,String> param)->{
            final TableCell<Estilo, String> cell=new TableCell<>(){
                @Override
                public void updateItem(String item, boolean empty){
                    if(empty){
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

                        deleteButton.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
                            @Override
                            public void handle(javafx.scene.input.MouseEvent event) {
                                Estilo estilo=tblEstilo.getSelectionModel().getSelectedItem();
                                EliminarEstilo(estilo);
                            }
                        });

                       editButton.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
                           @Override
                           public void handle(javafx.scene.input.MouseEvent event) {
                               Estilo estilo=tblEstilo.getSelectionModel().getSelectedItem();
                               EditarEstilo(estilo);
                           }
                       });

                        HBox containBoton=new HBox(deleteButton,editButton);
                        containBoton.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteButton,new Insets(2,10,2,2));
                        HBox.setMargin(editButton,new Insets(2,2,2,10));
                        setGraphic(containBoton);
                    }
                    setText(null);
                }
            };
            return cell;
        };
        cellOpciones.setCellFactory(cellFactory);
        Platform.runLater(()-> sizeColumnTable.ajustarColumna(tblEstilo));
        tblEstilo.getColumns().setAll(cellCodigo,cellNombre,cellEstado,cellOpciones);
    }
    private void llenarTabla(){
        EstiloData estiloData=new EstiloData();
        listEstilo= FXCollections.observableArrayList(estiloData.viewEstilo(new Estilo(0,"",""),"viewall"));
        filtrarEstilo=new FilteredList<>(listEstilo, p ->true);
        SortedList<Estilo> datosOrdenados=new SortedList<>(filtrarEstilo);
        datosOrdenados.comparatorProperty().bind(tblEstilo.comparatorProperty());
        tblEstilo.setItems(datosOrdenados);

        txtBuscar.textProperty().addListener((prop, old, text) -> {
            filtrarEstilo.setPredicate(estilo -> {
                if (text ==null ||  text.isEmpty()){
                    return true;
                }
                String texto=text.toLowerCase();
                if (String.valueOf(estilo.getCodigo()).toLowerCase().contains(texto)){
                    return  true;
                }else if (estilo.getNombre().toLowerCase().contains(texto)){
                    return true;
                }
                return false;
            });
        });
    }

    public void iniciarLista(ListView<Estilo> listView){
        EstiloData estiloData=new EstiloData();
        listEstilo=FXCollections.observableArrayList(estiloData.viewEstilo(new Estilo(0,"",""),"viewall"));
        filtrarEstilo=new FilteredList<>(listEstilo,s ->true );
        listView.setItems(filtrarEstilo);
     /*   listView.setCellFactory(new Callback<ListView<Estilo>, ListCell<Estilo>>() {
            @Override
            public ListCell<Estilo> call(ListView<Estilo> listView) {
                CellEstilo cellEstilo=new CellEstilo();
                return  cellEstilo;
            }
        });*/
    }
    public void llenarLista(){
        txtBuscar.textProperty().addListener((prop,old,text) ->{
            filtrarEstilo.setPredicate(estilo ->{
                if (text==null || text.isEmpty()){
                    return  true;
                }
                String texto=text.toLowerCase();
                if(String.valueOf(estilo.getCodigo()).toLowerCase().contains(texto)){
                    return true;
                }
                else if(estilo.getNombre().toLowerCase().contains(texto)){
                    return true;
                }
                return false;
            });
        });
    }




    public void IngresarNuevo(ActionEvent actionEvent) {
        try{
          FXMLLoader loader=new FXMLLoader(getClass().getResource("/Operacion/Estilo/FormEstilo.fxml"));
          Parent parent=loader.load();
          Stage stage=new Stage();
          stage.setScene(new Scene(parent));
          stage.setTitle("Maya_textil");
          stage.setOnHiding((event ->{
             //iniciarLista(listViewEstilo);
             llenarTabla();
             tblEstilo.refresh();
          }));
          stage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public  void EliminarEstilo(Estilo estilo){
        if (alertDialog.alertConfirm("Maya Textil","Esta seguro de eliminar este estilo")){
            EstiloData estiloData=new EstiloData();
            estiloData.crudEStilo(estilo,"delete");
            llenarTabla();
            tblEstilo.refresh();
        }
    }
    public void EditarEstilo(Estilo estilo){
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
               llenarTabla();
                tblEstilo.refresh();
            }));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

/*
* */
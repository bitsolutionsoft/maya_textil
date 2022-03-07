package Operacion.Delantera;

import ClassAux.AlertDialog;
import ClassAux.SetBotonIcon;
import ClassAux.SizeColumnTable;
import Operacion.DAO.DataOperacion;
import Operacion.DAO.Operacion;
import Operacion.FormOperacion;
import Operacion.Estilo.DAO.Estilo;
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




public class DelanteraCotrolller implements Initializable {
    public TextField txtBuscar;
    public Button btnNuevo;

    private final int sizeIcon =20;
    public Label lblTitulo;

    public TableView<Operacion> tblDelanteras;
    public TableColumn<Operacion,String> cellNombre;
    public TableColumn<Operacion,String> cellPrecio;
    public TableColumn<Operacion,String> cellVariacion;
    public TableColumn<Operacion,String> cellOpciones;

    SizeColumnTable sizeColumnTable=new SizeColumnTable();
    SetBotonIcon setBotonIcon=new SetBotonIcon();
    AlertDialog alertDialog = new AlertDialog();
    ObservableList<Operacion> listDelantera;
    FilteredList<Operacion> filterDelantera;
    private String tipo_operacion="Delantera";

public   int codigo_estilo=0,codigo_tipo=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTabla();
        initLista();
        llenarLista();

    }
    public  void initTabla(){
        cellNombre=new TableColumn<>("Operación");
        cellPrecio=new TableColumn<>("Precio");
        cellVariacion=new TableColumn<>("Precio de varación");
        cellOpciones=new TableColumn<>("Opciones");
        cellNombre.setCellValueFactory(new PropertyValueFactory<Operacion,String>("nombre"));
        cellPrecio.setCellValueFactory(new PropertyValueFactory<Operacion,String>("precio"));
        cellVariacion.setCellValueFactory(new PropertyValueFactory<Operacion,String>("variacion"));
        Callback<TableColumn<Operacion, String>, TableCell<Operacion, String>> cellFactory=(TableColumn<Operacion,String> param) ->{
          final  TableCell<Operacion,String> cell=new TableCell<>(){
              @Override
              public void updateItem(String item,boolean empty){
               if (empty){
                   setGraphic(null);
               }  else {
                   Button editButton=new Button();
                   editButton.setGraphic(setBotonIcon.ImgUpdate());
                   editButton.setStyle(setBotonIcon.ButtonStyle());
                   Button deleteButton=new Button();
                   deleteButton.setGraphic(setBotonIcon.ImgDelete());
                   deleteButton.setStyle(setBotonIcon.ButtonStyle());

                   deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                       @Override
                       public void handle(ActionEvent actionEvent) {
                           Operacion operacion=getTableView().getItems().get(getIndex());
                           EliminarOperacion(operacion);
                       }
                   });
                   editButton.setOnAction(new EventHandler<ActionEvent>() {
                       @Override
                       public void handle(ActionEvent actionEvent) {
                           Operacion operacion=getTableView().getItems().get(getIndex());
                           EditarOperacion(operacion);
                       }
                   });


                   HBox containBoton=new HBox(deleteButton,editButton);
                   containBoton.setStyle(setBotonIcon.HboxStyle());
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
        Platform.runLater(()-> sizeColumnTable.ajustarColumna(tblDelanteras));
        tblDelanteras.getColumns().addAll(cellNombre,cellPrecio,cellVariacion,cellOpciones);
    }
    public void initLista(){
        DataOperacion dataOperacion=new DataOperacion();
        listDelantera= FXCollections.observableArrayList(dataOperacion.viewOperacion(new Operacion(0,codigo_tipo,codigo_estilo,"",0,0),"viewtipo"));
        filterDelantera=new FilteredList<>(listDelantera, p ->true);
        tblDelanteras.setItems(filterDelantera);

    }
    public  void llenarLista(){
        txtBuscar.textProperty().addListener((prop,old,text) ->{
            filterDelantera.setPredicate(delantera ->{
                if (text==null || text.isEmpty()){
                    return  true;
                }
                String texto=text.toLowerCase();
                if(String.valueOf(delantera.getCodigo()).toLowerCase().contains(texto)){
                    return true;
                }
                else if(delantera.getNombre().toLowerCase().contains(texto)){
                    return true;
                }


                return false;
            });
        });
    }




    private void EliminarOperacion(Operacion operacion){
        if (alertDialog.alertConfirm("Operación","Esta seguro de descativar esta operación")){
            DataOperacion dataOperacion=new DataOperacion();
            dataOperacion.crudOperacion(operacion,"delete");
            initLista();
            tblDelanteras.refresh();
        }
    }

    private void EditarOperacion(Operacion operacion){
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
            codigo_estilo=operacion.getCodigoEstilo();
                codigo_tipo=operacion.getCodigoTipo();
                initLista();
                tblDelanteras.refresh();

            }));

        } catch (IOException e) {
            e.printStackTrace();

        }


    }


    public void IngresarNuevo(ActionEvent actionEvent) {
        try{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/Operacion/FormOperacion.fxml"));
            Parent parent=loader.load();
            Stage stage=new Stage();
            stage.setScene(new Scene(parent));
            stage.getIcons().add(new Image("/Img/icon.png"));
            stage.setTitle("Maya_textil");
            FormOperacion formOperacion=loader.getController();
            formOperacion.pasarDatos(null,"new",tipo_operacion,codigo_tipo,codigo_estilo);
            stage.setOnHiding((event ->{
              initLista();
              tblDelanteras.refresh();
            }));
            stage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void pasarEstilo(Estilo estilo,String tipo){
        tipo_operacion=tipo;
        switch (tipo_operacion){
            case "Delantera":
                codigo_tipo=1;
                codigo_estilo=estilo.getCodigo();
                lblTitulo.setText("Operaciones de la Delantera");
                initLista();
                tblDelanteras.refresh();

                break;
            case "Trasera":
                codigo_tipo=2;
                codigo_estilo=estilo.getCodigo();
                lblTitulo.setText("Operaciones de la Trasera");
                initLista();
                tblDelanteras.refresh();
                break;
            case "Ensamble":
                codigo_tipo=3;
                codigo_estilo=estilo.getCodigo();
                lblTitulo.setText("Operaciones del Ensamble");
                initLista();
                tblDelanteras.refresh();
                break;
        }

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

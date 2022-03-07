package Bodega;

import Bodega.DAO.Bodega;
import Bodega.DAO.DataBodega;
import ClassAux.AlertDialog;
import ClassAux.SetBotonIcon;
import ClassAux.SizeColumnTable;
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

public class BodegaController implements Initializable {
    public Button btnIngresarNuevo;
    public TextField txtBuscar;

    static ObservableList<Bodega> bodega;
    static FilteredList<Bodega> fileterBodega;
    public TableView<Bodega> tblBodega;
    public TableColumn<Bodega,String> cellCodigo;
    public TableColumn<Bodega,String> cellTipo;
    public TableColumn<Bodega,String> cellCantidad;
    public TableColumn<Bodega,String> cellColor;
    public TableColumn<Bodega,String> cellOpciones;
    AlertDialog alertDialog=new AlertDialog();
    SizeColumnTable sizeColumnTable=new SizeColumnTable();
   SetBotonIcon setBotonIcon=new SetBotonIcon();
    

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTabla();
        initList();
        llenarLista();
    }
    public void initTabla(){
        cellCodigo =new TableColumn<>("Código");
        cellTipo =new TableColumn<>("Tipo de tela");
        cellCantidad =new TableColumn<>("Cantidad/Rollos");
        cellColor =new TableColumn<>("Color");
        cellOpciones =new TableColumn<>("Opciones");

        cellCodigo.setCellValueFactory(new PropertyValueFactory<Bodega,String>("codigo_tela"));
        cellTipo.setCellValueFactory(new PropertyValueFactory<Bodega,String>("tipo"));
        cellCantidad.setCellValueFactory(new PropertyValueFactory<Bodega,String>("colores"));
        cellColor.setCellValueFactory(new PropertyValueFactory<Bodega,String>("cantidad"));

        Callback<TableColumn<Bodega,String>,TableCell<Bodega,String>> cellFactory=(TableColumn<Bodega,String> param)->{
            final  TableCell<Bodega, String> cell=new TableCell<>(){
                @Override
                public void updateItem(String item,boolean empty){
                    if (empty){
                        setGraphic(null);
                    }else{
                        Button editButton=new Button();
                        editButton.setGraphic(setBotonIcon.ImgUpdate());
                        editButton.setStyle(setBotonIcon.ButtonStyle());
                        Button deleteButton=new Button();
                        deleteButton.setGraphic(setBotonIcon.ImgDelete());
                        deleteButton.setStyle(setBotonIcon.ButtonStyle());
                        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Bodega bodega=getTableView().getItems().get(getIndex());
                                EliminarBodega(bodega);
                            }
                        });

                      editButton.setOnAction(new EventHandler<ActionEvent>() {
                          @Override
                          public void handle(ActionEvent actionEvent) {
                              Bodega bodega=getTableView().getItems().get(getIndex());
                              EditarBodega(bodega);
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
        Platform.runLater(()-> sizeColumnTable.ajustarColumna(tblBodega));
        tblBodega.getColumns().addAll(cellCodigo,cellTipo,cellColor,cellCantidad,cellOpciones);
    }
    public  void initList(){
        DataBodega datos=new DataBodega();
        bodega= FXCollections.observableArrayList(datos.viewBodega(new Bodega(), "viewall"));
        fileterBodega=new FilteredList<>(bodega, s->true);
        tblBodega.setItems(fileterBodega);

    }
    public void llenarLista(){
        txtBuscar.textProperty().addListener((prop,old,text) ->{
            fileterBodega.setPredicate(bodega -> {
                if (text ==null || text.isEmpty()){
                    return  true;
                }
                String texto=text.toLowerCase();
                if (bodega.getCodigo_tela().toLowerCase().contains(texto)){
                    return true;
                }
                if (bodega.getTipo().toLowerCase().contains(texto)){
                    return true;
                }
                if (bodega.getColores().toLowerCase().contains(texto)){
                    return  true;
                }
                return  false;
            });
        });
    }

    public void nuevoIngreso(ActionEvent actionEvent) {
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/Bodega/FormBodega.fxml"));
            Parent parent=loader.load();
            Stage stage=new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            stage.getIcons().add(new Image("/Img/icon.png"));
            stage.setOnHiding((event ->{
               initList();
               tblBodega.refresh();
            }));


        }catch (IOException e){
            e.printStackTrace();

        }



    }
    public void EliminarBodega(Bodega bodega){
        if (alertDialog.alertConfirm("", "Esta seguro de eliminar este registro")){
            DataBodega datos=new DataBodega();
            datos.crudBodega(bodega,"delete");
            BodegaController bodegaController=new BodegaController();
            initList();
            tblBodega.refresh();

        }
    }
    public void EditarBodega(Bodega bodega){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Bodega/FormBodega.fxml"));
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Maya Textil");
            stage.getIcons().add(new Image("/Img/icon.png"));
            stage.setScene(new Scene(parent));
            FormBodega formEmpleado = loader.getController();
            formEmpleado.pasarRegistro(bodega);
            stage.show();
            stage.setOnHiding((event -> {
                initList();
                tblBodega.refresh();
            }));

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}

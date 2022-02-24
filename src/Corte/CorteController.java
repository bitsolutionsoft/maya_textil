package Corte;

import ClassAux.AlertDialog;
import ClassAux.EstiloBoton;
import ClassAux.SizeColumnTable;
import Corte.DAO.Corte;
import Corte.DAO.DataCorte;
import Corte.DAO.DataRollos;
import Corte.DAO.Rollos;
import Corte.Pdf.imprimir;
import Empleado.DAO.Empleado;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class CorteController implements Initializable {
    public Button btnIngresarNuevo;

    public TextField txtBuscar;
    static ObservableList<Corte> cortes;
    static FilteredList<Corte> cortedata;
    static ObservableList<Rollos> listTela;
    static FilteredList<Rollos> filterListTela;
    public ListView<Rollos> listViewTela;
    public TableView<Corte> tblCorte;
    public TableColumn<Corte, String> cellCodigo;
    public TableColumn<Corte, String> cellEstilo;
    public TableColumn<Corte, String> cellFecha;
    public TableColumn<Corte, String> cellRollos;
    public TableColumn<Corte, String> cellCantidad;
    public TableColumn<Corte, String> cellEstado;
    public TableColumn<Corte, String> cellOpciones;
    public TableView<Rollos> tblTela;
    public TableColumn<Rollos, String> cellCodigoTela;
    public TableColumn<Rollos, String> cellTipo;
    public TableColumn<Rollos, String> cellColor;
    public TableColumn<Rollos, String> cellCantTela;
    public TableColumn<Rollos, String> cellOpcionesTela;
    EstiloBoton estiloBoton=new EstiloBoton();
    SizeColumnTable sizeColumnTable=new SizeColumnTable();
    AlertDialog alertDialog=new AlertDialog();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
initTablaCorte();
initTablaTela();
        initLista();
        llenarListaCorte();

    }
    public void initTablaCorte(){
        cellCodigo=new TableColumn<>("Código");
        cellEstilo=new TableColumn<>("Estilo");
        cellFecha=new TableColumn<>("Fecha Corte");
        cellCantidad=new TableColumn<>("Cantidad");
        cellRollos=new TableColumn<>("C/Rollos");
        cellEstado=new TableColumn<>("Estado");
        cellOpciones=new TableColumn<>("Opciones");

        cellCodigo.setCellValueFactory(new PropertyValueFactory<Corte,String>("idcorte"));
        cellEstilo.setCellValueFactory(new PropertyValueFactory<Corte,String>("nombre"));
        cellFecha.setCellValueFactory(new PropertyValueFactory<Corte,String>("fecha_corte"));
        cellCantidad.setCellValueFactory(new PropertyValueFactory<Corte,String>("cantidad"));
        cellRollos.setCellValueFactory(new PropertyValueFactory<Corte,String>("cant_rollo"));
        cellEstado.setCellValueFactory(new PropertyValueFactory<Corte,String>("estado"));

        Callback<TableColumn<Corte,String>, TableCell<Corte,String>> cellFactory= (TableColumn<Corte,String> param) ->{
          final TableCell<Corte,String> cell=new TableCell<>() {
              @Override
              public void updateItem(String item, boolean empty) {
                  if (empty) {
                      setGraphic(null);
                  } else {
                      ImageView editButon = new ImageView(estiloBoton.editImg());
                      editButon.setFitHeight(estiloBoton.sizeButton());
                      editButon.setFitWidth(estiloBoton.sizeButton());
                      editButon.setStyle(estiloBoton.Boton());


                      ImageView deleteButon = new ImageView(estiloBoton.deleteImg());
                      deleteButon.setFitHeight(estiloBoton.sizeButton());
                      deleteButon.setFitWidth(estiloBoton.sizeButton());
                      deleteButon.setStyle(estiloBoton.Boton());

                      editButon.setOnMouseClicked(new EventHandler<MouseEvent>() {
                          @Override
                          public void handle(MouseEvent mouseEvent) {
                              Corte corte=tblCorte.getSelectionModel().getSelectedItem();
                              EditarCorte(corte);
                          }
                      });
                      deleteButon.setOnMouseClicked(new EventHandler<MouseEvent>() {
                          @Override
                          public void handle(MouseEvent mouseEvent) {
                              Corte corte=tblCorte.getSelectionModel().getSelectedItem();
                              EliminarCorte(corte);
                          }
                      });
                      HBox containButton = new HBox(deleteButon, editButon);
                      containButton.setStyle("-fx-alignment:center");
                      HBox.setMargin(deleteButon, new Insets(2, 10, 2, 2));
                      HBox.setMargin(editButon, new Insets(2, 2, 2, 10));
                      setGraphic(containButton);
                  }
                  setText(null);
              };
          };
               return cell;
        };
       cellOpciones.setCellFactory(cellFactory);
        tblCorte.setEditable(true);
        tblCorte.getColumns().addAll(cellCodigo,cellEstilo,cellFecha,cellCantidad,cellRollos,cellEstado,cellOpciones);
        Platform.runLater(()-> sizeColumnTable.ajustarColumna(tblCorte));

        cellEstado.setCellFactory(new Callback<TableColumn<Corte, String>, TableCell<Corte, String>>() {
            @Override
            public TableCell<Corte, String> call(TableColumn<Corte, String> tableColumn) {
                return new TableCell<>(){
                    @Override
                    protected void updateItem(String item, boolean empty){
                        super.updateItem(item,empty);
                        if (!empty){
                            if (item.equals("Activo")) {
                                setStyle(estiloBoton.Activo1());
                            }else{
                                setStyle(estiloBoton.NoActivo1());
                            }
                            setText(item);
                            }else{
                            setText("");
                        }
                    }
                };
            }
        });
        tblCorte.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton()==MouseButton.PRIMARY && event.getClickCount()==1){

                    Corte corte=tblCorte.getSelectionModel().getSelectedItem();
                    initListaTela(corte);
                    tblTela.refresh();
                }
            }
        });
    }
    public void initTablaTela(){
        cellCodigoTela =new TableColumn<>("Código tela");
        cellTipo =new TableColumn<>("Tipo de Tela");
        cellColor =new TableColumn<>("Color");
        cellCantTela =new TableColumn<>("Cantidad/R");
        cellOpcionesTela =new TableColumn<>("Opciones");

        cellCodigoTela.setCellValueFactory(new PropertyValueFactory<Rollos,String>("idrollo"));
        cellTipo.setCellValueFactory(new PropertyValueFactory<Rollos,String>("tipo"));
        cellColor.setCellValueFactory(new PropertyValueFactory<Rollos,String>("color"));
        cellCantTela.setCellValueFactory(new PropertyValueFactory<Rollos,String>("cantidad"));


        Callback<TableColumn<Rollos,String>, TableCell<Rollos,String>> cellFactory= (TableColumn<Rollos,String> param) ->{
            final TableCell<Rollos,String> cell=new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                    } else {

                        ImageView deleteButon = new ImageView(estiloBoton.deleteImg());
                        deleteButon.setFitHeight(estiloBoton.sizeButton());
                        deleteButon.setFitWidth(estiloBoton.sizeButton());
                        deleteButon.setStyle(estiloBoton.Boton());


                        deleteButon.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                Rollos rollos=tblTela.getSelectionModel().getSelectedItem();
                                EliminarTela(rollos);
                            }
                        });
                        HBox containButton = new HBox(deleteButon);
                        containButton.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteButon, new Insets(2, 10, 2, 2));
                        setGraphic(containButton);
                    }
                    setText(null);
                };
            };
            return cell;
        };
        cellOpcionesTela.setCellFactory(cellFactory);
        tblTela.setEditable(true);
        tblTela.getColumns().addAll(cellCodigoTela,cellTipo,cellColor,cellCantTela,cellOpcionesTela);
        Platform.runLater(()->sizeColumnTable.ajustarColumna(tblTela));

    }
    public void initLista(){
        DataCorte datos=new DataCorte();
        cortes = FXCollections.observableArrayList(datos.viewCorte("viewall"));
        cortedata=new FilteredList<Corte>(cortes,s->true);
        tblCorte.setItems(cortedata);
        /*
        listView.setItems(cortedata);
        //para llenar las filas personalizadas

        listView.setCellFactory(new Callback<ListView<Corte>, ListCell<Corte>>() {
            @Override
            public ListCell<Corte> call(ListView<Corte> param) {
                CorteCell corteCell=new CorteCell();
                return corteCell;
            }
        });
*/
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
    public void initListaTela(Corte corte){
        DataRollos datos=new DataRollos();
        listTela = FXCollections.observableArrayList(datos.viewTela(new Rollos(0,corte.getIdcorte(),0,0),"viewxcorte"));
        filterListTela=new FilteredList<Rollos>(listTela,s->true);
        tblTela.setItems(filterListTela);
      /*  listView.setItems(filterListTela);
        //para llenar las filas personalizadas

        listView.setCellFactory(new Callback<ListView<Rollos>, ListCell<Rollos>>() {
            @Override
            public ListCell<Rollos> call(ListView<Rollos> param) {
                CellTela cellTela=new CellTela();
                return cellTela;
            }
        });*/

    }



    public void nuevoCorte(ActionEvent actionEvent) {
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/Corte/FormCorte.fxml"));
            Parent parent=loader.load();
            Stage stage=new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            stage.getIcons().add(new Image("/Img/icon.png"));
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
            stage.getIcons().add(new Image("/Img/icon.png"));
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
            Corte ct=new Corte(corte.getIdcorte(),0,0,"2021-07-31",0,"No Activo");
            DataCorte datos=new DataCorte();
            datos.crudCorte(ct,"delete");
            initLista();
            tblCorte.refresh();
        }
    }
    public void EliminarTela(Rollos rollos){
        if (alertDialog.alertConfirm("","Esta seguiro de eliminar este registro")) {
            //Rollos rollos = getListView().getSelectionModel().getSelectedItem();
            DataRollos dataRollos=new DataRollos();
            dataRollos.crudCorte(rollos,"delete");

            CorteController controller=new CorteController();
            initListaTela(new Corte(rollos.getIdcorte(),0,0,"2021-08-12",0,""));
            tblTela.refresh();
        }
    }

    public void imprimirDelantera(ActionEvent actionEvent) {
        imprimir imprimir=new imprimir();
        imprimir.imprimirpdf("C:/Program Files/BitSolutionSoft/Maya Textil/Pdf/delantera.pdf");
    }

    public void imprimirTrasera(ActionEvent actionEvent) {
        imprimir imprimir=new imprimir();
        imprimir.imprimirpdf("C:/Program Files/BitSolutionSoft/Maya Textil/Pdf/trasera.pdf");
    }

    public void imprimirEnsamble(ActionEvent actionEvent) {
        imprimir imprimir=new imprimir();
        imprimir.imprimirpdf("C:/Program Files/BitSolutionSoft/Maya Textil/Pdf/ensamble.pdf");
    }

    public void imprimirControlCorte(ActionEvent actionEvent) {
        imprimir imprimir=new imprimir();
        imprimir.imprimirpdf("C:/Program Files/BitSolutionSoft/Maya Textil/Pdf/controlcorte.pdf");
    }
}

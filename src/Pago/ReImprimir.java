package Pago;

import ClassAux.EstiloBoton;
import ClassAux.SizeColumnTable;
import Corte.Pdf.imprimir;
import Pago.DAO.Adelanto;
import Pago.DAO.Boletas;
import Pago.DAO.DataDetallePago;
import Pago.DAO.DetallePago;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JasperExportManager;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReImprimir implements Initializable {
    public TableView <Boletas> tblBoletas;
    public TableColumn <Boletas, String> cellNombre;
    public TableColumn <Boletas,String> cellOpciones;
    public TextField txtBuscar;
    SizeColumnTable sizeColumnTable=new SizeColumnTable();
    EstiloBoton estiloBoton=new EstiloBoton();

    static ObservableList<Boletas> listaBoleto;
    static FilteredList<Boletas> filterboleto;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTabla();
        LLenarTabla();
        filtrar();
    }

    public void initTabla(){
        cellNombre=new TableColumn<>("Boletas");
        cellOpciones=new TableColumn<>("Acción");

        cellNombre.setCellValueFactory(new PropertyValueFactory<Boletas,String>("nombre"));
       cellOpciones.setCellFactory(new Callback<TableColumn<Boletas, String>, TableCell<Boletas, String>>() {
            @Override
            public TableCell<Boletas, String> call(TableColumn<Boletas, String> boletasStringTableColumn) {
                return new TableCell<Boletas,String>(){
                    @Override
                    public  void updateItem(String item, boolean empty){
                        super.updateItem(item,empty);
                        if (empty){
                            setGraphic(null);
                        }else{
                            ImageView btnImprimir=new ImageView(estiloBoton.Imprimir());
                            btnImprimir.setFitWidth(estiloBoton.sizeButton());
                            btnImprimir.setFitWidth(estiloBoton.sizeButton());
                            btnImprimir.setStyle(estiloBoton.Boton());
                            ImageView btnVer=new ImageView(estiloBoton.Open());
                            btnVer.setFitWidth(estiloBoton.sizeButton());
                            btnVer.setFitWidth(estiloBoton.sizeButton());
                            btnVer.setStyle(estiloBoton.Boton());
                            btnImprimir.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    if (event.getClickCount()==1 && event.getButton()== MouseButton.PRIMARY){
                                        Boletas boletas= tblBoletas.getSelectionModel().getSelectedItem();
                                        Imprimir(boletas);
                                    }
                                }
                            });
                            btnVer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    if (event.getClickCount()==1 && event.getButton()== MouseButton.PRIMARY){
                                        Boletas boletas= tblBoletas.getSelectionModel().getSelectedItem();
                                        VerPdf(boletas);
                                    }
                                }
                            });
                            HBox containButton= new HBox(btnImprimir,btnVer);
                            containButton.setStyle("-fx-alignment:center");
                            HBox.setMargin(btnImprimir, new Insets(5,5,5,5));
                            HBox.setMargin(btnVer, new Insets(5,5,5,5));
                            setGraphic(containButton);
                        }
                    }
                };
            }
        });

        tblBoletas.getColumns().addAll(cellNombre,cellOpciones);
      Platform.runLater(()->sizeColumnTable.ajustarColumna(tblBoletas));

    }

    public  ArrayList<Boletas> lista(){
        ArrayList<Boletas> list=new ArrayList<>();
        File carpeta=new File("C:/Constancia/");
        File[] archivos;
        if(carpeta.exists()) {
            archivos = carpeta.listFiles();
            for (int i = 0; i < archivos.length; i++) {
                if (archivos[i].getName().endsWith("pdf")) {
                    Boletas boletas = new Boletas();
                    boletas.setNombre(archivos[i].getName());
//                System.out.println(archivos[i].getName());
                    list.add(boletas);
                }
            }
        }
    return list;
    }
    public void LLenarTabla(){

        listaBoleto = FXCollections.observableArrayList(lista());
        filterboleto=new FilteredList<Boletas>(listaBoleto,s->true);
        tblBoletas.setItems(filterboleto);


    }
    public  void filtrar(){
        txtBuscar.textProperty().addListener((prop,old,text) ->{
            filterboleto.setPredicate(boleta ->{
                if (text==null || text.isEmpty()){
                    return  true;
                }
                String texto=text.toLowerCase();
                if(boleta.getNombre().contains(texto)){
                    return true;
                }

                return false;
            });
        });
    }

    public void Imprimir(Boletas boletas){
        imprimir imprimir=new imprimir();
        imprimir.imprimirpdf("C:/Constancia/"+boletas.getNombre());
    }
    public void VerPdf(Boletas boletas){
        try {
            File path = new File ("C:/Constancia/"+boletas.getNombre());
            Desktop.getDesktop().open(path);

        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

package Pago;

import ClassAux.SetBotonIcon;
import ClassAux.SizeColumnTable;
import Corte.Pdf.imprimir;
import Pago.DAO.Boletas;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

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
    SetBotonIcon setBotonIcon=new SetBotonIcon();

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

                            Button btnImprimir=new Button();
                            btnImprimir.setGraphic(setBotonIcon.ImgPrint());
                            btnImprimir.setStyle(setBotonIcon.ButtonStyle());
                            Button btnVer=new Button();
                            btnVer.setGraphic(setBotonIcon.ImgOpen());
                            btnVer.setStyle(setBotonIcon.ButtonStyle());
                            btnImprimir.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    Boletas boletas=getTableView().getItems().get(getIndex());
                                    Imprimir(boletas);
                                }
                            });

                            btnVer.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    Boletas boletas=getTableView().getItems().get(getIndex());
                                    VerPdf(boletas);
                                }
                            });

                            HBox containButton= new HBox(btnImprimir,btnVer);
                            containButton.setStyle(setBotonIcon.HboxStyle());
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

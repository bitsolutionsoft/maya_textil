package Informe;

import Bodega.DAO.Bodega;
import Bodega.DAO.DataBodega;
import ClassAux.AlertDialog;
import ClassAux.SizeColumnTable;
import ClassAux.Util;
import Informe.DAO.*;
import Pago.DAO.Pago;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class InformeController implements Initializable {
    public TextField txtBuscar;
    public RadioButton rdia;
    public RadioButton rSemana;
    public RadioButton rMes;
    public DatePicker fInicial;
    public DatePicker fFinal;
    public Button btnVer;
    public Label lbladquirida;
    public Label lblusada;
    public Label lblrestante;
    public Label lblcortehecho;
    public Label lblcostototal;
    public TableView <ResumenCorte>tblCorte;
    public TableColumn<ResumenCorte,String> cellIdcorte;
    public TableColumn<ResumenCorte,String> cellIdestilo;
    public TableColumn<ResumenCorte,String> cellNombre;
    public TableColumn<ResumenCorte,String> cellCantidad;
    public TableColumn<ResumenCorte,String> cellFecha_corte;
    public TableColumn<ResumenCorte,String> cellCant_Rollo;
    public TableColumn<ResumenCorte,String> cellTotal;
    public TableView <ResumenPago>tblRollo;
    public TableColumn<ResumenPago,String> cellCorte;
    public TableColumn<ResumenPago,String> cellTotal2;
    public TableColumn<ResumenPago,String> cellNombre2;
    public TableColumn<ResumenPago,String> cellSutotal;
    public Label cantcorte;
    public Label cantpantalon;
    public Label lblgeneral;

    SizeColumnTable size_tabla=new SizeColumnTable();
    ObservableList<ResumenCorte> listPagos=null;
    FilteredList<ResumenCorte> filterPago;
    public ListView <Bodega> listBodega;
    static ObservableList<Bodega> bodega;
    static FilteredList<Bodega> fileterBodega;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iniciarTabla();
        accionRadioButtons();
        initList(listBodega);
        llenarLista();

        tblCorte.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount()==1 && event.getButton()== MouseButton.PRIMARY){
                    ResumenCorte resummen=tblCorte.getSelectionModel().getSelectedItem();
                    llenarTabla2(resummen.getIdpago());
                    tblRollo.refresh();
                }

            }
        });
    }
    /////////////////////
    public void accionRadioButtons(){
        rdia.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                rMes.setSelected(false);
                rSemana.setSelected(false);
                VerCierreCorte(new Cortes(obtenerfechaHoy(),obtenerfechaHoy()),"dia");
                llenarInformeCorte(new Cortes(obtenerfechaHoy(),obtenerfechaHoy()),"dia");
                lblgeneral.setText("Dìa");
            }
        });
        rSemana.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                rdia.setSelected(false);
                rMes.setSelected(false);
                VerCierreCorte(new Cortes(obtenerfechaHoy(),obtenerfechaHoy()),"semana");
                llenarInformeCorte(new Cortes(obtenerfechaHoy(),obtenerfechaHoy()),"semana");
                lblgeneral.setText("Semana");
            }//pruebo
        });
        rMes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                rdia.setSelected(false);
                rSemana.setSelected(false);
                VerCierreCorte(new Cortes(obtenerfechaHoy(),obtenerfechaHoy()),"mes");
                llenarInformeCorte(new Cortes(obtenerfechaHoy(),obtenerfechaHoy()),"mes");
                lblgeneral.setText("Mes");
            }
        });

        btnVer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                rSemana.setSelected(false);
                rdia.setSelected(false);
                rMes.setSelected(false);
                String fe_inicial=returnFechaSelect(fInicial,"Fecha Inicial");
                String fe_final=returnFechaSelect(fFinal,"Fecha Final");
                VerCierreCorte(new Cortes(fe_inicial,fe_final),"rango");
                llenarInformeCorte(new Cortes(fe_inicial,fe_final),"rango");
                lblgeneral.setText("de "+ fe_inicial+ " al "+ fe_final);


            }

        });
    }
    /////////////
    public String obtenerfechaHoy(){
        return String.valueOf(LocalDate.now());
    }
    public String fecha(DatePicker fecha, String op){
        String fe="";
        if (fecha.getValue()==null){
            AlertDialog alertDialog=new AlertDialog();
            alertDialog.alert("","Por favor de seleccionar la fecha "+op+" ");
        }else{
            fe=fecha.getValue().toString();
        }
        return fe;
    }
    public void iniciarTabla(){
        //tabla corte
        cellIdcorte=new TableColumn<>("Código");
        cellNombre=new TableColumn<>("Nombre de Estilo");
        cellCantidad=new TableColumn<>("Cantidad");
        cellFecha_corte=new TableColumn<>("Fecha Corte");
        cellCant_Rollo=new TableColumn<>("Cant. Rollo");
        cellTotal=new TableColumn<>("Total");

        cellIdcorte.setPrefWidth(70);
        cellNombre.setPrefWidth(310);
        cellCant_Rollo.setPrefWidth(90);
        cellCantidad.setPrefWidth(70);
        cellTotal.setPrefWidth(70);
        cellFecha_corte.setPrefWidth(90);


        cellIdcorte.setCellValueFactory(new PropertyValueFactory<ResumenCorte,String>("idcorte"));
        cellNombre.setCellValueFactory(new PropertyValueFactory<ResumenCorte,String>("nombre"));
        cellCantidad.setCellValueFactory(new PropertyValueFactory<ResumenCorte,String>("cantidad"));
        cellFecha_corte.setCellValueFactory(new PropertyValueFactory<ResumenCorte,String>("fecha_corte"));
        cellCant_Rollo.setCellValueFactory(new PropertyValueFactory<ResumenCorte,String>("cant_rollo"));
        cellTotal.setCellValueFactory(new PropertyValueFactory<ResumenCorte,String>("total"));
        tblCorte.setEditable(true);
        tblCorte.getColumns().addAll(cellIdcorte, cellNombre,cellCantidad, cellFecha_corte ,cellCant_Rollo,cellTotal);
       // Platform.runLater(()-> size_tabla.ajustarColumna(tblCorte));

        //tabla rollo
        cellCorte=new TableColumn<>("Codigo corte");
        cellTotal2=new TableColumn<>("Total del corte");
        cellSutotal=new TableColumn<>("Total por operacion");
        cellNombre2=new TableColumn<>("Operación");


        cellCorte.setCellValueFactory(new PropertyValueFactory<ResumenPago,String>("idcorte"));
        cellTotal2.setCellValueFactory(new PropertyValueFactory<ResumenPago,String>("total"));
        cellSutotal.setCellValueFactory(new PropertyValueFactory<ResumenPago,String>("subtotal"));
        cellNombre2.setCellValueFactory(new PropertyValueFactory<ResumenPago,String>("nombre"));
        tblRollo.setEditable(true);
        tblRollo.getColumns().addAll(cellCorte, cellNombre2, cellSutotal,cellTotal2);
        Platform.runLater(()-> size_tabla.ajustarColumna(tblRollo));


    }

    public void llenarTabla(ObservableList<ResumenCorte> list){
        listPagos= FXCollections.observableArrayList(list);
        filterPago=new FilteredList<>(listPagos, p-> true);
        SortedList<ResumenCorte> sortedList=new SortedList<>(filterPago);
        sortedList.comparatorProperty().bind(tblCorte.comparatorProperty());
        tblCorte.setItems(sortedList);

    }

    public void llenarTabla2(int codigo){
        DataResumPago resumen=new DataResumPago();
        ObservableList<ResumenPago> lis=FXCollections.observableArrayList(resumen.viewResumPago(codigo));
        FilteredList<ResumenPago> filter =new FilteredList<ResumenPago>(lis, p -> true);
        SortedList<ResumenPago> st=new SortedList<>(filter);
        tblRollo.setItems(st);
    }

    public void  VerCierreCorte(Cortes cortes, String accion){
        DataResumen dataResumenes=new DataResumen();
        ObservableList<ResumenCorte> list= FXCollections.observableArrayList(dataResumenes.viewCortes(cortes,accion));
        if (!list.isEmpty()){
            llenarTabla(list);
        }
        /*
        ArrayList<Cuenta> datosCueta=new ArrayList<>(dataVentas.viewCuenta(ventas,gaccion));
        if (!datosCueta.isEmpty()){
            lblVentas.setText("Q "+datosCueta.get(0).getVentas());
            lblCompras.setText("Q "+datosCueta.get(0).getInversion());
            lblGanancia.setText("Q "+datosCueta.get(0).getGanancia());
        }*/
    }
    public String returnFechaSelect(DatePicker txt, String nombre){

        if (txt.getValue()==null){
            Util.Advertencia("Advertencia", "Por favor la selecione " + nombre);
            return null;
        }else{
            return txt.getValue().toString();
        }

    }


    public  void initList(ListView<Bodega> listView){
        DataBodega datos=new DataBodega();
        bodega= FXCollections.observableArrayList(datos.viewBodega(new Bodega(), "viewall"));
        fileterBodega=new FilteredList<>(bodega, s->true);
        listView.setItems(fileterBodega);
        listView.setCellFactory(new Callback<ListView<Bodega>, ListCell<Bodega>>() {
            @Override
            public ListCell<Bodega> call(ListView<Bodega> bodegaListView) {
                CellBodega cellBodega=new CellBodega();
                return cellBodega;
            }
        });
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
            Parent parent = FXMLLoader.load(getClass().getResource("/Bodega/FormBodega.fxml"));
            Stage stage=new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            stage.getIcons().add(new Image("/img/icon.png"));
            stage.setOnHiding((event ->{
                initList(listBodega);
                listBodega.refresh();
            }));


        }catch (IOException e){
            e.printStackTrace();

        }



    }
    public void llenarInformeCorte(Cortes c,String accion){
        DataInformeCorte data=new DataInformeCorte();
        ObservableList<InformeCorte> corte=FXCollections.observableArrayList(data.viewCortes(c,accion));
        if (!corte.isEmpty()){
            cantcorte.setText(String.valueOf(corte.get(0).getCanCorte()));
            cantpantalon.setText(String.valueOf(corte.get(0).getCantPantalon()));
        }


    }
}

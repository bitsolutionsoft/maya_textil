package Corte;

import ClassAux.AlertDialog;
import Bodega.DAO.Bodega;
import Bodega.BodegaController;
import ClassAux.Util;
import Corte.DAO.Corte;
import Corte.DAO.DataCorte;
import Corte.DAO.DataRollos;
import Corte.DAO.Rollos;
import Operacion.Estilo.DAO.Estilo;
import Operacion.Estilo.EstiloController;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FormCorte implements Initializable {


    public TextField txtCorte;
    public Button btnEstilo;
    public TextField txtCantidad;
    public DatePicker fecha;
    public int txtRollo;
    public Button btnIngresarCorte;
    public CheckBox Activo;
    public CheckBox Noactivo;
    public Button SeleccionarTela;
    public TableView<Rollos> tblTela;
    public TableColumn<Rollos, String> cellCodigo;
    public TableColumn<Rollos, String> celltipo;
    public TableColumn<Rollos, String> cellColor;
    public TableColumn<Rollos,Integer> cellCantidad;
    public Label lblRollo;
    ObservableList<Rollos> listTela;
    FilteredList<Rollos> filterTela;
    private String estado = "Activo";
    private String accion = "new";
    public Label labelTitulo;
    public Label lbCodigo;
    public Label lbNombre;
    public Label lbEstado2;
    private  Estilo estiloSeleccionado ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        estado("Activo");
        btnEstilo.setGraphic(new ImageView("/Img/down.png"));
        lbCodigo.setVisible(false);
        lbEstado2.setVisible(false);
        lblRollo.setVisible(false);
        iniciarTabla();
    }

    private void iniciarTabla() {
        tblTela.setEditable(true);

        cellCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo_tela"));
        celltipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        cellColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        cellCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        cellCantidad.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer integer) {
                return integer.toString();
            }

            @Override
            public Integer fromString(String s) {
                return Integer.parseInt(s);
            }
        }));
        cellCantidad.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Rollos, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Rollos, Integer> event) {
                Rollos rollos = tblTela.getSelectionModel().getSelectedItem();
                int nuevaCantidad =event.getNewValue();
                for (int i = 0; i < listTela.size(); i++) {
                    if (listTela.get(i).getCodigo_tela() == rollos.getCodigo_tela()) {
                        listTela.get(i).setCantidad(nuevaCantidad);
                    }
                }
                calcularCantidad();
            }
        });

        //Platform.runLater(()-> ajustarTabla.ajustarColumna(tblEstilo));
        tblTela.getColumns().setAll(cellCodigo, celltipo, cellColor, cellCantidad);
        //eliminar datos de la tabla  con evento de doble clic

        tblTela.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount()==2 && event.getButton()==MouseButton.SECONDARY){
                    Rollos roll=tblTela.getSelectionModel().getSelectedItem();
                    for (int i=0; i<listTela.size(); i++){
                        if (listTela.get(i).getIdrollo()==roll.getIdrollo()){
                            listTela.remove(i);
                        }
                    }
                }

                tblTela.refresh();
            }
        });
    }

public void calcularCantidad(){
        txtRollo=0;
        for (int i=0; i<listTela.size();i++){
            txtRollo=txtRollo+listTela.get(i).getCantidad();
        }
        lblRollo.setText(String.valueOf(txtRollo));
}

    private void llenarTabla(ArrayList<Rollos> list){
        listTela= FXCollections.observableArrayList(list);
        filterTela=new FilteredList<>(listTela, p ->true);
        SortedList<Rollos> datosOrdenados=new SortedList<>(filterTela);
        datosOrdenados.comparatorProperty().bind(tblTela.comparatorProperty());
        tblTela.setItems(datosOrdenados);
    }

    public ArrayList<Rollos> devolverBodega(Bodega bodega){
        ArrayList<Rollos> list=new ArrayList<>();
        Rollos roll=new Rollos();
        roll.setIdrollo(0);
        roll.setIdetela(bodega.getIdBodega());
        roll.setCodigo_tela(bodega.getCodigo_tela());
        roll.setTipo(bodega.getTipo());
        roll.setColor(bodega.getColores());
        roll.setCantidad(1);
        list.add(roll);
        return list;
    }
    public Rollos devolverRollo(Bodega bodega){
        Rollos roll=new Rollos();
        roll.setIdrollo(0);
        roll.setCodigo_tela(bodega.getCodigo_tela());
        roll.setIdetela(bodega.getIdBodega());
        roll.setTipo(bodega.getTipo());
        roll.setColor(bodega.getColores());
        roll.setCantidad(1);
        return roll;
    }
    public void pasarRegistro(Corte corte) {
        if (corte != null) {
            accion = "update";
            labelTitulo.setText("Modificar datos del corte");
            txtCorte.setText(corte.getIdcorte());
            lbCodigo.setText(String.valueOf(corte.getIdestilo()));
            fecha.setValue(LocalDate.parse(corte.getFecha_corte()));
            txtCantidad.setText(String.valueOf(corte.getCantidad()));
            lbNombre.setText(corte.getNombre());
            txtRollo=corte.getCant_rollo();
            estado(corte.getEstado());
            btnIngresarCorte.setText("Actualizar corte");
            DataRollos dataRollos=new DataRollos();
            llenarTabla(dataRollos.viewTela(new Rollos(0,corte.getIdcorte(),0,0),"viewxcorte"));
            calcularCantidad();
        }
    }


    public void estado(String estado) {
        if (estado.equals("Activo")) {
            Activo.setSelected(true);
            Noactivo.setSelected(false);
        } else {
            Noactivo.setSelected(true);
            Activo.setSelected(false);
        }
    }

    public void estadoActivo(ActionEvent actionEvent) {
        if (Activo.isSelected()) {
            estado = "Activo";
            Noactivo.setSelected(false);
        }
    }

    public void estadoNoActivo(ActionEvent actionEvent) {
        if (Noactivo.isSelected()) {
            estado = "No Activo";
            Activo.setSelected(false);
        }
    }
    public void registrarCorte(ActionEvent actionEvent) {
        if (returnCorte()!=null){
            DataCorte dataCorte=new DataCorte();
            if (dataCorte.crudCorte(returnCorte(),accion)){
                for (int i=0;i<listTela.size();i++){
                    DataRollos dataRollos=new DataRollos();
                    if (listTela.get(i).getIdrollo()==0) {
                        dataRollos.crudCorte(new Rollos(0, txtCorte.getText(),listTela.get(i).getIdetela(),listTela.get(i).getCantidad()),accion);
                    }else {
                        dataRollos.crudCorte(new Rollos(listTela.get(i).getIdrollo(), txtCorte.getText(),listTela.get(i).getIdetela(),listTela.get(i).getCantidad()),accion);
                    }
                }

                limpiar();
            }
        }
    }
    public void limpiar(){
        txtCorte.setText("");
        lbNombre.setText("");
        lbCodigo.setText("");
        fecha.setValue(null);
        txtCantidad.setText("");
        txtRollo=0;
        listTela.clear();
        tblTela.refresh();
    }
    public Corte returnCorte(){
        Corte corte=new Corte();
        if (txtCorte.getText().isEmpty()){
            Util.Error("Error","El campo Codigo de Corte se encuentra vacio, ingrese algun Codigo");
        }else{
            corte.setIdcorte(txtCorte.getText());
        }

        if (lbCodigo.getText().isEmpty()){
            Util.Error("Error","El campo nombre se encuentra vacio, ingrese el nombre");
            return  null;
        }else {
            corte.setIdestilo(Integer.parseInt(lbCodigo.getText()));
            if (txtCantidad.getText().isEmpty()){
                Util.Error("Error","El campo cantidad se encuentra vacio, ingrese el apellido");
                return  null;
            }else{
                corte.setCantidad(Integer.parseInt(txtCantidad.getText()));
                if (fecha.getValue() == null){
                    Util.Error("Error","El campo Fecha  se encuentra vacío, Seleccione una Fecha");
                    return  null;
                }else{
                    corte.setFecha_corte(fecha.getValue().toString());
                    if (txtRollo==0){
                        Util.Error("Error","El campo Rollo se encuentra vacío, favor de ingresar la cantidad de rollo");
                        return  null;
                    }else{
                        corte.setCant_rollo(txtRollo);
                        corte.setEstado(estado);
                        return corte;

                    }
                }
            }


        }
    }

    public void SeleccionarEStilo(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Operacion/Estilo/Estilo.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            EstiloController controller=fxmlLoader.getController();
            controller.listViewEstilo.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount()==1 && event.getButton()== MouseButton.PRIMARY){
                        Estilo estilo=controller.listViewEstilo.getSelectionModel().getSelectedItem();
                        llenarEstilo(estilo);
                        Stage stage1=(Stage)controller.listViewEstilo.getScene().getWindow();
                        stage1.close();
                    }
                }
            });

            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void llenarEstilo(Estilo estilo){
        estiloSeleccionado=estilo;
        lbEstado2.setText(estiloSeleccionado.getEstado());

        if (lbEstado2.getText().equals("Activo")) {
            lbNombre.setText(estiloSeleccionado.getNombre());
            lbCodigo.setText(String.valueOf(estiloSeleccionado.getCodigo()));
        }else{
            AlertDialog aler=new AlertDialog();
            aler.alert("Estilo no activo","Por favor seleccione un estilo activo");
        }
    }

    public void AbrirBodega(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Bodega/Bodega.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            BodegaController controller =fxmlLoader.getController();
            controller.listBodega.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount()==1 && event.getButton()==MouseButton.PRIMARY){
                        Bodega bodega=controller.listBodega.getSelectionModel().getSelectedItem();
                        if (listTela==null){
                            llenarTabla(devolverBodega(bodega));
                            calcularCantidad();
                            tblTela.refresh();
                        }else{
                            listTela.add(devolverRollo(bodega));
                            calcularCantidad();
                            tblTela.refresh();
                        }
                    }
                    Stage cerrar=(Stage)controller.listBodega.getScene().getWindow();
                    cerrar.close();
                }
            });


        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

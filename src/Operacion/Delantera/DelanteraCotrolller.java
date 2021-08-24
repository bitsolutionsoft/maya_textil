package Operacion.Delantera;

import ClassAux.AjustarTabla;
import Operacion.DAO.DataOperacion;
import Operacion.DAO.Operacion;
import Operacion.FormOperacion;
import Operacion.Delantera.CellDelantera;
import Operacion.Estilo.DAO.Estilo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    public ListView <Operacion> listDelanteras;
    AjustarTabla ajustarTabla=new AjustarTabla();
    ObservableList<Operacion> listDelantera;
    FilteredList<Operacion> filterDelantera;
    private String tipo_operacion="Delantera";

public   int codigo_estilo=0,codigo_tipo=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//iniciarTabla();
        initLista(listDelanteras);
        llenarLista();

    }
    public void initLista(ListView<Operacion> listView){
        DataOperacion dataOperacion=new DataOperacion();
        listDelantera= FXCollections.observableArrayList(dataOperacion.viewOperacion(new Operacion(0,codigo_tipo,codigo_estilo,"",0,0),"viewtipo"));
        filterDelantera=new FilteredList<>(listDelantera, p ->true);
        listView.setItems(filterDelantera);
        listView.setCellFactory(new Callback<ListView<Operacion>, ListCell<Operacion>>() {
            @Override
            public ListCell<Operacion> call(ListView<Operacion> listView) {
                CellDelantera cellDelantera=new CellDelantera();
                return cellDelantera;
            }
        });

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



   /*
    private void eliminar(Operacion operacion){
        if (operacion!=null){
            DataOperacion dataOperacion=new DataOperacion();
            if (dataOperacion.crudOperacion(operacion,"delete")){
                llenarTabla();
            }
        }
    }

    private void modificar(Operacion operacion){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Operacion/FormOperacion.fxml"));
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            FormOperacion formOperacion=loader.getController();
            formOperacion.pasarDatos(operacion,"update",tipo_operacion,codigo_tipo,codigo_estilo);

            stage.setTitle("Maya_textil");
            stage.setOnHiding((windowEvent -> {
                llenarTabla();
            }));
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }*/


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
              initLista(listDelanteras);
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
                initLista(listDelanteras);

                break;
            case "Trasera":
                codigo_tipo=2;
                codigo_estilo=estilo.getCodigo();
                lblTitulo.setText("Operaciones de la Trasera");
                initLista(listDelanteras);
                break;
            case "Ensamble":
                codigo_tipo=3;
                codigo_estilo=estilo.getCodigo();
                lblTitulo.setText("Operaciones del Ensamble");
                initLista(listDelanteras);
                break;
        }

    }
}

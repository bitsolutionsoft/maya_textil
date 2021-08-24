package Bodega;

import Bodega.DAO.Bodega;
import Bodega.DAO.DataBodega;
import Usuario.DAO.DataUsuario;
import Usuario.DAO.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BodegaController implements Initializable {
    public Button btnIngresarNuevo;
    public TextField txtBuscar;
    public ListView <Bodega> listBodega;
    static ObservableList<Bodega> bodega;
    static FilteredList<Bodega> fileterBodega;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initList(listBodega);
        llenarLista();
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
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/Bodega/FormBodega.fxml"));
            Parent parent=loader.load();
            Stage stage=new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            stage.getIcons().add(new Image("/Img/icon.png"));
            stage.setOnHiding((event ->{
                initList(listBodega);
                listBodega.refresh();
            }));


        }catch (IOException e){
            e.printStackTrace();

        }



    }
}

package Corte;

import Corte.DAO.Corte;
import Corte.DAO.DataCorte;
import Corte.DAO.DataRollos;
import Corte.DAO.Rollos;
import Corte.Pdf.imprimir;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CorteController implements Initializable {
    public Button btnIngresarNuevo;
    public ListView<Corte> listCorte;
    public TextField txtBuscar;
    static ObservableList<Corte> cortes;
    static FilteredList<Corte> cortedata;
    static ObservableList<Rollos> listTela;
    static FilteredList<Rollos> filterListTela;
    public ListView<Rollos> listViewTela;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initLista(listCorte);
        llenarListaCorte();
        listCorte.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount()==1 && event.getButton()== MouseButton.PRIMARY){
                    Corte corte=listCorte.getSelectionModel().getSelectedItem();
                    initListaTela(listViewTela,corte);
                    listViewTela.refresh();
                }
            }
        });

    }
    public void initLista(ListView<Corte> listView){
        DataCorte datos=new DataCorte();
        cortes = FXCollections.observableArrayList(datos.viewCorte("viewall"));
        cortedata=new FilteredList<Corte>(cortes,s->true);
        listView.setItems(cortedata);
        //para llenar las filas personalizadas

        listView.setCellFactory(new Callback<ListView<Corte>, ListCell<Corte>>() {
            @Override
            public ListCell<Corte> call(ListView<Corte> param) {
                CorteCell corteCell=new CorteCell();
                return corteCell;
            }
        });

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
    public void initListaTela(ListView<Rollos> listView,Corte corte){
        DataRollos datos=new DataRollos();
        listTela = FXCollections.observableArrayList(datos.viewTela(new Rollos(0,corte.getIdcorte(),0,0),"viewxcorte"));
        filterListTela=new FilteredList<Rollos>(listTela,s->true);
        listView.setItems(filterListTela);
        //para llenar las filas personalizadas

        listView.setCellFactory(new Callback<ListView<Rollos>, ListCell<Rollos>>() {
            @Override
            public ListCell<Rollos> call(ListView<Rollos> param) {
                CellTela cellTela=new CellTela();
                return cellTela;
            }
        });

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
                initLista(listCorte);
                listCorte.refresh();
            }));


        }catch (IOException e){
            e.printStackTrace();

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

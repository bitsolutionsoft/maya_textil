package ClassAux;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.util.concurrent.atomic.AtomicLong;

public class SizeColumnTable {
    public void  ajustarColumna(TableView<?> view){
        AtomicLong ancho= new AtomicLong();
        view.getColumns().forEach(tableColumn -> {
            ancho.addAndGet((long) tableColumn.getWidth());
        });
        double anchoTabla=view.getWidth();
        if (anchoTabla>ancho.get()){
            view.getColumns().forEach(tableColumn -> {
                tableColumn.setPrefWidth(tableColumn.getWidth()+((anchoTabla-ancho.get())/view.getColumns().size()));
            });
        }
    }

    public  void autAjuste(TableView<?> tableView){
        AtomicLong ancho=new AtomicLong();
        tableView.getColumns().forEach(col ->{
            ancho.addAndGet((long) col.getWidth());
        });
        double anchoTabla=tableView.getWidth();
        if (anchoTabla > ancho.get()){
            TableColumn<?,?> col=tableView.getColumns().get(tableView.getColumns().size()-1);
            col.setPrefWidth(col.getWidth()+(anchoTabla - ancho.get()));
        }
    }
    public  static  void autoresize(TableView<?> tableView){
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getColumns().stream().forEach((column)->{
            Text text=new Text(column.getText());
            double max=text.getLayoutBounds().getWidth();
            for (int i=0;i<tableView.getItems().size(); i++){
                if (column.getCellData(i) != null){
                    text=new Text(column.getCellData(i).toString());
                    double calcwidth=text.getLayoutBounds().getWidth();
                    if (calcwidth > max){
                        max=calcwidth;
                    }
                }
            }
            column.setPrefWidth(max+10.0d);
        });
    }
}

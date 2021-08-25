package Pago.Factura;

import ClassAux.AlertDialog;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.collections.map.HashedMap;


import javax.swing.*;
import java.io.File;
import java.io.FilePermission;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class ImprimirVale {

//    C:\Program Files\BitSolutionSoft\Maya Textil\Constancia
    //private final String path="C:"+ File.separator+"Program Files"+File.separator+"BitSolutionSoft"+File.separator+"Maya Textil"+File.separator+"Constancia";
    private final String path="C:"+ File.separator+"Constancia";

    private final String fac="/Pago/Factura/pagos.jasper";
private final  boolean guardar=true;

    public void Constancia(List<ConstanciaPago> lista, float totaloperacion, float descontado, float  totalfinal, String empleado, boolean imprimir){
        File nuevoDirectorio=new File(path);
     nuevoDirectorio.mkdir();
        Map<String,Object> par = new HashMap();
        par.put("subtotalfinal",totaloperacion);
        par.put("descontado",descontado);
        par.put("totalfinal",totalfinal);
        par.put("empleado",empleado);

        par.put("fecha",getFecha());

        try {

            JasperPrint jPrint = JasperFillManager.fillReport(this.getClass().getResourceAsStream(fac), par,
                    new JRBeanCollectionDataSource(lista));

            if(imprimir){
                JasperPrintManager.printReport(jPrint, false);
            }

            JasperViewer jasperViewer = new JasperViewer(jPrint,false);
            jasperViewer.setTitle("Comprobante de pago");
            jasperViewer.setVisible(true);
if (guardar){
    JasperExportManager.exportReportToPdfFile(jPrint, path+File.separator+getFecha()+empleado+".pdf");
}
        } catch (JRException e) {
            e.printStackTrace();

            AlertDialog alerterdialog =new AlertDialog();
            alerterdialog.alert("",""+e);

        }

    }

    private String getFecha(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);

    }
}

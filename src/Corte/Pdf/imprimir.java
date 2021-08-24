package Corte.Pdf;




import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import ClassAux.AlertDialog;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;


    public class imprimir {
        public imprimir() {
        }


    public void imprimirpdf(String url)  {
         final  Logger LOGGER = Logger.getLogger("Maya Textil");
try {
    PDDocument document = Loader.loadPDF(new File(url));
    PrinterJob job = PrinterJob.getPrinterJob();
    LOGGER.log(Level.INFO, "Mostrando el dialogo de impresion");
    if (job.printDialog()) {
        job.setPageable(new PDFPageable(document));

        LOGGER.log(Level.INFO, "Imprimiendo documento");
        job.print();
    }
}catch (IOException | PrinterException e){
    e.printStackTrace();
    AlertDialog alertDialog=new AlertDialog();
    alertDialog.alert("",""+e);
}

    }
}


module maya.textil {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.media;
    requires javafx.swt;
    requires javafx.web;
    requires javafx.swing;

    requires mysql.connector.java;
    requires TrayNotification;
    requires java.desktop;
    requires  pdfbox;
    requires commons.logging;




  requires jasperreports;
  requires jasperreports.fonts;
 // requires jasperreports.javaflow;

    requires commons.beanutils;
    requires commons.digester;
    requires commons.javaflow;
 requires commons.collections;

    requires itextpdf;

    opens Libreria;
    opens Conexion;
    opens Login;
    opens ClassAux;
    opens Img;
    opens css;
    opens Menu;
    opens  Operacion;
    opens  Operacion.DAO;
    opens  Operacion.Delantera;
    opens Operacion.Estilo;
    opens Operacion.Estilo.DAO;

    opens Empleado;
    opens Empleado.DAO;
    opens Usuario;
    opens  Permiso;
    opens  Permiso.DAO;
    opens Corte;
    opens Corte.DAO;
    opens  Bodega;
    opens  Bodega.DAO;
    opens Pago;
    opens Pago.DAO;
    opens Informe;
    opens Informe.DAO;
    opens Corte.Pdf;
    opens Pago.ListCorte;
    opens Pago.Factura;




}
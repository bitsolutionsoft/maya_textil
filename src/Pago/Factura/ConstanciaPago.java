package Pago.Factura;

import Pago.DAO.DetallePago;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConstanciaPago {
    String  nombre;
    float precio, subtotal;
    int cantidad;


    public ConstanciaPago( String nombre, int cantidad, float precio, float subtotal) {

        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.subtotal = subtotal;
    }

    public ConstanciaPago() {
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }


    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }


    public ObservableList<ConstanciaPago> datos(ObservableList<DetallePago> list){
        ObservableList<ConstanciaPago> datos= FXCollections.observableArrayList();
        for (int i=0; i<list.size(); i++){
            if (list.get(i).getEstado().equals("Cancelado")){
            ConstanciaPago modelo=new ConstanciaPago();
         //   modelo.setCodigo(String.valueOf(list.get(i).getIdoperacion()));
            modelo.setNombre(list.get(i).getNombre());
            modelo.setCantidad(list.get(i).getCantidad());
            modelo.setPrecio(list.get(i).getPrecio());
            modelo.setSubtotal(list.get(i).getTotal());
            datos.addAll(modelo);
            }
        }
        return  datos;
    }
}

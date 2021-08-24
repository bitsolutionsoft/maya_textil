package Pago.DAO;

import ClassAux.Util;
import Conexion.Conexion;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataDetallePago {

    public ArrayList<DetallePago> viewDetallePago(DetallePago detalle, String accion){

        ArrayList<DetallePago> lista=new ArrayList<DetallePago>();
        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_detallepago(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            callableStatement.setInt(1,detalle.iddetalle);
            callableStatement.setInt(2,detalle.idpago);
            callableStatement.setInt(3,detalle.idempleado);
            callableStatement.setInt(4,detalle.idoperacion);
            callableStatement.setInt(5,detalle.cantidad);
            callableStatement.setFloat(6,detalle.precio);
            callableStatement.setFloat(7,detalle.total);
            callableStatement.setFloat(8,detalle.descuento);
            callableStatement.setString(9,detalle.estado);
            callableStatement.setString(10,accion);

            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                DetallePago dt=new DetallePago();
                dt.setIddetalle(resultSet.getInt("iddetalle"));
                dt.setIdpago(resultSet.getInt("idpago"));
                dt.setIdempleado(resultSet.getInt("idempleado"));
                dt.setIdoperacion(resultSet.getInt("idoperacion"));
                dt.setCantidad(resultSet.getInt("cantidad"));
                dt.setPrecio(resultSet.getFloat("precio"));
                dt.setDescuento(resultSet.getFloat("descuento"));
                dt.setTotal(resultSet.getFloat("total"));
                dt.setEstado(resultSet.getString("estado"));
                lista.add(dt);
            }

                callableStatement.close();
            conexion.con.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return lista;
    }
    public ArrayList<DetallePago> viewDetallePagoXEmp(DetallePago detalle, String accion){

        ArrayList<DetallePago> lista=new ArrayList<DetallePago>();
        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_detallepago(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            callableStatement.setInt(1,detalle.iddetalle);
            callableStatement.setInt(2,detalle.idpago);
            callableStatement.setInt(3,detalle.idempleado);
            callableStatement.setInt(4,detalle.idoperacion);
            callableStatement.setInt(5,detalle.cantidad);
            callableStatement.setFloat(6,detalle.precio);
            callableStatement.setFloat(7,detalle.total);
            callableStatement.setFloat(8,detalle.descuento);
            callableStatement.setString(9,detalle.estado);
            callableStatement.setString(10,accion);

            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                DetallePago dt=new DetallePago();
                dt.setIddetalle(resultSet.getInt("iddetalle"));
                dt.setIdpago(resultSet.getInt("idpago"));
                dt.setIdempleado(resultSet.getInt("idempleado"));
                dt.setIdoperacion(resultSet.getInt("idoperacion"));
                dt.setNombre(resultSet.getString("nombre"));
                dt.setCantidad(resultSet.getInt("cantidad"));
                dt.setPrecio(resultSet.getFloat("precio"));
                dt.setDescuento(resultSet.getFloat("descuento"));
                dt.setTotal(resultSet.getFloat("total"));
                dt.setEstado(resultSet.getString("estado"));
                dt.setFecha(resultSet.getString("fecha"));
                lista.add(dt);
            }

            callableStatement.close();
            conexion.con.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return lista;
    }
    public boolean crudDetallePago(DetallePago detallepago, String accion){

        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_detallepago(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            callableStatement.setInt(1,detallepago.iddetalle);
            callableStatement.setInt(2,detallepago.idpago);
            callableStatement.setInt(3,detallepago.idempleado);
            callableStatement.setInt(4,detallepago.idoperacion);
            callableStatement.setInt(5,detallepago.cantidad);
            callableStatement.setFloat(6,detallepago.precio);
            callableStatement.setFloat(7,detallepago.descuento);
            callableStatement.setFloat(8,detallepago.total);
            callableStatement.setString(9,detallepago.estado);
            callableStatement.setString(10,accion);

            callableStatement.execute();
            Util.Exito("Operacion","Realizado con exito:");

            callableStatement.close();
            conexion.con.close();
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }



    }
}

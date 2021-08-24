package Pago.DAO;

import ClassAux.Util;
import Conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;

public class DataAdelanto {

    public ArrayList<Adelanto> viewAdelanto(Adelanto adelanto, String accion){


        ArrayList<Adelanto> lista=new ArrayList<Adelanto>();
        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_adelanto(?, ?, ?, ?, ?, ?)}");
            callableStatement.setInt(1,adelanto.idadelanto);
            callableStatement.setInt(2,adelanto.idempleado);
            callableStatement.setFloat(3,adelanto.cantidad);
            callableStatement.setString(4,adelanto.concepto);
            callableStatement.setString(5,adelanto.estado);
            callableStatement.setString(6,accion);

            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                Adelanto ad=new Adelanto();
                ad.setIdadelanto(resultSet.getInt("idadelanto"));
                ad.setIdempleado(resultSet.getInt("idempleado"));
                ad.setCantidad(resultSet.getFloat("cantidad"));
                ad.setConcepto(resultSet.getString("concepto"));
                ad.setFecha(resultSet.getString("fecha"));
                ad.setEstado(resultSet.getString("estado"));
                lista.add(ad);
            }

            callableStatement.close();
            conexion.con.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return lista;

    }
    public boolean crudAdelanto(Adelanto adelanto, String accion){

        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_adelanto(?, ?, ?, ?, ?, ?)}");
            callableStatement.setInt(1,adelanto.idadelanto);
            callableStatement.setInt(2,adelanto.idempleado);
            callableStatement.setFloat(3,adelanto.cantidad);
            callableStatement.setString(4,adelanto.concepto);
            callableStatement.setString(5,adelanto.estado);
            callableStatement.setString(6,accion);

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

    public float totalPago() {
        Conexion conexion =new Conexion();
        Connection connection= conexion.Conexion();
        float codigo=0;
        String sql = "select * from totalapagar";

        try {
            conexion.Conexion();
            Statement statement= connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql);

            while (resultSet.next()) {
                codigo=resultSet.getFloat("total");
            }

            resultSet.close();
            connection.close();
            statement.close();
            conexion.Conexion().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codigo;
    }


}

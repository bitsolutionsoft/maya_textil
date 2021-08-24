package Bodega.DAO;

import ClassAux.Util;
import Conexion.Conexion;


import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataBodega {

    public ArrayList<Bodega> viewBodega(Bodega bdb,String accion){


        ArrayList<Bodega> lista=new ArrayList<Bodega>();
        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_bodega(?, ?, ?, ?, ?, ?,?, ?)}");
            callableStatement.setInt(1,bdb.idBodega);
            callableStatement.setString(2,bdb.codigo_tela);
            callableStatement.setString(3,bdb.tipo);
            callableStatement.setString(4,bdb.colores);
            callableStatement.setInt(5,bdb.cantidad);
            callableStatement.setFloat(6,bdb.precio);
            callableStatement.setFloat(7,bdb.total);
            callableStatement.setString(8,accion);

            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                Bodega bodega=new Bodega();
                bodega.setIdBodega(resultSet.getInt("idbodega"));
                bodega.setCodigo_tela(resultSet.getString("codigo_tela"));
                bodega.setTipo(resultSet.getString("tipo"));
                bodega.setColores(resultSet.getString("colores"));
                bodega.setCantidad(resultSet.getInt("cantidad"));
                bodega.setPrecio(resultSet.getFloat("precio"));
                bodega.setTotal(resultSet.getFloat("total"));
                lista.add(bodega);
            }

            callableStatement.close();
            conexion.con.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return lista;
    }
    public boolean crudBodega(Bodega bdb, String accion){


        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_bodega(?, ?, ?, ?, ?, ?,?, ?)}");
            callableStatement.setInt(1,bdb.idBodega);
            callableStatement.setString(2,bdb.codigo_tela);
            callableStatement.setString(3,bdb.tipo);
            callableStatement.setString(4,bdb.colores);
            callableStatement.setInt(5,bdb.cantidad);
            callableStatement.setFloat(6,bdb.precio);
            callableStatement.setFloat(7,bdb.total);
            callableStatement.setString(8,accion);
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

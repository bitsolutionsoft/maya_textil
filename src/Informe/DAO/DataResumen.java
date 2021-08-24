package Informe.DAO;

import Conexion.Conexion;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataResumen {


    public ArrayList<ResumenCorte> viewCortes(Cortes cortes, String accion){


        ArrayList<ResumenCorte> lista=new ArrayList<>();
        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call informe_corte( ?, ?, ?)}");

            callableStatement.setString(1, cortes.getfInicial());
            callableStatement.setString(2,cortes.getfFinal());
            callableStatement.setString(3,accion);

            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                ResumenCorte resumenCorte=new ResumenCorte();
                resumenCorte.setIdcorte(resultSet.getString("idcorte"));
                resumenCorte.setIdestilo(resultSet.getInt("idestilo"));
                resumenCorte.setNombre(resultSet.getString("nombre"));
                resumenCorte.setCantidad(resultSet.getInt("cantidad"));

                resumenCorte.setFecha_corte(resultSet.getString("fecha_corte"));
                resumenCorte.setCant_rollo(resultSet.getInt("cant_rollo"));
                resumenCorte.setTotal(resultSet.getFloat("total"));
                resumenCorte.setIdpago(resultSet.getInt("idpago"));

                lista.add(resumenCorte);
            }

            callableStatement.close();
            conexion.con.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return lista;
    }






}

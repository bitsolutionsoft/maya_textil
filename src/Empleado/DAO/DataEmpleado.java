package Empleado.DAO;

import ClassAux.Util;
import Conexion.Conexion;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataEmpleado {
    public ArrayList<Empleado> viewEmpleado(Empleado empleado, String accion){


        ArrayList<Empleado> lista=new ArrayList<Empleado>();
        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_empleado(?, ?, ?, ?, ?, ?, ?)}");
            callableStatement.setInt(1,empleado.codigo);
            callableStatement.setString(2,empleado.nombre);
            callableStatement.setString(3,empleado.apellido);
            callableStatement.setString(4,empleado.dpi);
            callableStatement.setInt(5,empleado.telefono);
            callableStatement.setString(6,empleado.estado);
            callableStatement.setString(7,accion);

            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                Empleado emp=new Empleado();
                emp.setCodigo(resultSet.getInt("idempleado"));
                emp.setNombre(resultSet.getString("nombre"));
                emp.setApellido(resultSet.getString("apellido"));
                emp.setDpi(resultSet.getString("dpi"));
                emp.setTelefono(resultSet.getInt("telefono"));
                emp.setEstado(resultSet.getString("estado"));
                lista.add(emp);
            }

            callableStatement.close();
            conexion.con.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return lista;
    }
    public boolean crudEmpleado(Empleado empleado, String accion){


        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_empleado(?, ?, ?, ?, ?, ?,?)}");
            callableStatement.setInt(1,empleado.codigo);
            callableStatement.setString(2,empleado.nombre);
            callableStatement.setString(3,empleado.apellido);
            callableStatement.setString(4,empleado.dpi);
            callableStatement.setInt(5,empleado.telefono);
            callableStatement.setString(6,empleado.estado);
            callableStatement.setString(7,accion);

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

package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    public Connection con =null;
    public  Connection Conexion(){
        try {
            String server="localhost:3306";
            String db="maya_textil";
            String user="root";
            String pass="49390508";
            //String pass="root12345";
            String horario="useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String Sll="&useSSL=false&allowPublicKeyRetrieval=true";
            String conexionstr=String.format("jdbc:mysql://%s/%s?%s%s",server,db,horario,Sll);
            con= DriverManager.getConnection(conexionstr,user,pass);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return con;
    }
}

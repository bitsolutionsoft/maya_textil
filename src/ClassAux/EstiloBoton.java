package ClassAux;



public class EstiloBoton {


    public static String Boton() {
        String boton = "" +
                "-fx-background-color:#edebe9;" +
                "-fx-background-radius:100px;" +
                "-fx-margin:5px 0px 10px 0px;";
        return boton;
    }

    public static String Activo() {
        String Activo = "" +
                "-fx-background-color:#00EB59;" +
                "-fx-text-fill:#ffffff;" +
                "-fx-background-radius:10px;" +
                "-fx-margin:5px 0px 10px 0px;";
        return Activo;
    }

    public static String NoActivo() {
        String Noactivo = "" +
                "-fx-background-color:#F03F37;" +
                "-fx-background-radius:10px;" +
                "-fx-margin:5px 0px 10px 0px;" +
                "-fx-text-fill:#ffffff;";
        return Noactivo;
    }

    public static   String deleteImg () {
        String deleteImg="/Img/delete.png";
        return deleteImg;
    }
    public static   String editImg () {
        String editImg="/Img/edit.png";
        return editImg;
    }
    public static   String accessImg () {
        String accessImg="/Img/access.png";
        return accessImg;
    }




/*
    //estilo de los estados
    public   String Activo="" +
            "-fx-background-color:#00EB59;" +
            "-fx-text-fill:#ffffff;"+

            "-fx-background-radius:10px;"+
            "-fx-margin:5px 0px 10px 0px;"

            ;
    public    String Noactivo="" +
            "-fx-background-color:#F03F37;" +
            "-fx-background-radius:10px;"+
            "-fx-margin:5px 0px 10px 0px;"+
            "-fx-text-fill:#ffffff;"

            ;
    public String deleteImg="/Img/delete.png";
    public String editImg="/Img/edit.png";
    public String accessImg="/Img/access.png";

*/


}

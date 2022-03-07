package ClassAux;

import javafx.scene.image.ImageView;

public class SetBotonIcon {
  public static   int height=25;
  public static   int width=25;
    public static ImageView icono(String url){
        ImageView imageView = new ImageView(url);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return imageView;
    }
    public static  ImageView ImgDelete(){
        ImageView imageView = new ImageView("/Img/delete.png");
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return imageView;
    }
    public static  ImageView ImgUpdate(){
        ImageView imageView = new ImageView("/Img/edit.png");
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return imageView;
    }
    public static  ImageView ImgPermision(){
        ImageView imageView = new ImageView("/Img/access.png");
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return imageView;
    }
    public static  ImageView ImgPrint(){
        ImageView imageView = new ImageView("/Img/print.png");
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return imageView;
    }
    public static  ImageView ImgOpen(){
        ImageView imageView = new ImageView("/Img/open.png");
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return imageView;
    }
    public static  ImageView ImgCancel(){
        ImageView imageView = new ImageView("/Img/check.png");
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return imageView;
    }
    public String ButtonStyle(){
        String a="-fx-cursor:hand; " +
                "-fx-background-color: transparent;";
        return a;
    }
    public static String Activo() {
        String Activo = "" +
                "-fx-text-fill:#30CF9D;" +
                "-fx-font-weight:bold;";
        return Activo;
    }

    public static String NoActivo() {
        String Noactivo = ""+
                "-fx-text-fill:#D12E33; " +
                "-fx-font-weight:bold;";
        return Noactivo;
    }
    public static String HboxStyle() {
        String style = "-fx-alignment:center";
        return style;
    }
}

package ClassAux;

import javafx.scene.image.ImageView;

public class SetBotonIcon {
    public static ImageView icono(String url){
        ImageView imageView = new ImageView(url);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        return imageView;
    }
}

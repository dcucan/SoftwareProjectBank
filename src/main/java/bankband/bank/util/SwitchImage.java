package bankband.bank.util;
import javafx.scene.image.Image;


public class SwitchImage {

    public Image nameToImage(String name) {
        switch (name) {
            case "Card": return new Image("images/card.png");
            case "Nature": return new Image("images/nature.png");
            case "Gold": return new Image("images/gold.jpg");
        }
        return null;
    }

}

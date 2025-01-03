package ma.CabinetDentaire.presentation.view.themes;

import javax.swing.*;
import java.awt.*;

public class ThemeLight extends Theme {
    @Override
    public Color bgColor() {
        return new Color(239, 255, 252);
    }

    @Override
    public Color darkBgColor() {
        return new Color(200, 212, 209);
    }

    @Override
    public ImageIcon getButtonIcon(String name) {

        return new ImageIcon(ThemeLight.class.getResource("/icons/" + name));
    }

    @Override
    public Color fontColor() {
        return new Color(39,39,39);
    }

    @Override
    public Color fieldsBgColor() {
        return new Color(232, 246, 243);
    }


}

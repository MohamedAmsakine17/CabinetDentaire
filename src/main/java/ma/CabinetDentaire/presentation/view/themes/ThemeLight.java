package ma.CabinetDentaire.presentation.view.themes;

import javax.swing.*;
import java.awt.*;

public class ThemeLight extends Theme {
    @Override
    public Color bgColor() {
        return new Color(255, 255, 255);
    }
    @Override
    public ImageIcon getButtonIcon(String name) {

        return new ImageIcon(ThemeLight.class.getResource("/icons/" + name));
    }
}

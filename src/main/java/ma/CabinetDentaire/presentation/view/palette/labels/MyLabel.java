package ma.CabinetDentaire.presentation.view.palette.labels;

import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import java.awt.*;

public class MyLabel extends JLabel {
    private Theme currentTheme;

    public MyLabel(Theme currentTheme, String text, int fontSize, int isBold) {
        this.currentTheme = currentTheme;

        setText(text);
        setForeground(currentTheme.fontColor());
        setFont(new Font("Arial", isBold, fontSize));
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public void SetText(String text) {
        setText(text);
    }
}

package ma.CabinetDentaire.presentation.view.palette.labels;

import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import java.awt.*;

public class MyLabel extends JLabel {
    private Theme currentTheme;
    private int isBold;
    private String text;
    private int fontSize;

    private String fontName;

    private void _init(){
        setText(text);
        setForeground(currentTheme.fontColor());
        setFont(new Font(fontName, isBold, fontSize));
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public MyLabel(Theme currentTheme, String text, int fontSize, int isBold) {
        this.currentTheme = currentTheme;
        this.isBold = isBold;
        this.text = text;
        this.fontSize = fontSize;
        this.fontName = "Arial";
        _init();
    }

    public void changeTextSize(int newsize){
        setFont(new Font(fontName, isBold, newsize));
    }

    public void changeColor(Color color){
        setForeground(color);
    }
}

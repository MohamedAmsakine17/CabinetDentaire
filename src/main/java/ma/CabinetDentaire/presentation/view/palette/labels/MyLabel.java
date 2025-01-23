package ma.CabinetDentaire.presentation.view.palette.labels;

import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import java.awt.*;

public class MyLabel extends JLabel {
    private Theme currentTheme;
    private int isBold;
    private String text;
    private int fontSize;
    private String iconSrc;
    private String fontName;

    private int iconSize;

    private void _init() {
        setText(text);
        setFont(new Font(fontName, isBold, fontSize));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        if (iconSrc != null) {
            ImageIcon icon = new ImageIcon(iconSrc);
            icon.setImage(icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            setIcon(icon);
            setHorizontalTextPosition(SwingConstants.RIGHT);
            setIconTextGap(15);
        }
    }

    public MyLabel(Theme currentTheme,String iconSrc, int iconSize){
        this.currentTheme = currentTheme;
        this.isBold = 1;
        this.text = "";
        this.fontSize = iconSize;
        this.fontName = "Arial";
        this.iconSrc = iconSrc;
        this.iconSize = iconSize;
        setForeground(currentTheme.fontColor());

        _init();
    }

    public MyLabel(Theme currentTheme, String text, int fontSize, int isBold) {
        this.currentTheme = currentTheme;
        this.isBold = isBold;
        this.text = text;
        this.fontSize = fontSize;
        this.fontName = "Arial";
        this.iconSrc = null;
        setForeground(currentTheme.fontColor());

        _init();
    }

    public MyLabel(Theme currentTheme, String text, int fontSize, int isBold, Color color) {
        this.currentTheme = currentTheme;
        this.isBold = isBold;
        this.text = text;
        this.fontSize = fontSize;
        this.fontName = "Arial";
        this.iconSrc = null;
        setForeground(color);
        _init();
    }

    public MyLabel(Theme currentTheme,String text, int fontSize, int isBold,String iconSrc, int iconSize ) {
        this.currentTheme = currentTheme;
        this.isBold = isBold;
        this.text = text;
        this.fontSize = fontSize;
        this.fontName = "Arial";
        this.iconSrc = iconSrc;
        this.iconSize = iconSize;
        setForeground(currentTheme.fontColor());

        _init();
    }

    public void changeTextSize(int newsize){
        setFont(new Font(fontName, isBold, newsize));
    }

    public void changeColor(Color color){
        setForeground(color);
    }
}

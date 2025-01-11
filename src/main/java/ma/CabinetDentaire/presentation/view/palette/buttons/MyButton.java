package ma.CabinetDentaire.presentation.view.palette.buttons;

import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyButton extends JButton {
    private Theme currentTheme;
    private MyLabel font;
    private String iconSrc, text;
    private Color bgColor,hoverColor;
    private Image img;
    private ImageIcon icon;
    private JLabel iconLabel;

    private void _init(){
        iconLabel = new JLabel();
        iconLabel.setOpaque(false);
        iconLabel.setIcon(new ImageIcon(img));

        add(iconLabel);

        setAlignmentX(Component.CENTER_ALIGNMENT);

        setBackground(bgColor);
        setForeground(currentTheme.fontColor());

        font = new MyLabel(currentTheme,this.text,22,1);
        add(font);

        setBorderPainted(false);
        setMargin(new Insets(5, 30, 5, 30));

        setFocusPainted(false);
        setContentAreaFilled(true);

        setUI(new BasicButtonUI());

        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(bgColor);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                setBackground(hoverColor);
            }
        });
    }

    public MyButton(Theme currentTheme, String iconSrc){
        this.currentTheme = currentTheme;
        this.iconSrc = iconSrc;

        this.bgColor = currentTheme.bgColor();
        this.hoverColor = currentTheme.darkBgColor();

        this.icon = new ImageIcon(iconSrc);
        this.img = icon.getImage().getScaledInstance(42,42, Image.SCALE_SMOOTH);


        _init();
    }

    public MyButton(Theme currentTheme, String text,String iconSrc, Color bgColor, Color hoverColor) {
        this.currentTheme = currentTheme;

        if(text != null)  this.text = text;
        if(iconSrc != null)  this.iconSrc = iconSrc;

        this.bgColor = bgColor;
        this.hoverColor = hoverColor;

        this.icon = new ImageIcon(iconSrc);
        this.img = icon.getImage().getScaledInstance(42,42, Image.SCALE_SMOOTH);

        _init();
    }

    public void changeTextSize(int size){
        font.changeTextSize(size);
    }

    public void changeMargin(int top, int left, int bottom, int right){
        setMargin(new Insets(top, left, bottom, right));
    }

    public void changeImageSize(int size){
        img = icon.getImage().getScaledInstance(size,size, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(img));
    }
}

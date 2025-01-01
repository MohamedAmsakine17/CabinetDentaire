package ma.CabinetDentaire.presentation.view.palette.buttons;

import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyButton extends JButton {
    private Theme currentTheme;
    private Color defaultColor = new Color(70, 130, 180); // Blue
    private Color hoverColor = new Color(30, 80, 150);

    public MyButton(String text,String iconName, Theme currentTheme) {
        this.currentTheme = currentTheme;
        setText(text);

        //setForeground(currentTheme.primaryTextColor());
        //setFont(new Font("Arial", Font.BOLD, 16));

        if (iconName != null) {
            setIcon(currentTheme.getButtonIcon(iconName)); // Load the button icon from the theme
        }


        setForeground(Color.black);
        setBackground(defaultColor);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(true);
        setOpaque(true);
        setPreferredSize(new Dimension(200, 60));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(defaultColor);
            }
        });
    }
}

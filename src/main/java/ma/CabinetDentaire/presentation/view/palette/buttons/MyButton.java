package ma.CabinetDentaire.presentation.view.palette.buttons;

import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyButton extends JButton {
    private Theme currentTheme;

    public MyButton(Theme currentTheme, String text, Color bgColor, Color hoverColor) {
        this.currentTheme = currentTheme;

        setText(text);
        setAlignmentX(Component.CENTER_ALIGNMENT);

        setBackground(bgColor);
        setForeground(currentTheme.fontColor());
        setFont(new Font("Arial", Font.BOLD, 22));

        setBorderPainted(false);
        setMargin(new Insets(10, 50, 10, 50));

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
}

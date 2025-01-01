package ma.CabinetDentaire.presentation.view.palette.fields;

import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PasswordInputField  extends JPasswordField {
    private Theme currentTheme;

    public PasswordInputField(Theme currentTheme, String placeholder, int fontSize) {
        this.currentTheme = currentTheme;

        setFont(new Font("Arial", Font.PLAIN, fontSize));
        setBackground(currentTheme.fieldsBgColor());

        setBorder(new EmptyBorder(5, 20, 5, 5));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        setForeground(Color.GRAY);
        setEchoChar((char) 0); // Disable masking for placeholder
        setText(placeholder);

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getPassword().length > 0) {
                    setText("");
                    setForeground(Color.BLACK); // Change text color to normal
                    setEchoChar('●'); // Enable masking for password
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getPassword().length == 0) {
                    setForeground(Color.GRAY); // Placeholder color
                    setEchoChar((char) 0); // Disable masking for placeholder
                    setText(placeholder);
                }
            }
        });
    }
}

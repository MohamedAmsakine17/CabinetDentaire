package ma.CabinetDentaire.presentation.view.fields;

import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MyInputField extends JTextField {
    private Theme currentTheme;

    public MyInputField(String placeholder, Theme theme) {
        this.currentTheme = theme;

        // Set Placeholder
        setText(placeholder);
        setForeground(Color.GRAY);

        // Apply Theme Styles
        setFont(new Font("Arial", Font.PLAIN, 14));
        //setBackground(currentTheme.inputBackgroundColor());
        //setForeground(currentTheme.inputTextColor());
//        setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(currentTheme.inputBorderColor(), 1),
//                BorderFactory.createEmptyBorder(5, 10, 5, 10)
//        ));

        // Placeholder Behavior
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                    //setForeground(currentTheme.inputTextColor());
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder);
                    setForeground(Color.GRAY);
                }
            }
        });
    }
}

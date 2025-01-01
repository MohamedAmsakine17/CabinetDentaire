package ma.CabinetDentaire.presentation.view.palette.fields;

import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class TextInputField extends JTextField {
    private Theme currentTheme;

    public TextInputField(Theme currentTheme, String placeHolder, int fontSize) {
        this.currentTheme = currentTheme;

        setFont(new Font("Arial", Font.PLAIN, fontSize));
        setBackground(currentTheme.fieldsBgColor());

        setBorder(new EmptyBorder(0, 20, 0, 0));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        setText(placeHolder);

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeHolder)) {
                    setText("");
                    setForeground(Color.BLACK); // Change text color to normal
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setForeground(Color.GRAY); // Placeholder color
                    setText(placeHolder);
                }
            }
        });
    }
}

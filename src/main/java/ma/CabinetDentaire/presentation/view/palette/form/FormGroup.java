package ma.CabinetDentaire.presentation.view.palette.form;

import ma.CabinetDentaire.presentation.view.palette.fields.TextInputField;
import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import java.awt.*;

public class FormGroup extends JPanel {
    private Theme currentTheme;
    private String text, iconSrc;
    private TextInputField inputField;

    private void _init(){
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        setOpaque(false);
        ImageIcon icon = new ImageIcon(iconSrc);
        icon.setImage(icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);

        JPanel inputFieldContainer = new JPanel(new GridLayout(2,1));
        inputFieldContainer.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        inputFieldContainer.setOpaque(false);
        MyLabel label = new MyLabel(currentTheme,text,12,1);
        inputField = new TextInputField(currentTheme,"",14);
        inputFieldContainer.add(label);
        inputFieldContainer.add(inputField);

        add(iconLabel, BorderLayout.LINE_START);
        add(inputFieldContainer, BorderLayout.CENTER);
    }

    public FormGroup(Theme currentTheme, String text, String iconSrc){
        this.currentTheme = currentTheme;
        this.text = text;
        this.iconSrc = iconSrc;
        _init();
    }

    public TextInputField getInputField(){
        return inputField;
    }
}

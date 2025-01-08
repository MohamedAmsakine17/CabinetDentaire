package ma.CabinetDentaire.presentation.view.palette.form;

import ma.CabinetDentaire.presentation.view.palette.fields.TextInputField;
import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class SelectFormGroup extends JPanel {
    private Theme currentTheme;
    private String text, iconSrc;
    private String[] options;
    private JComboBox<String> selectField;

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

        selectField = new JComboBox<>(options);
        selectField.setSelectedIndex(0);
        selectField.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton arrowButton = super.createArrowButton();

                // Change the size of the dropdown button
                arrowButton.setPreferredSize(new Dimension(30, 30));

                // Set background color for the dropdown button
                arrowButton.setBackground(currentTheme.greenColor());
                arrowButton.setOpaque(false);
                arrowButton.setBorder(BorderFactory.createEmptyBorder());

                return arrowButton;
            }
        });
        selectField.setBackground(currentTheme.fieldsBgColor());
        selectField.setBorder(new EmptyBorder(0, 20, 0, 0));

        inputFieldContainer.add(label);
        inputFieldContainer.add(selectField);

        add(iconLabel, BorderLayout.LINE_START);
        add(inputFieldContainer, BorderLayout.CENTER);
    }

    public SelectFormGroup(Theme currentTheme, String text, String iconSrc, String[] options){
        this.currentTheme = currentTheme;
        this.text = text;
        this.iconSrc = iconSrc;
        this.options = options;
        _init();
    }

    public JComboBox<String> getSelectField(){
        return selectField;
    }
}

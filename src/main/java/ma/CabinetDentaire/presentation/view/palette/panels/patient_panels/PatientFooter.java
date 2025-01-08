package ma.CabinetDentaire.presentation.view.palette.panels.patient_panels;

import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import java.awt.*;

public class PatientFooter extends JPanel {
    private Theme currentTheme;
    private String text;
    private Color bgColor;
    private Integer value;

    private void _init(){
        setPreferredSize(new Dimension(200, 100));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(bgColor);
        setLayout(new BorderLayout());

        MyLabel valueLabel = new MyLabel(currentTheme,value.toString(),50,1);
        valueLabel.setForeground(currentTheme.whiteColor());

        MyLabel textLabel = new MyLabel(currentTheme,text,16,1);

        add(valueLabel, BorderLayout.NORTH);
        add(textLabel, BorderLayout.SOUTH);
    }

    public PatientFooter(Theme currentTheme, String text, Color bgColor,Integer value){
        this.currentTheme = currentTheme;
        this.text = text;
        this.bgColor = bgColor;
        this.value = value;
        _init();
    }
}

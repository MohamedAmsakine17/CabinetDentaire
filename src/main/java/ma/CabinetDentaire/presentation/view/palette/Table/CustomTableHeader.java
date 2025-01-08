package ma.CabinetDentaire.presentation.view.palette.Table;

import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import java.awt.*;

public class CustomTableHeader extends JPanel {
    private Theme currentTheme;
    private final String[] ColNames;

    private void _init(){
        setLayout(new GridLayout(1,ColNames.length));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, currentTheme.darkBgColor()), // Green bottom border
                BorderFactory.createEmptyBorder(10, 0, 10, 10) // Padding
        ));
        setBackground(currentTheme.bgColor());

        for (int i = 0; i < ColNames.length; i++){
            add(new MyLabel(currentTheme,ColNames[i],18,1));
        }
    }

    public CustomTableHeader(Theme currentTheme, String[] ColNames) {
        this.currentTheme = currentTheme;
        this.ColNames = ColNames;
        _init();
    }
}

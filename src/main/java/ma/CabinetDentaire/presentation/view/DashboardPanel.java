package ma.CabinetDentaire.presentation.view;

import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.palette.panels.dashboard_panels.DashboardHeader;
import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private Theme currentTheme;

    private void _init(){
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        MyLabel title = new MyLabel(currentTheme,"Dashboard",24,1);
        add(title, BorderLayout.NORTH);

        DashboardHeader header = new DashboardHeader(currentTheme);
        add(header, BorderLayout.AFTER_LAST_LINE);
    }

    public DashboardPanel(Theme currentTheme) {
        this.currentTheme = currentTheme;
        _init();
    }
}

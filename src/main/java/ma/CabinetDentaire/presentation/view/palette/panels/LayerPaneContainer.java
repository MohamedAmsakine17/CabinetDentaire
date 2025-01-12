package ma.CabinetDentaire.presentation.view.palette.panels;

import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import java.awt.*;

public class LayerPaneContainer extends JLayeredPane {
    Theme currentTheme;
    JPanel panel;

    private void init(){
        setPreferredSize(new Dimension(1066, 70));
        setLayout(null);

        SearchBar searchBar = new SearchBar(currentTheme);
        searchBar.setBounds(0, 0, 1066, 768);

        add(searchBar, JLayeredPane.PALETTE_LAYER);
    }

    public LayerPaneContainer(Theme currentTheme) {
        this.currentTheme = currentTheme;
        init();
    }
}

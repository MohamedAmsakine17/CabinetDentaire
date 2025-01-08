package ma.CabinetDentaire.presentation.view.util;

import ma.CabinetDentaire.presentation.view.palette.buttons.MyButton;
import ma.CabinetDentaire.presentation.view.palette.fields.TextInputField;
import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.function.Consumer;

public class SearchBar extends JPanel {
    private Theme currentTheme;
    Consumer<String> onSearch;

    private void _init(){
        setBackground(currentTheme.greenColor());
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        TextInputField searchField = new TextInputField(currentTheme,"Rechercher un patient ...",16);
        searchField.setBorder(new EmptyBorder(5, 15, 5, 15));
        searchField.setPreferredSize(new Dimension(325, 40));
        searchField.setBackground(currentTheme.greenHoverColor());
        searchField.setForeground(currentTheme.whiteColor());
        searchField.setFontBold(1);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onSearch.accept(searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onSearch.accept(searchField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onSearch.accept(searchField.getText());
            }
        });

        MyButton menuBtn = new MyButton(currentTheme,"src/main/resources/images/menu_icon.png");
        menuBtn.setOpaque(false);
        menuBtn.changeImageSize(28);

        add(menuBtn, BorderLayout.LINE_END);
        add(searchField, BorderLayout.LINE_START);

    }

    public SearchBar(Theme currentTheme, Consumer<String> onSearch) {
        this.currentTheme = currentTheme;
        this.onSearch = onSearch;

        _init();
    }
}

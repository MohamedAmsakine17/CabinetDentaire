package ma.CabinetDentaire.presentation.view.palette.panels;

import ma.CabinetDentaire.presentation.view.palette.buttons.MyButton;
import ma.CabinetDentaire.presentation.view.palette.fields.TextInputField;
import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;

public class SearchBar extends JPanel {
    private Theme currentTheme;
    //Consumer<String> onSearch;
    private JPanel resultPanel;

    private void _init(){
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(currentTheme.greenColor());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        searchPanel.setPreferredSize(new Dimension(325, 70));


        TextInputField searchField = new TextInputField(currentTheme,"Rechercher un patient ...",16);
        searchField.setBorder(new EmptyBorder(5, 15, 5, 15));
        searchField.setPreferredSize(new Dimension(325, 40));
        searchField.setBackground(currentTheme.greenHoverColor());
        searchField.setForeground(currentTheme.whiteColor());
        searchField.setFontBold(1);

        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setOpaque(false);
        resultPanel.setBorder(new EmptyBorder(0, 15, 0, 0));
        //resultPanel.setBorder(BorderFactory.createLineBorder(currentTheme.whiteColor(), 1));
        resultPanel.setVisible(false);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            /*@Override
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
            }*/
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateResults(searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateResults(searchField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateResults(searchField.getText());
            }
        });

        MyButton menuBtn = new MyButton(currentTheme,"src/main/resources/images/menu_icon.png");
        menuBtn.setOpaque(false);
        menuBtn.changeImageSize(28);
        searchPanel.add(menuBtn, BorderLayout.LINE_END);
        searchPanel.add(searchField, BorderLayout.LINE_START);

        add(searchPanel,BorderLayout.NORTH);
        add(resultPanel,BorderLayout.CENTER);
    }

    private void updateResults(String query) {
        resultPanel.removeAll();

        if (query.isEmpty()) {
            resultPanel.setVisible(false);
        } else {
            // Simulate results (you can replace this with actual search logic)
            List<String> results = List.of("Patient 1", "Patient 2", "Patient 3");
            results.stream()
                    .filter(result -> result.toLowerCase().contains(query.toLowerCase()))
                    .forEach(result -> {
                        MyLabel resultLabel = new MyLabel(currentTheme,result,16,0);
                        resultLabel.setOpaque(true);
                        resultLabel.setBackground(currentTheme.bgColor());
                        resultLabel.setBorder(new EmptyBorder(5, 10, 5, 266));
                        resultLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                        resultPanel.add(resultLabel);
                    });

            resultPanel.setVisible(true);
        }

        // Refresh the result panel
        resultPanel.revalidate();
        resultPanel.repaint();
    }

    public SearchBar(Theme currentTheme) {
        this.currentTheme = currentTheme;
        //this.onSearch = onSearch;

        _init();
    }
}

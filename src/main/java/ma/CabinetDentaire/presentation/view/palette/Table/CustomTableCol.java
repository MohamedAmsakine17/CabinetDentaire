package ma.CabinetDentaire.presentation.view.palette.Table;

import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomTableCol extends JPanel {
    private Theme currentTheme;
    private String[] dataContainer;

    private void _init(){
        setLayout(new GridLayout(1,dataContainer.length));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, currentTheme.darkBgColor()), // Green bottom border
                BorderFactory.createEmptyBorder(10, 0, 10, 10) // Padding
        ));
        setBackground(currentTheme.bgColor());

        for (int i = 0; i < dataContainer.length; i++){
            if(i == 0){
                ImageIcon icon = new ImageIcon("src/main/resources/images/patient_pfp/" + dataContainer[i]);
                icon.setImage(icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
                JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
                add(iconLabel);

                continue;
            }
            add(new MyLabel(currentTheme,dataContainer[i],16,0));
        }

        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(currentTheme.greenColor());
                setLabelsForeground(currentTheme.whiteColor());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(currentTheme.bgColor());
                setLabelsForeground(currentTheme.fontColor());
            }
        });
    }

    private void setLabelsForeground(Color color) {
        for (Component component : getComponents()) {
            if (component instanceof JLabel) {
                component.setForeground(color);
            }
        }
    }


    public CustomTableCol(Theme currentTheme, String[] dataContainer) {
        this.currentTheme = currentTheme;
        this.dataContainer = dataContainer;

        _init();
    }
}

package ma.CabinetDentaire.presentation.view.themes;

import javax.swing.*;
import java.awt.*;

public abstract class Theme {
    public abstract Color bgColor();
    public abstract Color darkBgColor();
    public abstract ImageIcon getButtonIcon(String name);
    public abstract Color fontColor();
    public abstract Color fieldsBgColor();

    // Colors
    public  Color greenColor(){ return new Color(80,216,144); }
    public  Color redColor(){ return new Color(217,93,80); }
}

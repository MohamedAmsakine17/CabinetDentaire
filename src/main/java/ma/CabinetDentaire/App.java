package ma.CabinetDentaire;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.presentation.view.LoginView;
import ma.CabinetDentaire.presentation.view.themes.ThemeLight;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import javax.swing.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        SwingUtilities.invokeLater( () -> {
            try {
                new LoginView(new ThemeLight());
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

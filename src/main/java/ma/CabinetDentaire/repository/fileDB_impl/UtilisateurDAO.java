package ma.CabinetDentaire.repository.fileDB_impl;

import ma.CabinetDentaire.entities.Patient;
import ma.CabinetDentaire.entities.Utilisateur;
import ma.CabinetDentaire.repository.CRUDRepository;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO implements CRUDRepository<Utilisateur, Integer> {
    private static final String CREDENTIALS_FILE = "src/main/data/credentials.txt";
    private static final File SESSION_FILE = new File("src/main/data/session.txt");

    public boolean authenticate(String username, String password) {
        List<Utilisateur> users = findAll();
        for (Utilisateur user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true; // User authenticated successfully
            }
        }
        return false; // Authentication failed
    }

    public boolean isSessionSaved() {
        try (BufferedReader reader = Files.newBufferedReader(SESSION_FILE.toPath())) {
            String firstLine = reader.readLine();
            if(firstLine == null) {
                return false;
            }
            return firstLine.equals("SessionSaved");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveSession() throws DaoException {
        try {
            Files.writeString(
                    SESSION_FILE.toPath(),
                    "SessionSaved",
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new DaoException(e);
        }
    }

    public void deleteSession() throws DaoException {
        try {
            Files.writeString(
                    SESSION_FILE.toPath(),
                    ""
            );
        } catch (IOException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Utilisateur> findAll() {
        List<Utilisateur> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 2) {
                    users.add(new Utilisateur(credentials[0], credentials[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Utilisateur findById(Integer integer) {
        return null;
    }

    //@Override
    //public Optional<Utilisateur> findById(Integer integer) {
    //    return Optional.empty();
    //}

    @Override
    public Utilisateur save(Utilisateur element) {
        return null;
    }

    @Override
    public void update(Utilisateur element) {
    }

    @Override
    public void delete(Utilisateur element) {
    }

    @Override
    public void deleteById(Integer integer) {
    }
}

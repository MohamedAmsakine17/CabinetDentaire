package ma.CabinetDentaire.repository.exceptions;

public class DaoException extends Exception{
    public DaoException(String message) {
        super(message);
    }

    public DaoException(Exception e) {
        super(e);
    }
}

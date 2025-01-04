package ma.CabinetDentaire.repository;

import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository <T,ID>{
    List<T> findAll()       throws DaoException;

    T findById(ID id)       throws DaoException;

    T save(T element)       throws DaoException;

    void update(T element)  throws DaoException;

    void delete(T element)  throws DaoException;

    void deleteById(ID id)  throws DaoException;
}

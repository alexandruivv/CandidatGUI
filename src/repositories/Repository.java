package repositories;

import exceptions.ValidatorException;

import java.util.Optional;

public interface Repository<Id, T> {
    T save(T elem) throws ValidatorException;
    T delete(Id id);
    T update(T elem) throws ValidatorException;
    T getOne(int poz);
    Iterable<T> getAll();
}

package repositories;

import domain.hasId;
import exceptions.ValidatorException;
import validators.Validator;

import java.util.Optional;

public abstract class FileRepository<Id, T extends hasId<Id>> extends InMemoryRepository<Id, T> {

    String filePath;

    public FileRepository(String filePath, Validator validator) {
        super(validator);
        this.filePath = filePath;
        readFromFile();
    }

    public abstract void readFromFile();
    public abstract void writeToFile();

    @Override
    public T save(T elem) throws ValidatorException {
        T saved = super.save(elem);
        if(saved == null){
            writeToFile();
        }
        return saved;
    }

    @Override
    public T delete(Id id){
        T deleted = super.delete(id);
        if(deleted != null){
            writeToFile();
        }
        return deleted;
    }

    @Override
    public T update(T elem) throws ValidatorException {
        T updated = super.update(elem);
        if(updated == null){
            writeToFile();
        }
        return updated;
    }
}

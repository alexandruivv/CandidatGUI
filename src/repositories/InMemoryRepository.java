package repositories;

import domain.hasId;
import exceptions.ValidatorException;
import validators.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryRepository<Id, T extends hasId<Id>> implements Repository<Id, T> {
    private List<T> list;
    private Validator<T> validator;

    public InMemoryRepository(Validator validator){
        list = new ArrayList<T>();
        this.validator = validator;
    }

    @Override
    public T save(T elem) throws ValidatorException {
        validator.validate(elem);
        if(list.contains(elem)){
            return list.get(list.indexOf(elem));
        }
        list.add(elem);
        return null;
    }

    @Override
    public T delete(Id id) {
        for(T elem : list){
            if(elem.getId().equals(id)){
                T deleted = list.get(list.indexOf(elem));
                list.remove(elem);
                return deleted;
            }
        }
        return null;
    }

    @Override
    public T update(T elem) throws ValidatorException {
        validator.validate(elem);
        if(!list.contains(elem)){
            return elem;
        }
        list.set(list.indexOf(elem), elem);
        return null;
    }

    @Override
    public T getOne(int poz) {
        return null;
    }


    @Override
    public Iterable<T> getAll() {
        return this.list;
    }
}

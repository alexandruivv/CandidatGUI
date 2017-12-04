package validators;

import exceptions.ValidatorException;

public interface Validator<T> {
    void validate(T elem) throws ValidatorException;
}

package repositories;

import domain.Candidate;
import validators.Validator;

public class CandidateInMemoryRepo extends InMemoryRepository<Integer, Candidate> {
    public CandidateInMemoryRepo(Validator validator) {
        super(validator);
    }
}

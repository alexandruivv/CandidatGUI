package services;

import domain.Candidate;
import exceptions.ValidatorException;
import repositories.Repository;
import repositories.Repository;
import utils.ListEvent;
import utils.ListEventType;
import utils.Observable;
import utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CandidateService extends Observable<Candidate>{
    private Repository<Integer, Candidate> candidateRepo;
    ArrayList<Observer<Candidate>> CandidateObservers=new ArrayList<>();

    public CandidateService(Repository<Integer, Candidate> repo) {
        candidateRepo = repo;
    }

    /**
     * Saves a candidate to store. The observer is notified by an event ADD type
     * @param t - the candidate to be saved
     * @return the candidate saved / null
     * @throws ValidatorException - the candidate is not valid to be saved
     */
    public Candidate saveCandidate(Candidate t) throws ValidatorException {
        Candidate r=candidateRepo.save(t);
        if (r==null) {
            ListEvent<Candidate> ev = createEvent(ListEventType.ADD, r, candidateRepo.getAll());
            super.notifyObservers(ev);
        }
        return r;
    }

    /**
     * Deletes a candidate from the store. The observer is notified by an event REMOVE type
     * @param id - the id of the candidate to be deleted
     * @return the candidate deleted / null
     */
    public Candidate deleteCandidate(int id) {
        Candidate r=candidateRepo.delete(id);
        if (r!=null) {
            ListEvent<Candidate> ev = createEvent(ListEventType.REMOVE, r, candidateRepo.getAll());
            super.notifyObservers(ev);
        }
        return r;
    }

    /**
     * Updates a candidate from the store. The observer is notified by an event of UPDATE type
     * @param t - the candidate to be updated
     * @return the candidate updated / null
     * @throws ValidatorException - the candidate t is not valid to update
     */
    public Candidate updateCandidate(Candidate t) throws ValidatorException {
        Candidate r=candidateRepo.update(t);
        if (r==null) {
            ListEvent<Candidate> ev = createEvent(ListEventType.UPDATE, r, candidateRepo.getAll());
            super.notifyObservers(ev);
        }
        return r;
    }

    /**
     * Collects all the candidates from the store
     * @return list of candidates
     */
    public List<Candidate> getAllCandidates()
    {
        return StreamSupport.stream(candidateRepo.getAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Creates an event of a type, an entity and a list of entities
     * @param type - the type given
     * @param elem - the entity
     * @param l - the list of entities
     * @return listEvent
     */
    private <E> ListEvent<E> createEvent(ListEventType type, final E elem, final Iterable<E> l){
        return new ListEvent<E>(type) {
            @Override
            public Iterable<E> getList() {
                return l;
            }
            @Override
            public E getElement() {
                return elem;
            }
        };
    }
}

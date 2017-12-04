package utils;

import java.util.ArrayList;
import java.util.List;

public class Observable<E> {
    private List<Observer<E>> obs;

    public Observable(){
        obs = new ArrayList<Observer<E>>();
    }
    /**
     * Register an observer.
     * @param o the observer
     */
    public void addObserver(Observer<E> o){
        obs.add(o);
    }
    /**
     * Unregister an observer.
     * @param o the observer
     */
    public void removeObserver(Observer<E> o){
        obs.remove(o);
    }

    /**
     * Notify each observer about an event
     * @param event - the event given
     */
    public void notifyObservers(ListEvent<E> event){
        for(Observer o: obs){
            o.notifyEvent(event);
        }
    }
}

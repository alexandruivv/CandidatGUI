package utils;

public interface Observer<T> {
    void notifyEvent(ListEvent<T> event);
}

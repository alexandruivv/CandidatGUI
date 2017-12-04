package utils;

public abstract class ListEvent<T> {

    private ListEventType type;
    public ListEvent(ListEventType type){
        this.type = type;
    }

    public ListEventType getType(){
        return this.type;
    }

    public void setType(ListEventType type){
        this.type = type;
    }

    public abstract Iterable<T> getList();
    public abstract T getElement();
}

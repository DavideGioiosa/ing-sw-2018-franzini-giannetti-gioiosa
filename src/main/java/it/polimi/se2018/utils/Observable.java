package it.polimi.se2018.utils;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {
    private final List<Observer<T>> observerList = new ArrayList<>();

    public void register(Observer<T> observer){
        synchronized (observerList) {
            observerList.add(observer);
        }
    }

    public void deregister(Observer<T> observer){
        synchronized (observerList) {
            observerList.remove(observer);
        }
    }

    public void notify(T message){
        synchronized (observerList) {
            for(Observer<T> observer : observerList){
                observer.update(message);
            }
        }
    }

    public synchronized void addObserver(Observer o) {
        if (o == null)
            throw new NullPointerException();
        if (!observerList.contains(o)) {
            observerList.add(o);
        }
    }
}

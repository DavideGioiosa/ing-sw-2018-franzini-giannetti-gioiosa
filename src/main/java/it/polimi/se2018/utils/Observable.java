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

    protected void notify(T message){
        synchronized (observerList) {
            for(Observer<T> observer : observerList){
                observer.update(message);
            }
        }
    }

}

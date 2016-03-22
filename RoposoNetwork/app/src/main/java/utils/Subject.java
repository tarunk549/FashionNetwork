package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kumartarun on 22/03/16.
 */
public class Subject {

    private List<Observer> observers = new ArrayList<Observer>();

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void notify(String notificationString) {
        for(Observer observer : observers)
            observer.onNotify(notificationString);
    }
}

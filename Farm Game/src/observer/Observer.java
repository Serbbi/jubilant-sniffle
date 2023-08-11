package observer;

import java.util.HashSet;
import java.util.Set;

public class Observer {
    private Set<Observable> observables;

    public Observer() {
        observables = new HashSet<>();
    }

    public void addObservable(Observable observable) {
        observables.add(observable);
    }

    public void notifyObservers() {
        observables.forEach(Observable::update);
    }
}

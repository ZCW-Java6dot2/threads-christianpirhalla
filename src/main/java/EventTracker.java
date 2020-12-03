import java.util.HashMap;
import java.util.Map;

public class EventTracker implements Tracker {

    private static EventTracker INSTANCE = new EventTracker();

    private Map<String, Integer> tracker;

    private EventTracker() {
        this.tracker = new HashMap<>();
    }


    synchronized public static EventTracker getInstance() {
        return INSTANCE;
    }

    synchronized public void push(String message) {
        tracker.put(message, tracker.getOrDefault(message, 0) + 1);
    }

    synchronized public Boolean has(String message) {
        return (tracker.getOrDefault(message, 0) > 0);
    }

    synchronized public void handle(String message, EventHandler e) {
        e.handle();
        try {
            tracker.put(message,
                    tracker.get(message) - 1);
        }
        catch (NullPointerException n){
            System.out.println("Message is not currently tracked.");
        }
    }

    @Override
    public Map<String, Integer> tracker() {
        return tracker;
    }

    // Do not use this. This constructor is for tests only
    // Using it breaks the singleton class
    EventTracker(Map<String, Integer> tracker) {
        this.tracker = tracker;
    }
}

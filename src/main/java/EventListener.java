import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.w3c.dom.ls.LSOutput;

import java.sql.SQLOutput;

public class EventListener extends Thread{

    private String messageToListenFor;
    private String messageToReplyWith;
    private Tracker eventTracker;

    public EventListener(String message, String reply) {
        this.messageToListenFor = message;
        this.messageToReplyWith = reply;
        this.eventTracker = EventTracker.getInstance();
    }

    public EventListener(String message, String reply, Tracker tracker) {
        this.messageToListenFor = message;
        this.messageToReplyWith = reply;
        this.eventTracker = tracker;
    }

    public void run() {
        while (!readyToQuit()){
            if (shouldReply()) {
                reply();
            }
        }
    }

    public boolean readyToQuit() {
        return eventTracker.has("quit");
    }

    public boolean shouldReply() {
        return eventTracker.has(messageToListenFor);
    }

    public void reply() {
        class Handler implements EventHandler{
            String message;
            Handler(String message){
                this.message = message;
            }
            @Override
            public void handle() {
                System.out.println(message);
            }
        }
        eventTracker.handle(messageToListenFor, new Handler(messageToReplyWith));
    }
}
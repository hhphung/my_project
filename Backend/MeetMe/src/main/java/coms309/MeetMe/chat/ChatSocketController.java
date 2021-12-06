package coms309.MeetMe.chat;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import coms309.MeetMe.Meeting.Meeting;
import coms309.MeetMe.Meeting.Meeting.*;
import coms309.MeetMe.Meeting.MeetingController;
import coms309.MeetMe.Meeting.MeetingRepository;
import coms309.MeetMe.Meeting.MeetingShadow;
import coms309.MeetMe.User.User;
import coms309.MeetMe.User.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller      // this is needed for this to be an endpoint to springboot
@ServerEndpoint(value = "/chat/{meetname}/{username}")  // this is Websocket url
public class ChatSocketController {

    // cannot autowire static directly (instead we do it by the below
    // method
    private static MessageRepository msgRepo;

    private static UserRepository userRepository;

    private static MeetingController controller;

    private static MeetingRepository meetingRepository;

    /*
     * Grabs the MessageRepository singleton from the Spring Application
     * Context.  This works because of the @Controller annotation on this
     * class and because the variable is declared as static.
     * There are other ways to set this. However, this approach is
     * easiest.
     */
    @Autowired
    public void setMessageRepository(MessageRepository repo) {
        msgRepo = repo;  // we are setting the static variable
    }

    @Autowired
    public void setUserRepositoryRepository(UserRepository repo) {
        userRepository = repo;  // we are setting the static variable
    }

    @Autowired
    public void setMeetingRepository(MeetingRepository repo) {
        meetingRepository = repo;  // we are setting the static variable
    }

    @Autowired
    public void setMeetingController(MeetingController repo) {
        controller = repo;  // we are setting the static variable
    }

    private static User user;

    private static Meeting meet;

    private static boolean valid = false;

    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(ChatSocketController.class);




    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username,@PathParam("meetname") String meetname)
            throws IOException {

        int i = 0;
        user = userRepository.findByName(username);
        meet  = meetingRepository.findByName(meetname.replace('_', ' '));

        if(meet.getUserParticipants().contains(username)){
            valid = true;
        }



        if(valid == true) {
            logger.info("Entered into Open");

            // store connecting user information
            sessionUsernameMap.put(session, username);
            usernameSessionMap.put(username, session);

            //Send chat history to the newly connected user
            sendMessageToPArticularUser(username, getChatHistory());

            // broadcast that new user joined
            String message = "User:" + username + " has Joined the Chat";
            broadcast(message);
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {

        // Handle new messages
        if(valid == true) {
            logger.info("Entered into Message: Got Message:" + message);
            String username = sessionUsernameMap.get(session);
            // Direct message to a user using the format "@username <message>"
            if (message.startsWith("@")) {
                String destUsername = message.split(" ")[0].substring(1);

                // send the message to the sender and receiver
                sendMessageToPArticularUser(destUsername, "[DM] " + username + ": " + message);
                sendMessageToPArticularUser(username, "[DM] " + username + ": " + message);

            } else { // broadcast
                broadcast(username + ": " + message);
            }

            // Saving chat history to repository
            msgRepo.save(new Message(user, meet, message));
        }
    }


    @OnClose
    public void onClose(Session session) throws IOException {
        if (valid){
            logger.info("Entered into Close");
            // remove the user connection information
            String username = sessionUsernameMap.get(session);
            sessionUsernameMap.remove(session);
            usernameSessionMap.remove(username);

            // broadcase that the user disconnected
            String message = username + " disconnected";
            broadcast(message);
        }
    }


    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("Entered into Error");
        throwable.printStackTrace();
    }


    private void sendMessageToPArticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        }
        catch (IOException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }


    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            }
            catch (IOException e) {
                logger.info("Exception: " + e.getMessage().toString());
                e.printStackTrace();
            }

        });

    }


    // Gets the Chat history from the repository
    private String getChatHistory() {
        List<Message> messages = msgRepo.findByMeetingId(meet.getId());


        // convert the list to a string

        if (valid == true) {

            StringBuilder sb = new StringBuilder();
            if (messages != null && messages.size() != 0) {
                for (Message message : messages) {
                    sb.append(message.getUser().getName() + ": " + message.getContent() + "\n");
                }
            }
            return sb.toString();
        }
        else{
            return "";
        }
    }


}

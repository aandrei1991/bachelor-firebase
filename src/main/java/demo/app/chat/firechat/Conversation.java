package demo.app.chat.firechat;

import java.util.ArrayList;

public class Conversation {

    private String conversationId;
    private ArrayList<Message> messages;

    public Conversation() {

    }

    public Conversation(String conversationId, ArrayList<Message> messages) {
        this.conversationId = conversationId;
        this.messages = messages;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Conversation(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}

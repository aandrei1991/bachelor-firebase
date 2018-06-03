package demo.app.chat.firechat;

import java.util.Date;

public class Message {

    private String senderUID;
    private String receiverUID;
    private String text;
    private long timestamp;

    public Message(String senderUID, String receiverUID, String text) {
        this.senderUID = senderUID;
        this.receiverUID = receiverUID;
        this.text = text;
        this.timestamp = new Date().getTime();
    }

    public String getSenderUID() {
        return senderUID;
    }

    public void setSenderUID(String senderUID) {
        this.senderUID = senderUID;
    }

    public String getReceiverUID() {
        return receiverUID;
    }

    public void setReceiverUID(String receiverUID) {
        this.receiverUID = receiverUID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

package models;

import play.db.jpa.Model;

import java.util.Date;

public class Message extends Model {
    public Date timestamp;
    public String network;
    public String messageId;
    public String code;
    public String type;
    public String body;

    public Message(Date timestamp, String network, String messageId, String code, String type, String body) {
        this.timestamp = timestamp;
        this.network = network;
        this.messageId = messageId;
        this.code = code;
        this.type = type;
        this.body = body;
    }
}

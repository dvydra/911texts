package models;

import siena.*;

import java.util.Date;

@Table("message")
public class Message extends Model {
    @Id public Long id;
    @Column("timestamp") public Date timestamp;
    @Column("network") public String network;
    @Column("messageId") public String messageId;
    @Column("code") public String code;
    @Column("type") public String type;
    @Column("body") public String body;

    public Message(Date timestamp, String network, String messageId, String code, String type, String body) {
        this.timestamp = timestamp;
        this.network = network;
        this.messageId = messageId;
        this.code = code;
        this.type = type;
        this.body = body;
    }

    public static Query<Message> all() {
        return Model.all(Message.class);
    }


    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                '}';
    }
}

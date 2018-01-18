import java.time.LocalDateTime;

public class Message {
    private String sender;
    private String receiver = null;
    private String content;
    private LocalDateTime sendTime;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    // 构造方法
    Message(String content, String sender, String receiver, LocalDateTime sendTime) {
        // 好友间消息
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.sendTime = sendTime;
    }

    Message(String content, String sender, LocalDateTime sendTime) {
        // 广播消息，不必记录接收者
        this.content = content;
        this.sender = sender;
        this.sendTime = sendTime;
    }
}

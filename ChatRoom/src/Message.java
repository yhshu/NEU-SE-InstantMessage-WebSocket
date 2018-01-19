import java.time.LocalDateTime;

public class Message {
    private String sender;
    private String receiver = null;
    private String content;
    private LocalDateTime sendTime;
    private int type;
    public final static int LOGIN = 0; // 上线通知
    public final static int NORMAL = 1; // 普通消息
    public final static int QUIT = 2; // 离线通知
    public final static int LIST = 3; // 在线列表


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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    // 构造方法
    Message(String content, String sender, String receiver, LocalDateTime sendTime, int type) {
        // 好友间消息
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.sendTime = sendTime;
        this.type = type;
    }

    Message(String content, String sender, LocalDateTime sendTime, int type) {
        // 广播消息，不必记录接收者
        this.content = content;
        this.sender = sender;
        this.sendTime = sendTime;
        this.type = type;
    }
}

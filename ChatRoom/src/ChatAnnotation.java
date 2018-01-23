/**
 * 在服务器上描述一个客户端连接
 *
 * @author 舒意恒
 */

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@ServerEndpoint(value = "/ChatSocket")
public class ChatAnnotation {
    private Session session; // 与客户端通信的session
    private String nickname;
    private static Set<ChatAnnotation> connections = new HashSet<ChatAnnotation>();

    @OnOpen
    public void start(Session session) throws IOException {
        this.session = session;
        this.nickname = session.getId();
        // 把当前客户端对应的ChatAnnotation对象加入到集合中
        connections.add(this);

        // 广播新用户加入群聊的信息
        String message = String.format("用户 %s %s", nickname, "加入了群聊");
        broadcast(message, nickname, LocalDateTime.now(), Message.LOGIN);

        // 向新用户单播在线列表
        String[] onlineList = getOnlineList();
        Gson gson = new GsonBuilder().create();
        String JsOnlineList = gson.toJson(onlineList);
        unicast(JsOnlineList, "Server", nickname, LocalDateTime.now(), Message.LIST);
    }

    @OnMessage
    public void incoming(String Msg) throws IOException {
        Gson gson = new Gson();
        Message message = gson.fromJson(Msg, Message.class);
        String content = message.getContent();

        if (message.getReceiver().equals("group")) {
            // 消息推送给所有的客户端
            String sendString = String.format("%s %s %s", nickname, "：", content);
            broadcast(sendString, nickname, LocalDateTime.now(), Message.NORMAL);
        } else {
            // 消息推送给指定用户
            String sendString = String.format("[私密]%s %s %s", nickname, "：", content);
            unicast(sendString, nickname, message.getReceiver(), LocalDateTime.now(), Message.NORMAL);
        }
    }

    @OnClose
    public void end() {
        // 连接中断时
        connections.remove(this);
        String message = String.format("用户 %s %s", nickname, "退出了聊天");
        broadcast(message, nickname, LocalDateTime.now(), Message.QUIT);
    }

    @OnError
    public void onError(Throwable t) throws Throwable {
    }

    private static void broadcast(String msg, String sender, LocalDateTime sendTime, int type) {
        Message Msg = new Message(msg, sender, sendTime, type);
        Gson gson = new GsonBuilder().create();
        String msgToJson = gson.toJson(Msg);
        for (ChatAnnotation client : connections) {
            try {
                synchronized (client) { // 同步锁
                    client.session.getBasicRemote().sendText(msgToJson);
                }
            } catch (IOException e) {
                connections.remove(client);
                try {
                    client.session.close();
                } catch (IOException e1) {
                    // Ignore
                }
                String disconnect = String.format("用户 %s %s", client.nickname, "连接已中断");
                broadcast(disconnect, sender, LocalDateTime.now(), Message.QUIT);
            }
        }
    }

    private static void unicast(String msg, String sender, String receiver, LocalDateTime sendTime, int type) {
        Message Msg = new Message(msg, sender, receiver, sendTime, type);
        Gson gson = new GsonBuilder().create();
        String msgToJson = gson.toJson(Msg);
        for (ChatAnnotation client : connections) {
            if (client.nickname.equals(receiver) || client.nickname.equals(sender)) {
                try {
                    synchronized (client) { // 同步锁
                        client.session.getBasicRemote().sendText(msgToJson);
                    }
                } catch (IOException e) {
                    connections.remove(client);
                    try {
                        client.session.close();
                    } catch (IOException e1) {
                        // Ignore
                    }
                    String disconnect = String.format("用户 %s %s", client.nickname, "连接已中断");
                    broadcast(disconnect, sender, LocalDateTime.now(), Message.QUIT);
                }
            }
        }
    }

    private static String[] getOnlineList() {
        String[] onlineList = new String[connections.size()];
        int i = 0;
        for (ChatAnnotation client : connections) {
            synchronized (client) {
                onlineList[i++] = client.nickname;
            }
        }
        return onlineList;
    }
}


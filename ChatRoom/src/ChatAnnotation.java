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
    private static Set<ChatAnnotation> connections = new HashSet<ChatAnnotation>();

    @OnOpen
    public void start(Session session) throws IOException {
        this.session = session;
        // 把当前客户端对应的ChatAnnotation对象加入到集合中
        connections.add(this);

        String message = String.format("%s %s", session.getId(), "加入了群聊");
        broadcast(message, session.getId(), LocalDateTime.now());
    }

    @OnMessage
    public void incoming(String content) throws IOException {
        // 消息推送给所有的客户端
        String message = String.format("%s %s %s", session.getId(), "：", content);
        broadcast(message, session.getId(), LocalDateTime.now());
    }

    @OnClose
    public void end() {
        connections.remove(this);
        String message = String.format("%s %s", session.getId(), "连接已中断");
        broadcast(message, session.getId(), LocalDateTime.now());
    }

    @OnError
    public void onError(Throwable t) throws Throwable {
    }

    private static void broadcast(String msg, String sender, LocalDateTime sendTime) {
        Message Msg = new Message(msg, sender, sendTime);
        Gson gson = new GsonBuilder().create();
        String msgToJson = gson.toJson(Msg);
        for (ChatAnnotation client : connections) {
            try {
                synchronized (client) {
                    client.session.getBasicRemote().sendText(msgToJson);
                }
            } catch (IOException e) {
                connections.remove(client);
                try {
                    client.session.close();
                } catch (IOException e1) {
                }
                String disconnect = String.format("%s %s", client.session.getId(), "连接已中断");
                broadcast(disconnect, sender, LocalDateTime.now());
            }
        }
    }

}

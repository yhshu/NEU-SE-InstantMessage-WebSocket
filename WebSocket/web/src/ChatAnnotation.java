import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/ChatSocket")
public class ChatAnnotation {
    private Session session; // 与客户端通信的session
    private static Set<ChatAnnotation> connections = new HashSet<ChatAnnotation>();

    @OnOpen
    public void start(Session session) throws IOException {
        this.session = session;
        // 把当前客户端对应的ChatAnnotation对象加入到集合中
        connections.add(this);
        String message = String.format("%s %s", session.getId(), "加入了群聊");
        broadcast(message);
    }

    @OnMessage
    public void incoming(String content) throws IOException {
        // 消息推送给所有的客户端
        String message = String.format("%s %s %s", session.getId(), "：", content);
        broadcast(message);
    }

    @OnClose
    public void end() {
        connections.remove(this);
        String message = String.format("%s %s", session.getId(), "连接已中断");
        broadcast(message);
    }

    @OnError
    public void onError(Throwable t) throws Throwable {
    }

    private static void broadcast(String msg) {
        for (ChatAnnotation client : connections) {
            try {
                synchronized (client) {
                    client.session.getBasicRemote().sendText(msg);
                }
            } catch (IOException e) {
                connections.remove(client);
                try {
                    client.session.close();
                } catch (IOException e1) {
                }
                String disconnectMsg = String.format("%s %s", client.session.getId(), "连接已中断");
                broadcast(disconnectMsg);
            }
        }
    }
}

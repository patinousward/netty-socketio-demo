package com.corundumstudio.socketio.demo;

import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;

public class ChatLauncher {
    /**
     * 普通聊天室，直接本地运行main方法即可
     * 或者 mvn exec:java 就会运行pom中默认的类，也就是本类
     * 此命令可以运行别的main程序：
     * mvn exec:java -Dmain.class=com.corundumstudio.socketio.demo.SslChatLauncher
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
                // broadcast messages to all clients
                server.getBroadcastOperations().sendEvent("chatevent", data);
            }
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }

    /**
     * 初始化连接ws：ws://localhost:9092/socket.io/?EIO=3&transport=websocket&sid=c22c8156-d987-482c-a700-46e0f361affc
     * 发送消息的时候["chatevent", {userName: "user98", message: "dd"}] 数据结构
     * 开始连接的时候发送2probe，response 3probe ，再次发送 5表示 连接ok
     * 期间有用数字维持心跳ping 2 pong 3
     *
     * http://localhost:9092/socket.io/?EIO=3&transport=polling&t=1587114609931-3
     * 降级方案。。前台去拉取数据
     *
     */

}

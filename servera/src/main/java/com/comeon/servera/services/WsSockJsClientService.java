package com.comeon.servera.services;

import com.comeon.servera.beans.RefreshValuesBean;
import com.comeon.servera.model.WsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by s_lor_000 on 10/2/2015.
 */
@Service
public class WsSockJsClientService {

    private final static Logger LOGGER = LoggerFactory.getLogger(WsSockJsClientService.class);

    @Value("${lorem.ipsum}")
    private String loremIpsum;

    @Autowired
    RefreshValuesBean refreshValuesBean;

    private WebSocketStompClient stompClient;

    @Value("${ws.endpoint}" )
    private String url;

    @PostConstruct
    public void init() throws InterruptedException {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.afterPropertiesSet();
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        WebSocketClient transport = new SockJsClient(transports);
        stompClient = new WebSocketStompClient(transport);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setTaskScheduler(taskScheduler);
    }

    public void sendData() {
        final String[] randomMsg = loremIpsum.split(" ");
        ListenableFuture<StompSession> connect = stompClient.connect(url, new WsStompSessionHandler());
        connect.addCallback(success -> {
            success.send("/app/update", new WsData(new Random().longs().findAny().getAsLong(), randomMsg[new Random().ints(0, randomMsg.length).findFirst().getAsInt()], refreshValuesBean.getRefreshView()));
            success.disconnect();
        }, fail -> {
            LOGGER.error("Failed sending message! {}", fail.getMessage());
        });
    }
}

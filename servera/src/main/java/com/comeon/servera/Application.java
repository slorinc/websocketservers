package com.comeon.servera;

import com.comeon.servera.model.RefreshValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
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

@SpringBootApplication
@Configuration
@PropertySource("classpath:message.properties")
public class Application {

    @Autowired(required = true)
    private RefreshValues refreshValues;

    private static RefreshValues refreshValuesStatic;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${lorem.ipsum}")
    private String loremIpsum;

    private static String randomString;

    @PostConstruct
    public void postConstruct() {
        randomString = loremIpsum;
        refreshValuesStatic = refreshValues;
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
        final String[] randomMsg = randomString.split(" ");
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.afterPropertiesSet();
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        WebSocketClient transport = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(transport);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        while (true) {
            ListenableFuture<StompSession> connect = stompClient.connect("ws://localhost:8080/handshake", new WsStompSessionHandler());
            connect.addCallback(a -> {
                System.out.println("Success");
                a.send("/app/update", new WsData(new Random().longs().findAny().getAsLong(), randomMsg[new Random().ints(0,randomMsg.length).findFirst().getAsInt()]));
                a.disconnect();
            }, b -> {
                System.out.println(b.getMessage());
                System.out.println("Fail");
            });
            stompClient.setTaskScheduler(taskScheduler);
            Thread.sleep(refreshValuesStatic.getRefreshData()*1000);
        }
    }


}

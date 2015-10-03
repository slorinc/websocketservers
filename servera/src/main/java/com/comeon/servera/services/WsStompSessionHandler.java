package com.comeon.servera.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

/**
 * Created by s_lor_000 on 10/2/2015.
 */
public class WsStompSessionHandler extends StompSessionHandlerAdapter {

    private final static Logger LOGGER = LoggerFactory.getLogger(WsStompSessionHandler.class);


    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        LOGGER.error("Error while connecting! {}", exception.getMessage());
        super.handleException(session, command, headers, payload, exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        LOGGER.error("Disconnected? {}", exception.getMessage());
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
    }
}
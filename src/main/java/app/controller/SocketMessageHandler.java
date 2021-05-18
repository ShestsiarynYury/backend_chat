package app.controller;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketMessageHandler extends TextWebSocketHandler {

	private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

	synchronized void addSession(WebSocketSession session) {
		this.sessions.add(session);
	}

	synchronized void removeSession(WebSocketSession session) {
		this.sessions.remove(session);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		this.addSession(session);
		System.out.println("New Session: " + session.getId());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		this.sessions.remove(session);
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		// this is the message content, that can be any format (json, xml, plain text... who knows?)
		System.out.println(message.getPayload());
		System.out.println(this.sessions.size());
		/*->
			some bisnes login here 
		<-*/
		for (WebSocketSession s : this.sessions) {
			s.sendMessage(message);
		}
	}
}

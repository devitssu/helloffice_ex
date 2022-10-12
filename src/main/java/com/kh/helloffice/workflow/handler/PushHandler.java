package com.kh.helloffice.workflow.handler;

import com.google.gson.Gson;
import com.kh.helloffice.member.entity.DeptEmp;
import com.kh.helloffice.workflow.entity.PushData;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PushHandler extends TextWebSocketHandler {

    Map<Long, WebSocketSession> sessionMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long empNo = getEmpNo(session);
        sessionMap.put(empNo, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }

    private Long getEmpNo(WebSocketSession session) {
        Map<String, Object> attributes = session.getAttributes();
        DeptEmp loginUser = (DeptEmp) attributes.get("loginEmp");
        return loginUser.getEmpNo();
    }

    public void send(List<PushData> pushes) throws IOException {
        Gson gson = new Gson();

        for (PushData push: pushes) {
            WebSocketSession session = sessionMap.get(push.getReceiver());
            if(session != null){
                TextMessage msg = new TextMessage(gson.toJson(push));
                session.sendMessage(msg);
            }
        }
    }
}

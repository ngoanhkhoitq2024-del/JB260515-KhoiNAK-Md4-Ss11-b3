package re.edu.medicinestore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import re.edu.medicinestore.dto.AlertMessage;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AlertSubscriber implements MessageListener {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onMessage(Message message, byte[] pattern) {

        try {
            // Chuyển JSON nhận được thành AlertMessage
            AlertMessage alert = objectMapper.readValue(
                    message.getBody(),
                    AlertMessage.class
            );

            // In thông báo ra Console
            System.out.println("=== NHẬN THÔNG BÁO DASHBOARD ===");
            System.out.println("Type: " + alert.type());
            System.out.println("Nội dung: " + alert.message());
            System.out.println("================================");

        } catch (IOException e) {
            System.err.println("Lỗi đọc thông báo Redis: " + e.getMessage());
        }
    }
}
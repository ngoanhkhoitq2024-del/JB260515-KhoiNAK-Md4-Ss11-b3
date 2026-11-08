package re.edu.medicinestore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import re.edu.medicinestore.dto.AlertMessage;

@Service
@RequiredArgsConstructor
public class AlertPublisher {

    private final StringRedisTemplate redisTemplate;
    private final ChannelTopic alertTopic;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void publishAlert(AlertMessage alert) {

        try {
            // Chuyển đối tượng thành JSON
            String jsonMessage = objectMapper.writeValueAsString(alert);

            // Gửi JSON vào Redis channel
            redisTemplate.convertAndSend(
                    alertTopic.getTopic(),
                    jsonMessage
            );

            System.out.println("Đã gửi thông báo: " + jsonMessage);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Không thể chuyển AlertMessage thành JSON", e);
        }
    }
}
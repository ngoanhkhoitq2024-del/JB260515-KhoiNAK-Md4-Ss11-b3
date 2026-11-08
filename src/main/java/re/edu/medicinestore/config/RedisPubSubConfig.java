package re.edu.medicinestore.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import re.edu.medicinestore.service.AlertSubscriber;

@Configuration
public class RedisPubSubConfig {

    // Khai báo channel
    @Bean
    public ChannelTopic alertTopic() {
        return new ChannelTopic("pharmacy-alerts");
    }

    // Đăng ký Subscriber lắng nghe channel
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory,
            AlertSubscriber alertSubscriber,
            ChannelTopic alertTopic
    ) {
        RedisMessageListenerContainer container =
                new RedisMessageListenerContainer();

        container.setConnectionFactory(connectionFactory);

        container.addMessageListener(alertSubscriber, alertTopic);

        return container;
    }
}
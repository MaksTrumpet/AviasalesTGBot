package MTCompany.configuration;


import config.ObjectMapperConfig;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static MTCompany.model.RabbitQueue.ANSWER_MESSAGE;


@Configuration
public class RabbitConfiguration {
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter(ObjectMapperConfig.getObjectMapper());
    }

    @Bean
    public Queue updateAnswer() {
        return new Queue(ANSWER_MESSAGE);
    }
}


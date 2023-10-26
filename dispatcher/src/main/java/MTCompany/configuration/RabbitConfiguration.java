package MTCompany.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static MTCompany.model.RabbitQueue.ONE_DATE_ONE_CITY_REQUEST;

@Configuration
public class RabbitConfiguration {

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue oneDateOneCityQueue() {
        return new Queue(ONE_DATE_ONE_CITY_REQUEST);
    }
}


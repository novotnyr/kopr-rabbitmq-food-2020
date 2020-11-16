package sk.upjs.ics.kopr.rabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    public static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues = "food")
    public void consumeFood(String message) {
        logger.info("Food has arrived: {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "food", type = ExchangeTypes.FANOUT),
            value = @Queue
    ))
    public void consumeBroadcastFood(String message) {
        logger.info("Broadcast food has arrived: {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "vegetable", type = ExchangeTypes.TOPIC),
            value = @Queue,
            key = "food.carrot"
    ))
    public void consumeCarrot(String carrot) {
        logger.info("Carrot has arrived: {}", carrot);
    }

}

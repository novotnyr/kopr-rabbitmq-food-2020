package sk.upjs.ics.kopr.rabbitmq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    public static final Logger logger = LoggerFactory.getLogger(Producer.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private int messageNumber;

    @Scheduled(fixedDelay = 2000)
    public void publish() {
        String message = "Food " + (messageNumber++);

        rabbitTemplate.convertAndSend("food", message);

        rabbitTemplate.convertAndSend("food", "", message);

        if (Math.random() <= 0.5) {
            logger.info("Producing salad {}", message);
            rabbitTemplate.convertAndSend("vegetable", "food.salad", message);
        } else {
            logger.info("Producing carrot {}", message);
            rabbitTemplate.convertAndSend("vegetable", "food.carrot", message);
        }
    }
}

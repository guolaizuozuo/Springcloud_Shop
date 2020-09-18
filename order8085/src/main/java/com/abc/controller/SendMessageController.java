package com.abc.controller;

import com.abc.message.StreamClient;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;



@RestController
public class SendMessageController {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StreamClient streamClient;

    @GetMapping("/sendMessage")
    public void process(){
        String message = "now-"+new Date();
        streamClient.output().send(MessageBuilder.withPayload(message).build());
    }

    @GetMapping("/send")
    public void send(){
        amqpTemplate.convertAndSend("myQueue","now-"+new Date());
    }

    @GetMapping("/sendOrder")
    public void sendOrder(){
        amqpTemplate.convertAndSend("myOrder","computer","computer-"+new Date());
    }
    @GetMapping("/sendfruit")
    public void sendfruit(){
        amqpTemplate.convertAndSend("myOrder","fruit","fruit-"+new Date());
    }
}

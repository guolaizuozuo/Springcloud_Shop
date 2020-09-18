package com.abc.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;


public interface StreamClient {

    String INPUT = "myMessage1";

    String OUTPUT = "myMessage2";

    @Input(StreamClient.INPUT)
    SubscribableChannel input();

    //MessageChannel
    @Output(StreamClient.OUTPUT)
    MessageChannel output();
}

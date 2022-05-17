package com.dbls.client;

import com.dbls.client.model.AmqpResponse;
import com.dbls.client.service.AmqpConsumerService;
import com.dbls.client.service.AmqpPublisherService;
import com.dbls.client.service.configuration.ClientConfiguration;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import static java.util.Arrays.asList;

public class Main {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ClientConfiguration configuration = ClientConfiguration.readConfiguration(args[0]);
    }
}

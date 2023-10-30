package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.service.InfoService;

@Service
public class InfoServiceImpl implements InfoService {

    @Value("${server.port}")
    private Integer serverPort;

    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public Integer getPort() {
        return serverPort;
    }

    @Override
    public Integer getFastestResultOfStream() {
        LOGGER.info("Was invoked method for get sum of iteration");
        Long startTime = System.nanoTime();

        int sum = 0;
        for (int a = 1; a <= 1_000_000; a++) {
            sum += a;
        }

        Long endTime = System.nanoTime();
        long executionTime = endTime - startTime;

        LOGGER.info("Sum was received successfully. Method completed in " + executionTime + "ns");
        return sum; // использование цикла вместо Stream API более, чем в 4 раза ускоряет выполнение запроса
    }
}

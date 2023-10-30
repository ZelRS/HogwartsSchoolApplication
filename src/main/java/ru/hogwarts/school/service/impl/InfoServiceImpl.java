package ru.hogwarts.school.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.service.InfoService;

@Service
@Slf4j
public class InfoServiceImpl implements InfoService {
    @Value("${server.port}")
    private Integer serverPort;

    // получаем порт, на которм запущено приложение
    public Integer getPort() {
        return serverPort;
    }

    // получаем результат вычисления стрима за наименьшее кол-во времени(метод не относится к приложению
    // и написан в тренировочных целях
    @Override
    public Integer getFastestResultOfStream() {
        log.info("Was invoked method for get sum of iteration");
        Long startTime = System.nanoTime();

        int sum = 0;
        for (int a = 1; a <= 1_000_000; a++) {
            sum += a;
        }

        Long endTime = System.nanoTime();
        long executionTime = endTime - startTime;

        log.info("Sum was received successfully. Method completed in " + executionTime + "ns");
        return sum; // использование цикла вместо Stream API более, чем в 4 раза ускоряет выполнение запроса
    }
}

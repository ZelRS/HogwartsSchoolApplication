package ru.hogwarts.school.service;

public interface InfoService {
    // получаем порт, на которм запущено приложение
    Integer getPort();

    // получаем результат вычисления стрима за наименьшее кол-во времени(метод не относится к приложению
    // и написан в тренировочных целях
    Integer getFastestResultOfStream();
}

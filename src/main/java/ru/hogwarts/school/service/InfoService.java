package ru.hogwarts.school.service;

public interface InfoService {
    Integer getPort();

    Integer getFastestResultOfStream(); //получить результат вычисления стрима за наименьшее кол-во времени
}

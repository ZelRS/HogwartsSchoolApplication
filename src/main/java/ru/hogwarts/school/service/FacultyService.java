package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    // создаем факультет
    Faculty create(Faculty faculty);

    // получаем факультет по id
    Faculty getById(Long id);

    // обновляем существующий факультет
    Faculty update(Faculty faculty);

    // удаляем факультет по id
    void deleteById(Long id);

    // получаем все факультеты
    Collection<Faculty> getAll();

    // получаем факультеты по имени или цвету
    Collection<Faculty> getAllByNameOrColor(String name, String color);

    //получаем самое длинное имя факультета
    String getLongestName();
}

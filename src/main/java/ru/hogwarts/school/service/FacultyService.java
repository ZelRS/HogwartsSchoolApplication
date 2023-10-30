package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty create(Faculty faculty); //создать факультет

    Faculty getById(Long id); //получить факультет по id

    Faculty update(Faculty faculty); // обновить существующий факультет

    void deleteById(Long id); // удалить факультет по id

    Collection<Faculty> getAll(); // получить все факультеты

    Collection<Faculty> getAllByNameOrColor(String name, String color); // получить факультеты по имени или цвету

    String getLongestName(); //получить самое длинное имя факультета
}

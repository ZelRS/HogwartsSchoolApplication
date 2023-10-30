package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.InfoService;

@RestController
@RequestMapping("/info")
@Tag(name = "API для получения информации о приложении")
public class InfoController {
    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("getPort")
    @Operation(summary = "Получение номера используемого порта")
    public ResponseEntity<Integer> getPort() {
        return ResponseEntity.ok(infoService.getPort());
    }

    @GetMapping("get-fastest-result-of-stream")
    @Operation(summary = "Получить результат вычисления стрима за наименьшее кол-во времени")
    public ResponseEntity<Integer> getFastestResultOfStream() {
        return ResponseEntity.ok(infoService.getFastestResultOfStream());
    }
}

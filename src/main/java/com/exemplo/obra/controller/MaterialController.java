package com.exemplo.obra.controller;

import com.exemplo.obra.dto.ConcretoRequest;
import com.exemplo.obra.dto.ConcretoResponse;
import com.exemplo.obra.dto.TijoloRequest;
import com.exemplo.obra.dto.TijoloResponse;
import com.exemplo.obra.service.MaterialService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/materiais")


public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping("/concreto")
    public ResponseEntity<ConcretoResponse> calcularConcreto(@Valid @RequestBody ConcretoRequest request) {
        return ResponseEntity.ok(materialService.calcularVolumeConcreto(request));
    }

    @PostMapping("/tijolos")
    public ResponseEntity<TijoloResponse> calcularTijolos(@Valid @RequestBody TijoloRequest request) {
        return ResponseEntity.ok(materialService.calcularQuantidadeTijolos(request));
    }
}

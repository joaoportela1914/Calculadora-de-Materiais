package com.exemplo.obra.service;

import com.exemplo.obra.dto.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class MaterialService {

    public ConcretoResponse calcularVolumeConcreto(ConcretoRequest request) {
        BigDecimal volumeTotal = BigDecimal.ZERO;
        BigDecimal alturaViga = BigDecimal.valueOf(request.getAlturaViga());


        for (ArestaRequest aresta : request.getArestas()) {
            BigDecimal comp = BigDecimal.valueOf(aresta.getComprimento());
            BigDecimal esp = BigDecimal.valueOf(aresta.getEspessura());

            BigDecimal volumeAresta = comp.multiply(esp).multiply(alturaViga);
            volumeTotal = volumeTotal.add(volumeAresta);
        }

        return new ConcretoResponse(
                volumeTotal.setScale(4, RoundingMode.HALF_UP),
                1, // ID fictício da obra
                "Volume de concreto calculado com sucesso para todas as arestas."
        );
    }

    public TijoloResponse calcularQuantidadeTijolos(TijoloRequest request) {
        BigDecimal totalTijolos = BigDecimal.ZERO;


        BigDecimal alturaTijoloM = BigDecimal.valueOf(request.getAlturaTijolo()).divide(BigDecimal.valueOf(100));
        BigDecimal larguraTijoloM = BigDecimal.valueOf(request.getLarguraTijolo()).divide(BigDecimal.valueOf(100));
        BigDecimal areaTijolo = alturaTijoloM.multiply(larguraTijoloM);

        for (ArestaRequest aresta : request.getArestas()) {
            BigDecimal areaParede = BigDecimal.valueOf(aresta.getComprimento())
                    .multiply(BigDecimal.valueOf(aresta.getAlturaParede()));


            if (aresta.isPossuiPorta()) {
                BigDecimal areaPorta = BigDecimal.valueOf(aresta.getAlturaPorta())
                        .multiply(BigDecimal.valueOf(aresta.getLarguraPorta()));
                areaParede = areaParede.subtract(areaPorta);
            }
            if (aresta.isPossuiJanela()) {
                BigDecimal areaJanela = BigDecimal.valueOf(aresta.getAlturaJanela())
                        .multiply(BigDecimal.valueOf(aresta.getLarguraJanela()));
                areaParede = areaParede.subtract(areaJanela);
            }


            BigDecimal qtdAresta = areaParede.divide(areaTijolo, 0, RoundingMode.CEILING);
            totalTijolos = totalTijolos.add(qtdAresta);
        }

        BigDecimal fatorPerda = BigDecimal.valueOf(1).add(BigDecimal.valueOf(request.getPercentualPerda()).divide(BigDecimal.valueOf(100)));
        totalTijolos = totalTijolos.multiply(fatorPerda).setScale(0, RoundingMode.CEILING);

        return new TijoloResponse(
                totalTijolos,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                0, 0,
                "Quantidade de tijolos calculada com sucesso."
        );
    }
}
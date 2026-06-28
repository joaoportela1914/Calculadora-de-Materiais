package com.exemplo.obra.view;

import com.exemplo.obra.dto.TijoloRequest;
import com.exemplo.obra.model.Orcamento;
import com.exemplo.obra.repository.OrcamentoRepository;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.io.Serializable;
import java.util.List;

@Component("orcamentoBean")
@jakarta.faces.view.ViewScoped
public class OrcamentoBean implements Serializable {

    private final RestClient restClient;
    private final OrcamentoRepository orcamentoRepository;

    private String nomeUsuario;
    private Double comprimentoParede;
    private Double alturaParede;
    private Double comprimentoTijolo;
    private Double alturaTijolo;
    private Integer quantidadeTijolos;

    public OrcamentoBean(@Value("${app.base-url:http://localhost:8080}") String baseUrl,
                         OrcamentoRepository orcamentoRepository) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
        this.orcamentoRepository = orcamentoRepository;
    }

    public void calcularOrcamento() {
        try {
            if (comprimentoParede == null || alturaParede == null || comprimentoTijolo == null || alturaTijolo == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Preencha todos os campos corretamente."));
                return;
            }

            double areaParedeMetros = comprimentoParede * alturaParede;

            double comprimentoTijoloMetros = comprimentoTijolo / 100.0;
            double alturaTijoloMetros = alturaTijolo / 100.0;
            double areaTijoloMetros = comprimentoTijoloMetros * alturaTijoloMetros;

            double qtdBase = areaParedeMetros / areaTijoloMetros;
            double qtdComPerda = qtdBase * 1.10;

            this.quantidadeTijolos = (int) Math.ceil(qtdComPerda);

            try {
                TijoloRequest request = new TijoloRequest();
                request.setLarguraTijolo(this.comprimentoTijolo);
                request.setAlturaTijolo(this.alturaTijolo);
                request.setPercentualPerda(10.0);

                com.exemplo.obra.dto.ArestaRequest aresta = new com.exemplo.obra.dto.ArestaRequest();
                aresta.setId("1");
                aresta.setVerticeOrigem("A");
                aresta.setVerticeDestino("B");
                aresta.setEspessura(15.0);
                aresta.setComprimento(this.comprimentoParede * 100);
                aresta.setAlturaParede(this.alturaParede * 100);

                request.setArestas(List.of(aresta));

                restClient.post()
                        .uri("/api/materials/tijolos")
                        .body(request)
                        .retrieve()
                        .toBodilessEntity();
            } catch (Exception apiEx) {

            }

            Orcamento orcamento = new Orcamento();
            orcamento.setNomeUsuario(this.nomeUsuario);
            orcamento.setComprimentoParede(this.comprimentoParede);
            orcamento.setAlturaParede(this.alturaParede);
            orcamento.setQuantidadeTijolos(this.quantidadeTijolos);

            orcamentoRepository.save(orcamento);

            String resumo = "Orçamento de " + nomeUsuario + ": Serão necessários " + quantidadeTijolos + " tijolos!";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Cálculo Concluído!", resumo));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no Processamento", e.getMessage()));
        }
    }

    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }

    public Double getComprimentoParede() { return comprimentoParede; }
    public void setComprimentoParede(Double comprimentoParede) { this.comprimentoParede = comprimentoParede; }

    public Double getAlturaParede() { return alturaParede; }
    public void setAlturaParede(Double alturaParede) { this.alturaParede = alturaParede; }

    public Double getComprimentoTijolo() { return comprimentoTijolo; }
    public void setComprimentoTijolo(Double comprimentoTijolo) { this.comprimentoTijolo = comprimentoTijolo; }

    public Double getAlturaTijolo() { return alturaTijolo; }
    public void setAlturaTijolo(Double alturaTijolo) { this.alturaTijolo = alturaTijolo; }

    public Integer getQuantidadeTijolos() { return quantidadeTijolos; }
    public void setQuantidadeTijolos(Integer quantidadeTijolos) { this.quantidadeTijolos = quantidadeTijolos; }
}
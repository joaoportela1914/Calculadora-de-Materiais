package com.exemplo.obra.view;

import com.exemplo.obra.model.Orcamento;
import com.exemplo.obra.repository.OrcamentoRepository;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.List;

@Component("consultaBean")
@jakarta.faces.view.ViewScoped
public class ConsultaOrcamentoBean implements Serializable {

    private final OrcamentoRepository orcamentoRepository;

    private String termoBusca;
    private List<Orcamento> orcamentos;

    public ConsultaOrcamentoBean(OrcamentoRepository orcamentoRepository) {
        this.orcamentoRepository = orcamentoRepository;
    }

    public void buscar() {
        if (termoBusca == null || termoBusca.trim().isEmpty()) {
            orcamentos = orcamentoRepository.findAll();
        } else {
            orcamentos = orcamentoRepository.findByNomeUsuarioContainingIgnoreCase(termoBusca);
        }
    }

    public String getTermoBusca() { return termoBusca; }
    public void setTermoBusca(String termoBusca) { this.termoBusca = termoBusca; }

    public List<Orcamento> getOrcamentos() { return orcamentos; }
    public void setOrcamentos(List<Orcamento> orcamentos) { this.orcamentos = orcamentos; }
}

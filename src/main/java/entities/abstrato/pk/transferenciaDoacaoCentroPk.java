package entities.abstrato.pk;

import entities.abstrato.Doacao;
import entities.fisico.estrutural.CentroDistribuicao;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class transferenciaDoacaoCentroPk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doacao_id", nullable = false)
    private Doacao doacao;

    @ManyToOne
    @JoinColumn(name = "centro_distribuicao_id", nullable = false)
    private CentroDistribuicao centroDistribuicao;

    public transferenciaDoacaoCentroPk() {}

    public transferenciaDoacaoCentroPk(Doacao doacao, CentroDistribuicao centroDistribuicao) {
        this.doacao = doacao;
        this.centroDistribuicao = centroDistribuicao;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doacao getDoacao() {
        return doacao;
    }

    public void setDoacao(Doacao doacao) {
        this.doacao = doacao;
    }

    public CentroDistribuicao getCentroDistribuicao() {
        return centroDistribuicao;
    }

    public void setCentroDistribuicao(CentroDistribuicao centroDistribuicao) {
        this.centroDistribuicao = centroDistribuicao;
    }

}

package entities.abstrato;

import entities.abstrato.pk.TransferenciaDoacaoCentroPk;
import entities.fisico.estrutural.CentroDistribuicao;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class TransferenciaDoacaoCentro implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private TransferenciaDoacaoCentroPk id = new TransferenciaDoacaoCentroPk();

    public TransferenciaDoacaoCentro() {}

    public TransferenciaDoacaoCentro(Doacao doacao, CentroDistribuicao centroDistribuicao) {
        super();
        id.setDoacao(doacao);
        id.setCentroDistribuicao(centroDistribuicao);
    }

    public void setDoacao(Doacao doacao) {
        id.setDoacao(doacao);
    }

    public Doacao getDoacao() {
        return id.getDoacao();
    }

    public CentroDistribuicao getCentroDistribuicao() {
        return id.getCentroDistribuicao();
    }

    public void setCentroDistribuicao(CentroDistribuicao centroDistribuicao) {
        id.setCentroDistribuicao(centroDistribuicao);
    }

    public TransferenciaDoacaoCentroPk getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferenciaDoacaoCentro that = (TransferenciaDoacaoCentro) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

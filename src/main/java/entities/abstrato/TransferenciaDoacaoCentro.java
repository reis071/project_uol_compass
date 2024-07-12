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
        id.setId_centroDistribuicao(centroDistribuicao);
    }

    public Doacao getDoacao() {
        return id.getDoacao();
    }

    public void setDoacao(Doacao doacao) {
        this.id.setDoacao(doacao);
    }

    public CentroDistribuicao getCentroDistribuicao() {
        return id.getId_centroDistribuicao();
    }

    public void setCentroDistribuicao(CentroDistribuicao centroDistribuicao) {
        this.id.setId_centroDistribuicao(centroDistribuicao);
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

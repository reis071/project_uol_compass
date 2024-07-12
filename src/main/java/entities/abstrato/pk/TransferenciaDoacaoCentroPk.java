package entities.abstrato.pk;

import entities.abstrato.Doacao;
import entities.fisico.estrutural.CentroDistribuicao;
import jakarta.persistence.JoinColumn;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class TransferenciaDoacaoCentroPk implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "id_doacao")
    private Doacao doacao;

    @ManyToOne
    @JoinColumn(name = "id_centroDistribuicao")
    private CentroDistribuicao id_centroDistribuicao;

    public TransferenciaDoacaoCentroPk() {}

    public TransferenciaDoacaoCentroPk(Doacao doacao, CentroDistribuicao centroDistribuicao) {
        this.doacao = doacao;
        this.id_centroDistribuicao = centroDistribuicao;
    }

    // Getters and Setters

    public Doacao getDoacao() {
        return doacao;
    }

    public void setDoacao(Doacao id_doacao) {
        this.doacao = id_doacao;
    }

    public CentroDistribuicao getId_centroDistribuicao() {
        return id_centroDistribuicao;
    }

    public void setId_centroDistribuicao(CentroDistribuicao id_centroDistribuicao) {
        this.id_centroDistribuicao = id_centroDistribuicao;
    }

    // Equals and HashCode (optional but recommended)
    // Ensure you override equals and hashCode properly for correct comparison
    // of embedded IDs.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransferenciaDoacaoCentroPk that = (TransferenciaDoacaoCentroPk) o;

        if (!doacao.equals(that.doacao)) return false;
        return id_centroDistribuicao.equals(that.id_centroDistribuicao);
    }

    @Override
    public int hashCode() {
        int result = doacao.hashCode();
        result = 31 * result + id_centroDistribuicao.hashCode();
        return result;
    }
}

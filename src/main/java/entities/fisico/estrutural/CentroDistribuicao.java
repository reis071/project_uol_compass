package entities.fisico.estrutural;

import entities.abstrato.Doacao;
import entities.abstrato.TransferenciaDoacaoCentro;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class CentroDistribuicao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "id.centroDistribuicao")
    private Set<TransferenciaDoacaoCentro> transferencia = new HashSet<>();

    public CentroDistribuicao(){}
    public CentroDistribuicao(long l, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Set<Doacao> getDoacao() {
        Set<Doacao> set = new HashSet<>();
        for (TransferenciaDoacaoCentro t : transferencia) {
            set.add(t.getDoacao());
        }
        return set;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    public long countAlimentos() {
        long count = 0;
        for (TransferenciaDoacaoCentro transferencia : transferencia) {
            count += transferencia.getDoacao().getAlimentos().size();
        }
        return count;
    }

    public long countRoupas() {
        long count = 0;
        for (TransferenciaDoacaoCentro transferencia : transferencia) {
            count += transferencia.getDoacao().getRoupas().size();
        }
        return count;
    }

    public long countHigienes() {
        long count = 0;
        for (TransferenciaDoacaoCentro transferencia : transferencia) {
            count += transferencia.getDoacao().getHigienes().size();
        }
        return count;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CentroDistribuicao that = (CentroDistribuicao) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

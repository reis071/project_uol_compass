package entities.produtos;

import enums.HigieneEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Higiene implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private HigieneEnum tipoProduto;

    public Higiene() {}

    public Higiene(HigieneEnum tipoProduto, String descricao) {
        this.descricao = descricao;
        this.tipoProduto = tipoProduto;
    }

    public String getDescricao() {
        return descricao;
    }



    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public HigieneEnum getTippProduto() {
        return tipoProduto;
    }

    public void setTippProduto(HigieneEnum tippProduto) {
        this.tipoProduto = tippProduto;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Higiene higiene = (Higiene) o;
        return Objects.equals(id, higiene.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

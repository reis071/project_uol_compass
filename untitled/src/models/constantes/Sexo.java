package models.constantes;

public enum Sexo {
    MASCULINO(1),
    FEMININO(2),
    UNISSEX(3);

private int valor;
private Sexo(int valor) {
    this.valor = valor;
}
public int getValor() {
    return valor;
}
}

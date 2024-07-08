package enums;

public enum Tamanho {
    INFANTIL(1),
    PP(2),
    P(3),
    M(4),
    G(5),
    GG(6);

    private int value;
    private Tamanho(int cod) {
        this.value = cod;
    }
    public int getValue() {
        return value;
    }
    public static Tamanho fromValue(int value) {
        for (Tamanho tamanho : Tamanho.values()) {
            if (tamanho.getValue() == value) {
                return tamanho;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Tamanho: " + value);
    }
}

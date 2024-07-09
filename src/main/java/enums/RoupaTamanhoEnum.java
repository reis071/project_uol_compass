package enums;

public enum RoupaTamanhoEnum {
    INFANTIL(1),
    PP(2),
    P(3),
    M(4),
    G(5),
    GG(6);

    private int value;
    private RoupaTamanhoEnum(int cod) {
        this.value = cod;
    }
    public int getValue() {
        return value;
    }
    public static RoupaTamanhoEnum fromValue(int value) {
        for (RoupaTamanhoEnum tamanho : RoupaTamanhoEnum.values()) {
            if (tamanho.getValue() == value) {
                return tamanho;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Tamanho: " + value);
    }
}

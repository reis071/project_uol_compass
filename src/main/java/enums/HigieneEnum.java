package enums;

public enum HigieneEnum {
    SABONTE(1),
    ESCOVA_DE_DENTES(2),
    PASTA_DE_DENTES(3),
    ABSORVENTES(4);

    private int value;

    private HigieneEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static HigieneEnum fromValue(int value) {
        for (HigieneEnum tipo : HigieneEnum.values()) {
            if (tipo.getValue() == value) {
                return tipo;
            }
        }
        return null;
    }
}

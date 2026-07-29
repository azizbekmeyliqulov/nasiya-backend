package xurshid_azizbek.com.example.nasiyabackend.entity.enums;

public enum  TakenBy {

    SELF("O'zi"),
    SON("O'g'li"),
    DAUGHTER("Qizi"),
    WIFE("Xotini"),
    OTHER("Boshqa");

    private final String label;
    TakenBy(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
        public static TakenBy fromLabel(String label) {
            for (TakenBy value : values()) {
                if (value.label.equals(label)) {
                    return value;
                }
            }
            throw new IllegalArgumentException("Noma'lum taken_by qiymati: " + label);
        }
    }



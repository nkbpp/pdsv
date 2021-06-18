package ru.pfr.model.asv;


public class LegalEntityGroup {
    private String INSURER_REG_NUM;
    private Double PAYMENT_SUM;

    public LegalEntityGroup() {
    }

    public LegalEntityGroup(String INSURER_REG_NUM, Double PAYMENT_SUM) {
        this.INSURER_REG_NUM = INSURER_REG_NUM;
        this.PAYMENT_SUM = PAYMENT_SUM;
    }

    public String getINSURER_REG_NUM() {
        return INSURER_REG_NUM;
    }

    public void setINSURER_REG_NUM(String INSURER_REG_NUM) {
        this.INSURER_REG_NUM = INSURER_REG_NUM;
    }

    public Double getPAYMENT_SUM() {
        return PAYMENT_SUM;
    }

    public void setPAYMENT_SUM(Double PAYMENT_SUM) {
        this.PAYMENT_SUM = PAYMENT_SUM;
    }
}

package ru.pfr.model.asv;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PdsvLegalEntity")
public class LegalEntity {
    @Id
    @Column(name = "num")
    private String NUM;
    @Column(name = "INSURER_REG_NUM")
    private String INSURER_REG_NUM;
    @Column(name = "PAYMENT_SUM")
    private Double PAYMENT_SUM;

    public LegalEntity() {
    }

    public LegalEntity(String NUM, String INSURER_REG_NUM, Double PAYMENT_SUM) {
        this.NUM = NUM;
        this.INSURER_REG_NUM = INSURER_REG_NUM;
        this.PAYMENT_SUM = PAYMENT_SUM;
    }

    public String getNUM() {
        return NUM;
    }

    public void setNUM(String NUM) {
        this.NUM = NUM;
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

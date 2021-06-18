package ru.pfr.model.pdsvvrr;


import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.List;

@Entity
@Table(name = "tray")
public class Tray {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "num")
    private String num;

    @Column(name = "checksum")
    private double checksum;

    @Column(name = "name")
    private String name;

    @Transient
    private double fl;

    @Transient
    private double jul;

    @Transient
    private double itog;

    @Transient
    private double prozent;

    public Tray() {
    }

    public Tray(String num, Double checksum) {
        this.num = num;
        this.checksum = checksum;
        fl=0;
        jul=0;
        itog=0;
        prozent=0;
    }

    public Tray(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public String getIds() {
        if(this.getNum()==null) return "";
        return String.valueOf(id);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public String getNums() {
        if(this.getNum()==null) return "";
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public double getChecksum() {
        return checksum;
    }

    public String getChecksums() {
        if(this.getNum()==null) return "";
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);
        return df.format(checksum);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChecksum(double checksum) {
        this.checksum = checksum;
    }

    public double getFl() {
        return fl;
    }

    public String getFls() {
        if(this.getNum()==null) return "";
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);
        return df.format(fl);
    }

    public void setFl(double fl) {
        setItog(getItog()+fl);
        setProzent(getItog()/getChecksum()*100);
        this.fl = fl;
    }

    public double getJul() {
        return jul;
    }

    public String getJuls() {
        if(this.getNum()==null) return "";
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);
        return df.format(jul);
    }

    public void setJul(double jul) {
        setItog(getItog()+jul);
        setProzent(getItog()/getChecksum()*100);
        this.jul = jul;
    }

    public double getItog() {
        return itog;
    }

    public String getItogs() {
        if(this.getNum()==null) return "";
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);
        return df.format(itog);
    }

    private void setItog(double itog) {
        this.itog = itog;
    }

    public double getProzent() {
        return prozent;
    }

    public String getProzents() {
        if(this.getNum()==null) return "";
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);
        return df.format(prozent)+"%";
    }

    private void setProzent(double prozent) {
        this.prozent = prozent;
    }
}

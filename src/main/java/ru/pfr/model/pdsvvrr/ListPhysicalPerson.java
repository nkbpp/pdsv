package ru.pfr.model.pdsvvrr;

import javax.persistence.*;

@Entity
@Table(name = "listphysicalperson")
public class ListPhysicalPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rayon")
    private Long rayon;

    @Column(name = "inputnum")
    private String inputnum;

    @Column(name = "numberdoc")
    private Long numberdoc;

    @Column(name = "regnum")
    private String regnum;

    @Column(name = "srahnum")
    private String srahnum;

    @Column(name = "condition")
    private String condition;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name")
    private String name;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "index")
    private Long index;

    @Column(name = "address")
    private String address;

    public ListPhysicalPerson() {
    }

    public ListPhysicalPerson(Long rayon, String inputnum, Long numberdoc, String regnum, String srahnum, String condition, String surname, String name, String patronymic, Long index, String address) {
        this.rayon = rayon;
        this.inputnum = inputnum;
        this.numberdoc = numberdoc;
        this.regnum = regnum;
        this.srahnum = srahnum;
        this.condition = condition;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.index = index;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRayon() {
        return rayon;
    }

    public void setRayon(Long rayon) {
        this.rayon = rayon;
    }

    public String getInputnum() {
        return inputnum;
    }

    public void setInputnum(String inputnum) {
        this.inputnum = inputnum;
    }

    public Long getNumberdoc() {
        return numberdoc;
    }

    public void setNumberdoc(Long numberdoc) {
        this.numberdoc = numberdoc;
    }

    public String getRegnum() {
        return regnum;
    }

    public void setRegnum(String regnum) {
        this.regnum = regnum;
    }

    public String getSrahnum() {
        return srahnum;
    }

    public void setSrahnum(String srahnum) {
        this.srahnum = srahnum;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

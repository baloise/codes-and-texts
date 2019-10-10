package ch.basler.cat.model;

import javax.persistence.*;

@Entity(name = "codevalue")
public class CodeValue {

    @Id
    private String id;

    private long value;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codetype_id")
    private CodeType codeType;

    public CodeType getCodeType() {
        return codeType;
    }

    public void setCodeType(CodeType codeType) {
        this.codeType = codeType;
    }

    private String creator;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}



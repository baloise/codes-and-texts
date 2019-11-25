package ch.basler.cat.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "codetype")
public class CodeType {

    @Id
    @Column(name = "ID")
    private long id;
    private String creator;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "responsible_id", updatable = false)
    private Responsible responsible;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "codeType")
    private List<CodeValue> codeValues;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "codeType")
    private List<CodeStyle> codeStyles;

    public List<CodeStyle> getCodeStyles() {
        return codeStyles;
    }

    public void setCodeStyles(List<CodeStyle> codeStyles) {
        this.codeStyles = codeStyles;
    }

    public List<CodeValue> getCodeValues() {
        return codeValues;
    }

    public void setCodeValues(List<CodeValue> codeValues) {
        this.codeValues = codeValues;
    }

    public String getPrefix() {
        return this.responsible.getPrefix();
    }

    public String getPackageName() {
        return this.responsible.getPackagename();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
}

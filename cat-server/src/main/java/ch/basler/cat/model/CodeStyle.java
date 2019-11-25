package ch.basler.cat.model;

import javax.persistence.*;

@Entity(name = "codestyle")
public class CodeStyle {

    @Id
    @Column(name = "ID")
    private String id;

    @JoinColumn(name = "CODETYPE_ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private CodeType codeType;

    @Column(name = "STYLE_ID")
    private long styleId;

    private String creator;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CodeType getCodeType() {
        return codeType;
    }

    public void setCodeType(CodeType codeType) {
        this.codeType = codeType;
    }

    public long getStyleId() {
        return styleId;
    }

    public void setStyleId(long styleId) {
        this.styleId = styleId;
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

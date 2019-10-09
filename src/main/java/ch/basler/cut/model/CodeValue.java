package ch.basler.cut.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "codevalue")
public class CodeValue {

    @Id
    private long id;

    @Column(name = "codetype_id")
    private long codeTypeId;

    private String creator;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCodeTypeId() {
        return codeTypeId;
    }

    public void setCodeTypeId(long codeTypeId) {
        this.codeTypeId = codeTypeId;
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

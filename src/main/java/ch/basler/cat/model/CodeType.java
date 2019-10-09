package ch.basler.cat.model;

import org.springframework.data.annotation.AccessType;

import javax.persistence.*;

@Entity(name = "codetype")
@AccessType(AccessType.Type.PROPERTY)
public class CodeType {

    @Id
    @Column(name = "ID")
    private long id;
    private String creator;
    private String name;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "responsible_id", updatable = false)
    private Responsible responsible;

    public String getPrefix() {
        return this.responsible.getPrefix();
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

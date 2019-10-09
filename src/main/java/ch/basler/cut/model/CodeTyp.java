package ch.basler.cut.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "CODETYP")
public class CodeTyp {
    @Id
    @Column(name = "ID")
    private long id;
    private String erfasser;
    private String name;

    public String getErfasser() {
        return erfasser;
    }

    public void setErfasser(String erfasser) {
        this.erfasser = erfasser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

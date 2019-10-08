package ch.basler.cut.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "TBTI_CODETYP")
public class CodeTyp {

    @Id
    @Column(name = "lauf_nr")
    private long id;

    private String erfasser;
    private String name;
    @Column(name = "ZUSTAENDIGKEIT_NR")
    private int zustaendigkeitNr;

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

    public int getZustaendigkeitNr() {
        return zustaendigkeitNr;
    }

    public void setZustaendigkeitNr(int zustaendigkeitNr) {
        this.zustaendigkeitNr = zustaendigkeitNr;
    }
}

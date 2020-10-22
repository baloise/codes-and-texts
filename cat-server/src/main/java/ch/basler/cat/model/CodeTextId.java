package ch.basler.cat.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

public class CodeTextId implements Serializable {

    @Id
    private long value;

    @Id
    @Column(name="type_id")
    private long type;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getType() {
        return type;
    }

    public void setType(long typeId) {
        this.type = typeId;
    }

    public static CodeTextId of(long type, long value) {
        CodeTextId id = new CodeTextId();
        id.setType(type);
        id.setValue(value);
        return id;
    }
}

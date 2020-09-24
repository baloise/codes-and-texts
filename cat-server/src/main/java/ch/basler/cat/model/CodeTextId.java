package ch.basler.cat.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

public class CodeTextId implements Serializable {

    @Id
    @GenericGenerator(name = "codetext_id", strategy = "ch.basler.cat.model.CodeTextIdGenerator")
    @GeneratedValue(generator = "codetext_id")
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

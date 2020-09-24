package ch.basler.cat.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

public class CodeValueId implements Serializable {

    @Id
    @GenericGenerator(name = "codevalue_id", strategy = "ch.basler.cat.model.CodeValueIdGenerator")
    @GeneratedValue(generator = "codevalue_id")
    private long value;

    @Id
    @Column(name = "codetype_id")
    private long typeId;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public static CodeValueId of(String idString) {
        String[] idParts = idString.split(":");
        CodeValueId id = new CodeValueId();
        id.setTypeId(Long.parseLong(idParts[0]));
        id.setValue(Long.parseLong(idParts[1]));
        return id;
    }
}

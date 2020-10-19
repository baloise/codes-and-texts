package ch.basler.cat.client.backend.data;


public class CodeText {

    private Long type;
    private Long value;
    private String name;
    private Long textId;

    private String textD;
    private String textF;
    private String textI;
    private String textE;

    boolean newCodeText = false;

    public boolean isNewCodeText() {
        return newCodeText;
    }

    public void setNewCodeText(boolean newCodeText) {
        this.newCodeText = newCodeText;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTextId() {
        return textId;
    }

    public void setTextId(Long textId) {
        this.textId = textId;
    }

    public String getTextD() {
        return textD;
    }

    public void setTextD(String textD) {
        this.textD = textD;
    }

    public String getTextF() {
        return textF;
    }

    public void setTextF(String textF) {
        this.textF = textF;
    }

    public String getTextI() {
        return textI;
    }

    public void setTextI(String textI) {
        this.textI = textI;
    }

    public String getTextE() {
        return textE;
    }

    public void setTextE(String textE) {
        this.textE = textE;
    }
}

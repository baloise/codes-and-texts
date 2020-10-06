/*
 * Copyright 2020 Baloise Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.basler.cat.client.backend.data;

import javax.validation.constraints.NotNull;
import javax.xml.soap.Text;
import java.util.Objects;

public class TextData {

    @NotNull
    private Long id = -1L;
    @NotNull
    private Long type = TextType.ALL.getValue();

    private String textD = "";
    private String textF = "";
    private String textI = "";
    private String textE = "";

    private String creator = "";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getType() {
        return type;
    }

    public TextType getTextType() {
        return TextType.of(type);
    }

    public void setType(long type) {
        this.type = type;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return id + "::[" + textD.trim() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextData textData = (TextData) o;
        return id == textData.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isNewText() {
        return id < 0;
    }
}

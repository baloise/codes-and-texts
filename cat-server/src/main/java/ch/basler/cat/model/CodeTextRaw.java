/*
 * Copyright 2019 Baloise Group
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
package ch.basler.cat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity(name = "codetextraw")
@IdClass(CodeTextId.class)
public class CodeTextRaw {


    public static final int ONLY_SUPPORTERD_YET_STYLE_ID = 1;

    public CodeTextRaw () {}
    public CodeTextRaw (CodeText codeText) {
        this.type = codeText.getType();
        this.value = codeText.getValue();
        this.textId = codeText.getTextId();
        this.styleId = ONLY_SUPPORTERD_YET_STYLE_ID;
    }

    @Id
    private long value;

    @Id
    private long type;

    @Column(name="text_nr")
    private long textId;

    @Column(name="stil_nr")
    private long styleId;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public long getTextId() {
        return textId;
    }

    public void setTextId(long textId) {
        this.textId = textId;
    }

    public long getStyleId() {
        return styleId;
    }

    public void setStyleId(long styleId) {
        this.styleId = styleId;
    }
}



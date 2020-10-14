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
package ch.basler.cat.api.text;

import ch.basler.cat.api.code.CodeValue;
import ch.basler.cat.api.base.TextItem;

public class CodeText implements TextItem<CodeTextId> {

    private final long type;
    private final long value;
    private final String name;

    private final String textD;
    private final String textF;
    private final String textI;
    private final String textE;

    public CodeText(CodeValue codeValue) {
        this(codeValue, null, null, null, null);
    }

    CodeText(CodeValue codeValue, String textD, String textF, String textI, String textE) {
        this.type = codeValue.getType();
        this.value = codeValue.getValue();
        this.name = codeValue.getName();
        this.textD = textD;
        this.textF = textF;
        this.textI = textI;
        this.textE = textE;
    }

    @Override
    public CodeTextId getIdentifier() {
        return new CodeTextId(type, value);
    }

    public String getName() {
        return name;
    }

    public String getTextD() {
        return textD;
    }

    public String getTextF() {
        return textF;
    }

    public String getTextI() {
        return textI;
    }

    public String getTextE() {
        return textE;
    }

}

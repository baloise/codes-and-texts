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
package ch.basler.cat.client.backend.data;


import javax.validation.constraints.NotNull;
import java.util.Objects;

public class CodeValue {

    // Combined key : codeType.id : codeValue.value
    @NotNull
    private String id ="";
    @NotNull
    private long value = -1;
    @NotNull
    private String name;
    @NotNull
    private CodeType codeType;
    @NotNull
    private String creator;

    public CodeType getCodeType() {
        return codeType;
    }

    public void setCodeType(CodeType codeType) {
        this.codeType = codeType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public boolean isNewCodeValue() {
        return getId().isEmpty();
    }

    /*
     * Vaadin DataProviders rely on properly implemented equals and hashcode
     * methods.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || id.isEmpty()) {
            return false;
        }
        if (obj instanceof CodeValue) {
            return id.equals(((CodeValue) obj).id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (id.isEmpty()) {
            return super.hashCode();
        }

        return Objects.hash(id);
    }
}



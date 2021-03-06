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

    @NotNull
    private long value = -1;
    @NotNull
    private String name;
    @NotNull
    private long type;
    @NotNull
    private String creator;

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
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
        return value < 0;
    }

    /*
     * Vaadin DataProviders rely on properly implemented equals and hashcode
     * methods.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || isNewCodeValue()) {
            return false;
        }
        if (obj instanceof CodeValue) {
            CodeValue other = (CodeValue) obj;
            return (this.type == other.type) && (this.value == other.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (isNewCodeValue()) {
            return super.hashCode();
        }

        return Objects.hash(type, value);
    }
}



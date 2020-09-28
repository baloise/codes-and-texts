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

public class CodeType {

    @NotNull
    private long id = -1;;
    @NotNull
    private String creator;
    @NotNull
    private String name;


    private Long domain;

    public CodeType() {
    }

    public long getId() {
        return id;
    }
    public Long getDomain() {
        return domain;
    }

    public void setDomain(Long domainId) {
        this.domain = domainId;
    }

    public void setId(long id) {
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

    public boolean isNewCodeType() {
        return getId() == -1;
    }
    @Override
    public String toString() {
        return  name;
    }
    /*
     * Vaadin DataProviders rely on properly implemented equals and hashcode
     * methods.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || id == -1) {
            return false;
        }
        if (obj instanceof CodeType) {
            return id == ((CodeType) obj).id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (id == -1) {
            return super.hashCode();
        }

        return Objects.hash(id);
    }
}

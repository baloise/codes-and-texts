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

import javax.persistence.*;
import java.util.List;

@Entity(name = "codetype")
public class CodeType {

    @Id
    @Column(name = "ID")
    private long id;
    private String creator;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "responsible_id", updatable = false)
    private Responsible responsible;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "codeType")
    private List<CodeValue> codeValues;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "codeType")
    private List<CodeStyle> codeStyles;

    public List<CodeStyle> getCodeStyles() {
        return codeStyles;
    }

    public void setCodeStyles(List<CodeStyle> codeStyles) {
        this.codeStyles = codeStyles;
    }

    public List<CodeValue> getCodeValues() {
        return codeValues;
    }

    public void setCodeValues(List<CodeValue> codeValues) {
        this.codeValues = codeValues;
    }

    public String getPrefix() {
        return this.responsible.getPrefix();
    }

    public String getPackageName() {
        return this.responsible.getPackageName();
    }

    public long getId() {
        return id;
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
}

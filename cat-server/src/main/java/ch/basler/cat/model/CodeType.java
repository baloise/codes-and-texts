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

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "codetype")
public class CodeType {
    @Id
    @GenericGenerator(name = "codetype_id", strategy = "ch.basler.cat.model.CodeTypeIdGenerator")
    @GeneratedValue(generator = "codetype_id")
    @Column(name = "ID")
    private Long id;

    private String creator;
    private String name;
    @Column(name = "domain_id")
    private long domainId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public long getDomainId() {
        return this.domainId;
    }

    public void setDomainId(long domainId) {
        this.domainId = domainId;
    }
}

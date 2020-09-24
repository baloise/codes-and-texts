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

@Entity(name = "labeltext")
public class LabelText {

    @Id
    private String id;

    @Column(name="application_id")
    private long appId;
    private String name;

    @Column(name="text_d")
    private String textD;
    @Column(name="text_f")
    private String textF;
    @Column(name="text_i")
    private String textI;
    @Column(name="text_e")
    private String textE;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long codeTyp) {
        this.appId = codeTyp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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



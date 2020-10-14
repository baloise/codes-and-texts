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
package ch.basler.cat.api.base;

public interface TextItem<T> {

    T getIdentifier();

    String getTextD();

    String getTextF();

    String getTextI();

    String getTextE();

    default String getText(Language language) {
        if (language == null) {
            return null;
        }
        switch (language) {
            case DE:
                return getTextD();
            case FR:
                return getTextF();
            case IT:
                return getTextI();
            case EN:
                return getTextE();
            default:
                return null;
        }
    }

}

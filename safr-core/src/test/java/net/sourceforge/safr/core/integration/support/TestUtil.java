/*
 * Copyright 2006-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sourceforge.safr.core.integration.support;

import java.util.Collection;

/**
 * @author Martin Krasser
 */
public class TestUtil {

    public static <T extends Collection<String>> T createStringCollection(Class<T> clazz, String... elements) throws Exception {
        T result = clazz.newInstance();
        for (String element : elements) {
            result.add(element);
        }
        return result;
    }

    public static String[] createStringArray(String... elements) {
        return elements;
    }

}

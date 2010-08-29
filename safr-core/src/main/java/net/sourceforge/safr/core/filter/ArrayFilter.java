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
package net.sourceforge.safr.core.filter;

import java.lang.reflect.Array;
import java.util.ArrayList;

import net.sourceforge.safr.core.provider.AccessManager;

/**
 * A security filter that operates on array types. This class doesn't support
 * filtering of nested arrays. A nested array is treated like a simple object.
 * 
 * @author Martin Krasser
 */
class ArrayFilter extends ObjectFilter {

    public ArrayFilter(AccessManager accessManager) {
        super(accessManager);
    }

    public Object apply(Object obj) {
        if (obj == null) {
            return null;
        }
        if (!obj.getClass().isArray()) {
            throw new ResultFilterException("array filter cannot be applied to a non-array object");
        }
        ArrayList<Object> list = new ArrayList<Object>();
        for (int i = 0; i < Array.getLength(obj); i++) {
            Object o = Array.get(obj, i);
            if (o == null) {
                list.add(o);
                continue;
            }
            o = super.apply(o);
            if (o != null) {
                list.add(o);
            }
        }
        return createResultArray(obj.getClass().getComponentType(), list);
    }

    private Object createResultArray(Class<?> arrayComponentType, ArrayList<Object> list) {
        Object resultArray = Array.newInstance(arrayComponentType, list.size());
        for (int i = 0; i < list.size(); i++) {
            Array.set(resultArray, i, list.get(i));
        }
        return resultArray;
    }
    
}

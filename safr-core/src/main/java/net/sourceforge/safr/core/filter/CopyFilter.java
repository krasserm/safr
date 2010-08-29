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

import java.util.Collection;

import net.sourceforge.safr.core.provider.AccessManager;

/**
 * A security filter that operates on collection types. This class doesn't
 * support filtering of nested collections. A nested collection is treated like
 * a simple object.
 * 
 * @author Martin Krasser
 */
class CopyFilter extends ObjectFilter {

    private Class<? extends Collection> resultClass;
    
    public CopyFilter(AccessManager accessManager, Class<? extends Collection> resultClass) {
        super(accessManager);
        this.resultClass = resultClass;
    }

    public Object apply(Object obj) {
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof Iterable)) {
            throw new ResultFilterException("copy filter cannot be applied to a non-iterable object");
        }
        Collection result = createResultCollection();
        for (Object elem : (Iterable)obj) {
            Object o = elem;
            if (o == null) {
                result.add(o);
                continue;
            }
            o = super.apply(o);
            if (o != null) {
                result.add(o);
            }
        }
        return result;
    }

    private Collection<?> createResultCollection() {
        try {
            return resultClass.newInstance();
        } catch (Exception e) {
            throw new ResultFilterException("cannot instantiate result collection", e);
        }
    }
    
}

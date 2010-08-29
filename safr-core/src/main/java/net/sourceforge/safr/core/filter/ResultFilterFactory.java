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

import java.lang.reflect.Method;
import java.util.Collection;

import net.sourceforge.safr.core.attribute.FilterAttribute;
import net.sourceforge.safr.core.provider.AccessManager;

/**
 * @author Martin Krasser
 */
public abstract class ResultFilterFactory {

    private ResultFilter arrayFilter;
    private ResultFilter objectFilterNullify;
    private ResultFilter objectFilterException;
    
    private AccessManager accessManager;
    
    public ResultFilterFactory(AccessManager accessManager) {
        this.accessManager = accessManager;
        this.arrayFilter = new ArrayFilter(accessManager);
        this.objectFilterNullify = new ObjectFilter(accessManager);
        this.objectFilterException = new ObjectFilter(accessManager, false);
    }

    public AccessManager getAccessManager() {
        return accessManager;
    }

    public ResultFilter getResultFilter(Method method, FilterAttribute attribute) {
        if (method.getReturnType().isArray()) {
            return arrayFilter;
        } else if (Collection.class.isAssignableFrom(method.getReturnType())) {
            return doGetResultFilter(method, attribute);
        } else if (attribute.isNullifyResult()){
            return objectFilterNullify;
        } else {
            return objectFilterException;
        }
    }
    
    protected abstract ResultFilter doGetResultFilter(Method method, FilterAttribute attribute);
    
}

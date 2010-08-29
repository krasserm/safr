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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sourceforge.safr.core.attribute.FilterAttribute;
import net.sourceforge.safr.core.provider.AccessManager;

/**
 * @author Martin Krasser
 */
public class CopyFilterFactory extends ResultFilterFactory {

    private Map<Class<?>, CopyFilter> defaultCollectionFilters;
    private Map<Class<?>, CopyFilter> customCollectionFilters;
    
    public CopyFilterFactory(AccessManager accessManager) {
        super(accessManager);
        defaultCollectionFilters = new HashMap<Class<?>, CopyFilter>();
        customCollectionFilters = new HashMap<Class<?>, CopyFilter>();
        
        // default result collection classes
        defaultCollectionFilters.put(Collection.class, new CopyFilter(accessManager, ArrayList.class));
        defaultCollectionFilters.put(List.class, new CopyFilter(accessManager, ArrayList.class));
        defaultCollectionFilters.put(Set.class, new CopyFilter(accessManager, HashSet.class));
        defaultCollectionFilters.put(SortedSet.class, new CopyFilter(accessManager, TreeSet.class));
    }

    @Override
    protected ResultFilter doGetResultFilter(Method method, FilterAttribute attribute) {
        Class<? extends Collection> clazz = attribute.getResultCollectionClass(); 
        if (clazz == null) {
            return getCollectionFilter(method);
        } else {
            return getCollectionFilter(clazz);
        }
    }

    private synchronized ResultFilter getCollectionFilter(Class<? extends Collection> clazz) {
        CopyFilter filter = customCollectionFilters.get(clazz);
        if (filter == null) {
            filter = new CopyFilter(getAccessManager(), clazz);
            customCollectionFilters.put(clazz, filter);
        }
        return filter;
    }
    
    private ResultFilter getCollectionFilter(Method method) {
        ResultFilter filter = defaultCollectionFilters.get(method.getReturnType());
        if (filter == null) {
            throw new ResultFilterException(
                    "No default filter defined for " + method.getReturnType() + ". " + 
                    "Define one using @Filter(resultCollectionClass=...) or do not " +
                    "copy the result collection using @Filter(copyResultCollection=false)");
        }
        return filter;
    }
    
}

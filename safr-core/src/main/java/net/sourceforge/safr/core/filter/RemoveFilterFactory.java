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

import net.sourceforge.safr.core.attribute.FilterAttribute;
import net.sourceforge.safr.core.provider.AccessManager;

/**
 * @author Martin Krasser
 */
public class RemoveFilterFactory extends ResultFilterFactory {

    private RemoveFilter removeFilter;
    
    public RemoveFilterFactory(AccessManager accessManager) {
        super(accessManager);
        removeFilter = new RemoveFilter(accessManager);
    }
    
    @Override
    protected ResultFilter doGetResultFilter(Method method, FilterAttribute attribute) {
        return removeFilter;
    }

}

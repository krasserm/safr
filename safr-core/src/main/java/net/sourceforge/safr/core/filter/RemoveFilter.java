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

import java.util.Iterator;

import net.sourceforge.safr.core.provider.AccessManager;

/**
 * @author Martin Krasser
 */
public class RemoveFilter extends ObjectFilter {

    public RemoveFilter(AccessManager accessManager) {
        super(accessManager);
    }

    @Override
    public Object apply(Object obj) {
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof Iterable)) {
            throw new ResultFilterException("remove filter cannot be applied to a non-iterable object");
        }
        Iterator<?> iter = ((Iterable<?>)obj).iterator();
        while (iter.hasNext()) {
            Object o = iter.next();
            if (o == null) {
                continue;
            }
            o = super.apply(o);
            if (o == null) {
                iter.remove();
            }
        }
        return obj;
    }

}

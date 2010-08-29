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
package net.sourceforge.safr.core.annotation.info;

import java.util.Collection;

import net.sourceforge.safr.core.annotation.Filter;
import net.sourceforge.safr.core.annotation.Undefined;
import net.sourceforge.safr.core.attribute.FilterAttribute;

/**
 * Internal representation of a {@link Filter} annotation.
 * 
 * @author Martin Krasser
 */
public class FilterAnnotationInfo implements FilterAttribute {

    private Class<? extends Collection> resultCollectionClass;
    
    private boolean copyResultCollection;
    
    private boolean nullifyResult;
    
    public FilterAnnotationInfo(Filter annotation) {
        init(annotation);
    }
    
    public boolean isNullifyResult() {
        return nullifyResult;
    }
    
    public boolean isCopyResultCollection() {
        return copyResultCollection;
    }

    public Class<? extends Collection> getResultCollectionClass() {
        return resultCollectionClass;
    }

    private void init(Filter annotation) {
        Class<? extends Collection> clazz = annotation.resultCollectionClass();
        resultCollectionClass = Undefined.class.equals(clazz) ? null : clazz;
        copyResultCollection = annotation.copyResultCollection();
        nullifyResult = annotation.nullifyResult();
    }

}

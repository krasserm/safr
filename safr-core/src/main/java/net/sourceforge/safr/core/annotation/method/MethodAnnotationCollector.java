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
package net.sourceforge.safr.core.annotation.method;

import java.lang.reflect.Method;

import net.sourceforge.safr.core.annotation.Filter;
import net.sourceforge.safr.core.annotation.Secure;
import net.sourceforge.safr.core.annotation.info.FilterAnnotationInfo;
import net.sourceforge.safr.core.annotation.info.SecureAnnotationInfo;
import net.sourceforge.safr.core.attribute.FilterAttribute;
import net.sourceforge.safr.core.attribute.SecureAttribute;
import net.sourceforge.safr.core.attribute.method.MethodAttributeCollector;
import net.sourceforge.safr.core.attribute.method.MethodAttributeContainer;

/**
 * @author Martin Krasser
 */
public class MethodAnnotationCollector extends MethodAttributeCollector {

    @Override
    public boolean visit(Method m) {
        MethodAttributeContainer container = getMethodAttributeContainer();
        container.clear();
        container.setMethodFilterAttribute(getMethodFilterAttribute(m));
        container.setMethodSecureAttribute(getMethodSecureAttribute(m));
        container.setParameterSecureAttributes(getParameterSecureAttributes(m));
        return container.isAnyMethodAttributeDefined();
    }

    private static FilterAttribute getMethodFilterAttribute(Method m) {
        return createFilterAttribute(m.getAnnotation(Filter.class));
    }

    private static SecureAttribute getMethodSecureAttribute(Method m) {
        return createSecureAttribute(m.getAnnotation(Secure.class));
    }

    private static SecureAttribute getParameterSecureAttribute(Method m, int index) {
        return createSecureAttribute(MethodAnnotationUtil.getParameterSecureAnnotation(m, index));
    }
    
    private static SecureAttribute[] getParameterSecureAttributes(Method m) {
        int parameterCount = m.getParameterTypes().length;
        SecureAttribute[] attributes = new SecureAttribute[parameterCount];
        for (int i = 0; i < parameterCount; i++) {
            attributes[i] = getParameterSecureAttribute(m, i); 
        }
        return attributes;
    }
    
    private static FilterAttribute createFilterAttribute(Filter annotation) {
        if (annotation == null) {
            return null;
        }
        return new FilterAnnotationInfo(annotation);
    }
    
    private static SecureAttribute createSecureAttribute(Secure annotation) {
        if (annotation == null) {
            return null;
        }
        return new SecureAnnotationInfo(annotation);
    }

}

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
package net.sourceforge.safr.core.attribute.method;

import net.sourceforge.safr.core.attribute.FilterAttribute;
import net.sourceforge.safr.core.attribute.SecureAttribute;

/**
 * @author Martin Krasser
 */
public class MethodAttributeContainer {

    private FilterAttribute methodFilterAttribute;
    
    private SecureAttribute methodSecureAttribute;
    
    private SecureAttribute[] parameterSecureAttributes;
    
    public FilterAttribute getMethodFilterAttribute() {
        return methodFilterAttribute;
    }

    public void setMethodFilterAttribute(FilterAttribute methodFilterAttribute) {
        this.methodFilterAttribute = methodFilterAttribute;
    }

    public SecureAttribute getMethodSecureAttribute() {
        return methodSecureAttribute;
    }

    public void setMethodSecureAttribute(SecureAttribute methodSecureAttribute) {
        this.methodSecureAttribute = methodSecureAttribute;
    }

    public SecureAttribute[] getParameterSecureAttributes() {
        return parameterSecureAttributes;
    }

    public void setParameterSecureAttributes(
            SecureAttribute[] parameterSecureAttributes) {
        this.parameterSecureAttributes = parameterSecureAttributes;
    }

    public boolean isAnyMethodAttributeDefined() {
        return isMethodFilterAttributeDefined()
            || isMethodSecureAttributeDefined()
            || isAnyParameterSecureAttributeDefined();
    }
    
    public boolean isMethodFilterAttributeDefined() {
        return methodFilterAttribute != null;
    }
    
    public boolean isMethodSecureAttributeDefined() {
        return methodSecureAttribute != null;
    }
    
    public boolean isAnyParameterSecureAttributeDefined() {
        if (parameterSecureAttributes == null) {
            return false;
        }
        for (SecureAttribute attribute : parameterSecureAttributes) {
            if (attribute != null) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        setMethodFilterAttribute(null);
        setMethodSecureAttribute(null);
        setParameterSecureAttributes(null);
    }
    
}

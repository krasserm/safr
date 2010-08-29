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
package net.sourceforge.safr.core.spring.advice;

import java.lang.reflect.Method;

import net.sourceforge.safr.core.attribute.SecurityAttributeSource;

import org.springframework.aop.support.StaticMethodMatcherPointcut;

/**
 * @author Martin Krasser
 */
class SecurityAttributeSourcePointcut extends StaticMethodMatcherPointcut {

    private SecurityAttributeSource source;
    
    public void setSecurityAttributeSource(SecurityAttributeSource source) {
        this.source = source;
    }
    
    public boolean matches(Method method, Class targetClass) {
        if (source == null) {
            return false;
        }
        return source.isAnyMethodAttributeDefined(method, targetClass);
    }

}

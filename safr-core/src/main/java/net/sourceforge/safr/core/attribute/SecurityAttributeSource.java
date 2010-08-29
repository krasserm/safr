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
package net.sourceforge.safr.core.attribute;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Source of security attributes on methods, parameters and fields.
 * 
 * @author Martin Krasser
 */
public interface SecurityAttributeSource {

    /**
     * Returns the {@link EncryptAttribute} defined for <code>field</code>.
     * 
     * @param field
     *            field to be inspected for {@link EncryptAttribute}
     *            definitions.
     * @return {@link EncryptAttribute} or <code>null</code>.
     */
    EncryptAttribute getFieldEncryptAttribute(Field field);
    
    /**
     * Returns the {@link FilterAttribute} defined for <code>method</code> on
     * <code>targetClass</code>. If no security attribute is defined for
     * <code>method</code> on <code>targetClass</code> the
     * {@link FilterAttribute} is inherited from a superclass or interface.
     * Having any other security attributes defined for <code>method</code> on
     * <code>targetClass</code> then no attempt is made to inherit a
     * {@link FilterAttribute}.
     * 
     * @param method
     *            method to be inspected for {@link FilterAttribute}
     *            definitions.
     * @param targetClass
     *            class where inspection should start (before visiting
     *            superclasses or interfaces)
     * @return a {@link FilterAttribute} or <code>null</code>.
     */
    FilterAttribute getMethodFilterAttribute(Method method, Class<?> targetClass);

    /**
     * Returns the {@link SecureAttribute} defined for <code>method</code> on
     * <code>targetClass</code>. If no security attribute is defined for
     * <code>method</code> on <code>targetClass</code> the
     * {@link SecureAttribute} is inherited from a superclass or interface.
     * Having any other security attributes defined for <code>method</code> on
     * <code>targetClass</code> then no attempt is made to inherit a
     * {@link SecureAttribute}.
     * 
     * @param method
     *            method to be inspected for {@link SecureAttribute}
     *            definitions.
     * @param targetClass
     *            class where inspection should start (before visiting
     *            superclasses or interfaces)
     * @return a {@link SecureAttribute} or <code>null</code>.
     */
    SecureAttribute getMethodSecureAttribute(Method method, Class<?> targetClass);

    /**
     * Returns the {@link SecureAttribute}s defined for parameters of
     * <code>method</code> on <code>targetClass</code>. If no security
     * attribute is defined for <code>method</code> on
     * <code>targetClass</code> the {@link SecureAttribute}s are inherited
     * from a superclass or interface. Having any other security attributes
     * defined for <code>method</code> on <code>targetClass</code> then no
     * attempt is made to inherit a {@link SecureAttribute}.
     * 
     * @param method
     *            method to be inspected for {@link SecureAttribute}
     *            definitions.
     * @param targetClass
     *            class where inspection should start (before visiting
     *            superclasses or interfaces)
     * @return a {@link SecureAttribute} or <code>null</code>.
     */
    SecureAttribute[] getParameterSecureAttributes(Method method, Class<?> targetClass);

    /**
     * Returns <code>true</code> if any security attribute is defined for
     * <code>method</code> on <code>targetClass</code> or was inherited
     * from a superclass or interface.
     * 
     * @param method
     *            method to be inspected for any security attribute definition.
     * @param targetClass
     *            class where inspection should start (before visiting
     *            superclasses or interfaces)
     * @return <code>true</code> if any security attribute is defined.
     */
    boolean isAnyMethodAttributeDefined(Method method, Class<?> targetClass);
    
}

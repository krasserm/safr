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
package net.sourceforge.safr.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sourceforge.safr.core.filter.ResultFilterException;
import net.sourceforge.safr.core.provider.AccessManager;

/**
 * Enforces permission checks on objects returned from method invocations. The
 * implied action is {@link SecureAction#READ}. If the caller has no read
 * permission to the returned object then <code>null</code> will be returned.
 * If the return type is a (subtype of) {@link Collection} or an array type then
 * permission checks are performed for the collection- or array elements. If the
 * caller has no read permission to an element it won't be part of the result.
 * If all elements have been filtered out an empty collection or an empty array
 * will be returned. <code>null</code> elements in the original collection or
 * array are never filtered out.
 * 
 * @see AccessManager
 * 
 * @author Martin Krasser
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( ElementType.METHOD)
public @interface Filter {

    /**
     * If a method returns a single object (not a collection) for which no read
     * access is granted <code>null</code> is returned by default. If an
     * {@link AccessControlException} shall be thrown instead of returning
     * <code>null</code> set this element to <code>false</code>.
     * 
     * @return <code>true</code> to nullify the result object and
     *         <code>false</code> to throw an {@link AccessControlException}.
     */
    boolean nullifyResult() default true;
    
    /**
     * Defines whether the returned collection- or array instance is a copy of
     * the original instance. The default is <code>true</code>. This is
     * convenient e.g. for persistent collections that should not be modified by
     * filtering actions. Use <code>false</code> if elements shall be removed
     * from the original collection. This annotation element has no effect if
     * the method's return type is not a (subtype of) {@link Collection} or an
     * array type.
     * 
     * @return <code>true</code> to create a copy of the original collection
     *         or array.
     */
    boolean copyResultCollection() default true; 
    
    /**
     * If a method's return type is a (subtype of) {@link Collection} and
     * {@link #copyResultCollection()} is set to <code>true</code> then this
     * element type defines the implementation class of the collection to be
     * returned. If this annotation element is not set the following default
     * settings apply.
     * <p>
     * <ul>
     * <li>If the return type is {@link Collection} then an instance of
     * {@link ArrayList} will be returned.</li>
     * <li>If the return type is {@link List} then an instance of
     * {@link ArrayList} will be returned.</li>
     * <li>If the return type is {@link Set} then an instance of
     * {@link HashSet} will be returned.</li>
     * <li>If the return type is {@link SortedSet} then an instance of
     * {@link TreeSet} will be returned.</li>
     * </ul>
     * <p>
     * The default setting can be overridden. For all other return types this
     * annotation element must be defined otherwise a
     * {@link ResultFilterException} will be thrown.
     * 
     * @return the implementation class of the collection object to be returned
     *         from a method invocation.
     */
    Class<? extends Collection> resultCollectionClass() default Undefined.class;
    
}

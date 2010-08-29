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
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import net.sourceforge.safr.core.attribute.field.FieldAttributeCollector;
import net.sourceforge.safr.core.attribute.field.FieldAttributeContainer;
import net.sourceforge.safr.core.attribute.field.FieldAttributeHierarchy;
import net.sourceforge.safr.core.attribute.method.MethodAttributeCollector;
import net.sourceforge.safr.core.attribute.method.MethodAttributeContainer;
import net.sourceforge.safr.core.attribute.method.MethodAttributeHierarchy;

/**
 * @author Martin Krasser
 */
public abstract class SecurityAttributeSourceSupport implements SecurityAttributeSource {

    private ConcurrentCache<String, MethodAttributeContainer> maCache;
    private ConcurrentCache<Field, FieldAttributeContainer> faCache;
    
    public SecurityAttributeSourceSupport() {
        this.maCache = new ConcurrentCache<String, MethodAttributeContainer>(); 
        this.faCache = new ConcurrentCache<Field, FieldAttributeContainer>(); 
    }
    
    public EncryptAttribute getFieldEncryptAttribute(Field field) {
        return getFieldAttributeContainer(field).getFieldEncryptAttribute();
    }

    public FilterAttribute getMethodFilterAttribute(Method method, Class<?> targetClass) {
        return getMethodAttributeContainer(method, targetClass).getMethodFilterAttribute();
    }

    public SecureAttribute getMethodSecureAttribute(Method method, Class<?> targetClass) {
        return getMethodAttributeContainer(method, targetClass).getMethodSecureAttribute();
    }

    public SecureAttribute[] getParameterSecureAttributes(Method method, Class<?> targetClass) {
        return getMethodAttributeContainer(method, targetClass).getParameterSecureAttributes();
    }
    
    public boolean isAnyMethodAttributeDefined(Method method, Class<?> targetClass) {
        return getMethodAttributeContainer(method, targetClass).isAnyMethodAttributeDefined();
    }

    private FieldAttributeContainer getFieldAttributeContainer(Field field) {
        FieldAttributeContainer container = faCache.get(field);
        if (container != null) {
            return container;
        }
        container = computeFieldAttributes(field);
        // Only calls to annotated methods are intercepted. Therefore, 
        // containers without attributes need not be added to the cache.
        if (container.isAnyFieldAttributeDefined()) {
            faCache.put(field, container);
        }
        return container;
        
    }
    
    private MethodAttributeContainer getMethodAttributeContainer(Method method, Class<?> targetClass) {
        String key = generateKey(method, targetClass);
        MethodAttributeContainer container = maCache.get(key);
        if (container != null) {
            return container;
        }
        container = computeMethodAttributes(method, targetClass);
        // Only calls to annotated methods are intercepted. Therefore, 
        // containers without attributes need not be added to the cache.
        if (container.isAnyMethodAttributeDefined()) {
            maCache.put(key, container);
        }
        return container;
    }

    private FieldAttributeContainer computeFieldAttributes(Field field) {
        FieldAttributeHierarchy hierarchy = new FieldAttributeHierarchy(field);
        FieldAttributeCollector collector = createFieldAttributeCollector();
        hierarchy.accept(collector);
        return collector.getFieldAttributeContainer();
    }
    
    private MethodAttributeContainer computeMethodAttributes(Method method, Class<?> targetClass) {
        MethodAttributeHierarchy hierarchy = new MethodAttributeHierarchy(getMostSpecificMethod(method, targetClass));
        MethodAttributeCollector collector = createMethodAttributeCollector();
        hierarchy.accept(collector);
        return collector.getMethodAttributeContainer();
    }
    
    protected abstract FieldAttributeCollector createFieldAttributeCollector();
    
    protected abstract MethodAttributeCollector createMethodAttributeCollector();
    
    private static String generateKey(Method method, Class<?> targetClass) {
        return targetClass + "." + method;
    }

    private static Method getMostSpecificMethod(Method method, Class<?> targetClass) {
        if (method == null || targetClass == null) {
            return method;
        }
        try {
            String name = method.getName();
            Class<?>[] types = method.getParameterTypes();
            method = targetClass.getMethod(name, types);
        } catch (NoSuchMethodException ex) {
            // use original method
        }
        return method;
    }
    
    /**
     * Unlike {@link ConcurrentHashMap} this implementation allows
     * <code>null</code> values.
     * 
     * @author Martin Krasser
     * 
     * @param <K>
     *            key type
     * @param <V>
     *            value type
     */
    @SuppressWarnings("serial")
    private static final class ConcurrentCache<K, V> extends HashMap<K, V> {

        private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
        private final Lock r = rwl.readLock();
        private final Lock w = rwl.writeLock();
        
        @Override
        public V get(Object key) {
            r.lock();
            try {
                return super.get(key);
            } finally {
                r.unlock();
            }
        }

        @Override
        public V put(K key, V value) {
            w.lock();
            try {
                return super.put(key, value);
            } finally {
                w.unlock();
            }
        }
        
    }
    
}

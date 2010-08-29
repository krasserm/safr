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

import net.sourceforge.safr.core.provider.CryptoProvider;

/**
 * Enforces a field value encryption operation on field write-access and a field
 * value decryption operation on field read-access. Crypto operations are
 * delegated to {@link CryptoProvider} instances.
 * 
 * @see CryptoProvider
 * 
 * @author Martin Krasser
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( ElementType.FIELD)
public @interface Encrypt {
    
    /**
     * Hints to a {@link CryptoProvider}. A hint is defined in the format
     * 
     * <pre>
     * hintkey = hintValue
     * </pre>
     * 
     * Whitespaces are not significant. Hints are provided to the
     * {@link CryptoProvider} as {@link Map}. If a hint doesn't define a
     * <code>hintValue</code> then the value of the corresponding map entry is
     * <code>null</code>.
     * 
     * @return an array of hints.
     */
    String[] hints() default {};
    
}

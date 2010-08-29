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
package net.sourceforge.safr.core.provider;

import java.util.Map;

import net.sourceforge.safr.core.annotation.Encrypt;

/**
 * Implemented by classes that provide encryption and decryption services. 
 * 
 * @author Martin Krasser
 */
public interface CryptoProvider {

    /**
     * Encrypts a value.
     * 
     * @param value
     *            value to be encrypted. This is usually the value to be set on
     *            a field annotated with {@link Encrypt}.
     * @param context
     *            the value`s context. This is the object on which the encrypted
     *            field value is set.
     * @param hints
     *            provider hints for crypto operations. The map is read-only.
     * @return the encrypted value.
     */
    public Object encrypt(Object value, Object context, Map<String, String> hints);
    
    /**
     * Decrypts a value.
     * 
     * @param value
     *            value to be decrypted. This is usually the value of a field
     *            annotated with {@link Encrypt}.
     * @param context
     *            the value`s context. This is the object from which the
     *            encrypted field value is retrieved.
     * @param hints
     *            provider hints for crypto operations. The map is read-only.
     * @return the decrypted value.
     */
    public Object decrypt(Object value, Object context, Map<String, String> hints);
    
}

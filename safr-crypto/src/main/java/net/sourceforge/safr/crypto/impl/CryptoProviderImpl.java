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
package net.sourceforge.safr.crypto.impl;

import java.util.Map;

import org.jasypt.util.text.BasicTextEncryptor;

import net.sourceforge.safr.core.provider.CryptoProvider;
import net.sourceforge.safr.core.spring.annotation.CryptographicServiceProvider;

/**
 * @author Martin Krasser
 */
@CryptographicServiceProvider
public class CryptoProviderImpl implements CryptoProvider {

    private BasicTextEncryptor encryptor;
    
    public CryptoProviderImpl() {
        encryptor = new BasicTextEncryptor();
    }
    
    public void setSecret(String secret) {
        encryptor.setPassword(secret);
    }
    
    public Object decrypt(Object value, Object context, Map<String, String> hints) {
        return encryptor.decrypt((String)value);
    }

    public Object encrypt(Object value, Object context, Map<String, String> hints) {
        return encryptor.encrypt((String)value);
    }

}

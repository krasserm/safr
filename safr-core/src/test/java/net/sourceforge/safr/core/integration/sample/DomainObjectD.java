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
package net.sourceforge.safr.core.integration.sample;

import java.lang.reflect.Field;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.sourceforge.safr.core.annotation.Encrypt;
import net.sourceforge.safr.core.annotation.SecureObject;

/**
 * @author Martin Krasser
 */
@SecureObject
@XmlRootElement(name="domainObjectD")
@XmlAccessorType(XmlAccessType.FIELD)
public class DomainObjectD {

    private static Field FIELD_S;
    
    static {
        
        // Please note that this is only necessary for field access via
        // reflection. Field access via reflection is only done for testing
        // purposes here.

        for (Field field : DomainObjectD.class.getDeclaredFields()) {
            if (field.getName().equals("s")) {
                FIELD_S = field;
            }
        }
        
        FIELD_S.setAccessible(true);
    }
    
    @Encrypt
    @XmlElement
    private String s;
    
    public DomainObjectD() {
        this(null);
    }
    
    public DomainObjectD(String s) {
        this.s = s;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
    
    public void setSReflective(String s) throws Exception {
        FIELD_S.set(this, s);
    }
    
    public String getSReflective() throws Exception {
        return (String)FIELD_S.get(this);
    }
    
}

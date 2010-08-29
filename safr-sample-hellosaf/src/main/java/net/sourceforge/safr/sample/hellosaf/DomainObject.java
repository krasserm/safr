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
package net.sourceforge.safr.sample.hellosaf;

import net.sourceforge.safr.core.annotation.Secure;
import net.sourceforge.safr.core.annotation.SecureAction;
import net.sourceforge.safr.core.annotation.SecureObject;

/**
 * @author Martin Krasser
 */
@SecureObject
public class DomainObject {

    private long id;
    
    public DomainObject(long id) {
        this.id = id;
    }
    
    public long getId() {
        return id;
    }
    
    @Secure(SecureAction.UPDATE)
    public void update() {
        // update (modify) this domain object ...
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DomainObject)) {
            return false;
        }
        DomainObject o = (DomainObject)obj;
        return id == o.id;
    }

    @Override
    public int hashCode() {
        return (int)(id^(id>>>32)); 
    }

    @Override
    public String toString() {
        return "DomainObject (id=" + id + ")";
    }
    
}

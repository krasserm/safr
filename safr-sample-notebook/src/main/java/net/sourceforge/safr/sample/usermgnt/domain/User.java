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
package net.sourceforge.safr.sample.usermgnt.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.sourceforge.safr.core.annotation.Secure;
import net.sourceforge.safr.core.annotation.SecureAction;
import net.sourceforge.safr.core.annotation.SecureObject;

/**
 * @author Martin Krasser
 */
@SecureObject
public class User {

    private String id;
    
    private String firstname;
    
    private String lastname;

    private Set<Role> roles;
    
    public User(String id) {
        this.id = id;
        this.roles = new HashSet<Role>();
    }

    public String getId() {
        return id;
    }

    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    @Secure(SecureAction.CUSTOM_BEFORE)
    public void addToRole(Role role) {
        roles.add(role);
    }
    
    @Secure(SecureAction.CUSTOM_BEFORE)
    public void removeFromRole(Role role) {
        roles.remove(role);        
    }
    
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void bootstrap() {
        this.roles.add(new Role("customer"));
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User u = (User)obj;
        return id.equals(u.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
}

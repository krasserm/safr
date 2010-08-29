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
package net.sourceforge.safr.jaas.policy;

import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.ProtectionDomain;

import net.sourceforge.safr.jaas.permission.InstancePermission;
import net.sourceforge.safr.jaas.permission.PermissionManager;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Martin Krasser
 */
public class InstancePolicy extends Policy {
    
    @Autowired
    private PermissionManager permissionManager;
    
    private Policy defaultPolicy;

    public Policy getDefaultPolicy() {
        return defaultPolicy;
    }
    
    public void setDefaultPolicy(Policy defaultPolicy) {
        this.defaultPolicy = defaultPolicy;
    }
    
    public void setPermissionManager(PermissionManager permissionManager) {
        this.permissionManager = permissionManager;
    }
    
    @Override
    public boolean implies(ProtectionDomain domain, Permission permission) {
        if (!(permission instanceof InstancePermission)) {
            return defaultPolicy.implies(domain, permission);
        }
        return permissionManager.implies(permission, domain.getPrincipals());
    }

    @Override
    public PermissionCollection getPermissions(CodeSource codesource) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void refresh() {
        throw new UnsupportedOperationException("not implemented");
    }

}

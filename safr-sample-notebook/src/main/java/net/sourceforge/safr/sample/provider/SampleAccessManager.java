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
package net.sourceforge.safr.sample.provider;

import java.security.Permission;
import java.util.Collection;

import net.sourceforge.safr.core.invocation.MethodInvocation;
import net.sourceforge.safr.core.spring.annotation.AuthorizationServiceProvider;
import net.sourceforge.safr.jaas.permission.Action;
import net.sourceforge.safr.jaas.permission.InstancePermission;
import net.sourceforge.safr.jaas.permission.PermissionManager;
import net.sourceforge.safr.jaas.permission.Target;
import net.sourceforge.safr.jaas.principal.RolePrincipal;
import net.sourceforge.safr.sample.notebook.domain.Notebook;
import net.sourceforge.safr.sample.usermgnt.domain.Role;
import net.sourceforge.safr.sample.usermgnt.domain.User;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Martin Krasser
 */
@AuthorizationServiceProvider
public class SampleAccessManager extends AccessManagerSupport {

    private static final String NOTEBOOK_CLASS = Notebook.class.getName();
    private static final String WILDCARD_TOKEN = Target.WILDCARD;
    
    @Autowired
    private PermissionManager permissionManager;
    
    public void setPermissionManager(PermissionManager permissionManager) {
        this.permissionManager = permissionManager;
    }
    
    public void checkCreate(Object obj) {
        checkPermission(createNotebookPermission(obj, Action.MANAGE));
    }

    public void checkRead(Object obj) {
        checkPermission(createNotebookPermission(obj, Action.READ));
    }

    public void checkUpdate(Object obj) {
        checkPermission(createNotebookPermission(obj, Action.WRITE));
    }

    public void checkDelete(Object obj) {
        checkPermission(createNotebookPermission(obj, Action.MANAGE));
    }

    public void checkCustomBefore(MethodInvocation invocation) {
        Role role = (Role)invocation.getArguments()[0];
        if (role == null) {
            return;
        }
        RolePrincipal rp =  new RolePrincipal(role.getId());
        Collection<InstancePermission> ips = permissionManager.getPermissions(rp);
        for (InstancePermission ip : ips) {
            checkPermission(ip.createAuthorizationPermission());
        }
    }

    private void checkPermission(Permission permission) {
        permissionManager.checkPermission(permission);
    }
    
    private static Permission createNotebookPermission(Object obj, Action action) {
        Notebook nb = (Notebook)obj;
        String context = getOwnerId(nb.getOwner());
        Target t = new Target(context, NOTEBOOK_CLASS, nb.getId());
        return new InstancePermission(t, action);
    }
    
    private static String getOwnerId(User owner) {
        if (owner == null) {
            return WILDCARD_TOKEN;
        }
        return owner.getId();
    }
    
}

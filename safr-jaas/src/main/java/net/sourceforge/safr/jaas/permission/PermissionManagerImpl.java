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
package net.sourceforge.safr.jaas.permission;

import java.security.AccessControlException;
import java.security.AccessController;
import java.security.Permission;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.security.auth.Subject;

import net.sourceforge.safr.jaas.principal.RolePrincipal;
import net.sourceforge.safr.jaas.principal.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Martin Krasser
 */
@Component
public class PermissionManagerImpl implements PermissionManager {

    private PermissionMap userPermissions;
    private PermissionMap rolePermissions;

    @Autowired
    private PermissionSource permissionSource;
    
    private boolean activated;
    
    public PermissionManagerImpl() {
        this.activated = false;
        this.userPermissions = new PermissionMap();
        this.rolePermissions = new PermissionMap();
    }
    
    public void setPermissionSource(PermissionSource permissionSource) {
        this.permissionSource = permissionSource;
    }
    
    @PostConstruct
    public void initialize() {
        userPermissions.putPermissions(permissionSource.getUserPermissions());
        rolePermissions.putPermissions(permissionSource.getRolePermissions());
        activated = true;
    }
    
    public void checkPermission(Permission permission) {
        Subject current = Subject.getSubject(AccessController.getContext());
        if (!implies(permission, current) && activated) {
            throw new AccessControlException("access denied", permission);
        }
    }

    public boolean implies(Permission permission, Subject subject) {
        Principal[] principals = null;
        if (subject == null) {
            principals = new Principal[0];
        } else {
            principals = new Principal[subject.getPrincipals().size()];
            subject.getPrincipals().toArray(principals);
        }
        return implies(permission, principals);
    }
    
    public boolean implies(Permission permission, Principal... principals) {
        if (!(permission instanceof InstancePermission)) {
            return false;
        }
        if (userPermissionsImply(permission, getUserId(principals))) {
            return true;
        }
        return rolePermissionsImply(permission, getRoleIds(principals));
    }
    
    public Collection<InstancePermission> getPermissions(Principal principal) {
        return Collections.unmodifiableList(getPermissionMap(principal).getEager(principal.getName()));
    }
    
    public PermissionManagementLog newPermissionManagementLog() {
        return new PermissionManagementLogImpl(this);
    }
    
    public void grantPermission(Principal principal, InstancePermission permission) {
        checkInstancePermission(permission);
        grantPermissionNoCheck(principal, permission);
    }
    
    public void revokePermission(Principal principal, InstancePermission permission) {
        checkInstancePermission(permission);
        revokePermissionNoCheck(principal, permission);
    }
    
    private void grantPermissionNoCheck(Principal principal, InstancePermission permission) {
        getPermissionMap(principal).getEager(principal.getName()).add(permission);
    }
    
    private void revokePermissionNoCheck(Principal principal, InstancePermission permission) {
        getPermissionMap(principal).getEager(principal.getName()).remove(permission);
    }
    
    private PermissionMap getPermissionMap(Principal principal) {
        if (principal instanceof UserPrincipal) {
            return userPermissions;
        }
        if (principal instanceof RolePrincipal) {
            return rolePermissions;
        }
        throw new IllegalArgumentException("unsupported principal class " + principal.getClass());
    }
    
    private boolean userPermissionsImply(Permission permission, String userId) {
        if (userId == null) {
            return false;
        }
        return userPermissions.getEager(userId).implies(permission);
        
    }
    
    private boolean rolePermissionsImply(Permission permission, List<String> roleIds) {
        for (String roleId : roleIds) {
            if (rolePermissions.getEager(roleId).implies(permission)) {
                return true;
            }
        }
        return false;
    }
    
    private void checkInstancePermission(InstancePermission permission) {
        checkPermission(permission.createAuthorizationPermission());
    }
    
    private static String getUserId(Principal... principals) {
        for (Principal p : principals) {
            if (p instanceof UserPrincipal) {
                return p.getName(); // return first
            }
        }
        return null;
    }

    private static List<String> getRoleIds(Principal... principals) {
        ArrayList<String> result = new ArrayList<String>(); 
        for (Principal p : principals) {
            if (p instanceof RolePrincipal) {
                result.add(p.getName());
            }
        }
        return result;
    }
    
    @SuppressWarnings("serial")
    private static class PermissionMap extends HashMap<String, PermissionList> {

        public void putPermissions(Map<? extends Principal, ? extends Collection<InstancePermission>> map) {
            for (Principal p : map.keySet()) {
                put(p.getName(), new PermissionList(map.get(p)));
            }
        }
        
        public synchronized PermissionList getEager(String userId) {
            PermissionList result = get(userId);
            if (result == null) {
                result = new PermissionList();
                put(userId, result);
            }
            return result;
        }
        
        @Override
        public synchronized void clear() {
            super.clear();
        }

    }
    
    @SuppressWarnings("serial")
    private static class PermissionList extends ArrayList<InstancePermission> {

        public PermissionList() {
            super();
        }
        
        public PermissionList(Collection<InstancePermission> c) {
            super(c);
        }
        
        @Override
        public synchronized boolean add(InstancePermission o) {
            return super.add(o);
        }

        @Override
        public synchronized boolean remove(Object o) {
            return super.remove(o);
        }

        public synchronized boolean implies(Permission permission) {
            for (Permission p : this) {
                if (p.implies(permission)) {
                    return true;
                }
            }
            return false;
        }

    }

    private class PermissionManagementLogImpl implements PermissionManagementLog {

        private PermissionManagerImpl manager;

        private Set<LogEntry> entries;
        
        public PermissionManagementLogImpl(PermissionManagerImpl manager) {
            this.manager = manager;
            this.entries = new HashSet<LogEntry>();
        }

        public void addGrantTask(Principal principal, InstancePermission permission) {
            entries.add(new LogEntry(true, principal, permission));
        }

        public void addRevokeTask(Principal principal, InstancePermission permission) {
            entries.add(new LogEntry(false, principal, permission));
        }

        public void commit() {
            for (LogEntry entry : entries) {
                checkInstancePermission(entry.getPermission());
            }
            for (LogEntry entry : entries) {
                if (entry.isGrant()) {
                    manager.grantPermissionNoCheck(entry.getPrincipal(), entry.getPermission());
                } else {
                    manager.revokePermissionNoCheck(entry.getPrincipal(), entry.getPermission());
                }
            }
        }
        
    }
    
    private static class LogEntry {
        
        private boolean grant;
        
        private Principal principal;
        
        private InstancePermission permission;

        public LogEntry(boolean grant, Principal principal, InstancePermission permission) {
            this.grant = grant;
            this.principal = principal;
            this.permission = permission;
        }

        public boolean isGrant() {
            return grant;
        }

        public Principal getPrincipal() {
            return principal;
        }
     
        public InstancePermission getPermission() {
            return permission;
        }
        
    }

}

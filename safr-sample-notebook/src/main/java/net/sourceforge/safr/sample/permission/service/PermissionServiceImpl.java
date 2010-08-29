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
package net.sourceforge.safr.sample.permission.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sourceforge.safr.jaas.permission.Action;
import net.sourceforge.safr.jaas.permission.InstancePermission;
import net.sourceforge.safr.jaas.permission.PermissionManagementLog;
import net.sourceforge.safr.jaas.permission.PermissionManager;
import net.sourceforge.safr.jaas.permission.PermissionSource;
import net.sourceforge.safr.jaas.permission.Target;
import net.sourceforge.safr.jaas.principal.RolePrincipal;
import net.sourceforge.safr.jaas.principal.UserPrincipal;
import net.sourceforge.safr.sample.notebook.domain.Notebook;
import net.sourceforge.safr.sample.notebook.service.NotebookService;
import net.sourceforge.safr.sample.permission.domain.PermissionAssignment;

/**
 * @author Martin Krasser
 */
@Service
public class PermissionServiceImpl implements PermissionService, PermissionSource {

    private static final String WILDCARD = Target.WILDCARD;
    private static final String NOTEBOOK = Notebook.class.getName();
    private static final String PUBLIC_ID = NotebookService.PUBLIC_NOTEBOOK_ID;

    @Autowired
    private PermissionManager permissionManager;
    
    public void setPermissionManager(PermissionManager permissionManager) {
        this.permissionManager = permissionManager;
    }
    
    public Collection<PermissionAssignment> getPermissionAssignments(String assigneeId) {
        ArrayList<PermissionAssignment> result = new ArrayList<PermissionAssignment>();
        UserPrincipal up = new UserPrincipal(assigneeId);
        for (InstancePermission ip : permissionManager.getPermissions(up)) {
            result.add(new PermissionAssignment(up.getName(), ip));
        }
        return null;
    }
    
    public PermissionAssignment getPermissionAssignment(String assigneeId, Notebook notebook) {
        UserPrincipal up = new UserPrincipal(assigneeId);
        for (InstancePermission ip : permissionManager.getPermissions(up)) {
            Target t = new Target(notebook.getOwner().getId(), NOTEBOOK, notebook.getId());
            if (!ip.getTarget().implies(t)) {
                continue;
            }
            return new PermissionAssignment(up.getName(), ip);
        }
        return null;
    }
    
    public void applyPermissionAssignments(PermissionAssignment... permissionAssignments) {
        PermissionManagementLog log = permissionManager.newPermissionManagementLog();
        for (PermissionAssignment npa : permissionAssignments) {
            applyPermissionAssignment(npa, log);
        }
        log.commit();
    }

    /**
     * Returns sample role permissions.
     * 
     * @return sample role permissions.
     */
    public Map<RolePrincipal, Set<InstancePermission>> getRolePermissions() {
        HashMap<RolePrincipal, Set<InstancePermission>> result = new HashMap<RolePrincipal, Set<InstancePermission>>();

        // define permissions for customer role
        HashSet<InstancePermission> permissions = new HashSet<InstancePermission>();
        permissions.add(new InstancePermission(new Target(WILDCARD, NOTEBOOK, PUBLIC_ID), Action.WRITE));
        result.put(new RolePrincipal("customer"), permissions);
        
        return result;
    }

    /**
     * Returns sample user permissions.
     * 
     * @return sample user permissions.
     */
    public Map<UserPrincipal, Set<InstancePermission>> getUserPermissions() {
        HashMap<UserPrincipal, Set<InstancePermission>> result = new HashMap<UserPrincipal, Set<InstancePermission>>();
        
        // define permissions for root
        HashSet<InstancePermission> permissions = new HashSet<InstancePermission>();
        permissions.add(new InstancePermission(new Target(), Action.AUTH));
        result.put(new UserPrincipal("root"), permissions);
        
        // define permissions for user1
        permissions = new HashSet<InstancePermission>();
        permissions.add(new InstancePermission(new Target("user1", NOTEBOOK, WILDCARD), Action.AUTH));
        result.put(new UserPrincipal("user1"), permissions);
        
        // define permissions for user2
        permissions = new HashSet<InstancePermission>();
        permissions.add(new InstancePermission(new Target("user2", NOTEBOOK, WILDCARD), Action.AUTH));
        result.put(new UserPrincipal("user2"), permissions);
        
        // define permissions for user3
        permissions = new HashSet<InstancePermission>();
        permissions.add(new InstancePermission(new Target("user3", NOTEBOOK, WILDCARD), Action.AUTH));
        result.put(new UserPrincipal("user3"), permissions);
        
        return result;
    }
    
    private void applyPermissionAssignment(PermissionAssignment npa, PermissionManagementLog log) {
        // Permission already granted to user
        InstancePermission ipCurrent = getInstancePermission(npa);
        // Permission to be granted to user
        InstancePermission ipDefined = npa.createInstancePermission();
        if (ipCurrent == null) {
            if (ipDefined != null) {
                log.addGrantTask(npa.createUserPrincipal(), ipDefined);
            }
        } else {
            if (ipDefined == null) {
                log.addRevokeTask(npa.createUserPrincipal(), ipCurrent);
            } else if (!ipCurrent.equals(ipDefined)) {
                log.addRevokeTask(npa.createUserPrincipal(), ipCurrent);
                log.addGrantTask(npa.createUserPrincipal(), ipDefined);
            }
        }
    }
    
    private InstancePermission getInstancePermission(PermissionAssignment npa) {
        Collection<InstancePermission> ips = null; 
        String userId = npa.getAssigneeId();
        String ntbkId = npa.getNotebookId();
        ips = permissionManager.getPermissions(new UserPrincipal(userId));
        if (ips == null) {
            return null;
        }
        for (InstancePermission ip : ips) {
            if (!ip.getTarget().getClassifier().equals(NOTEBOOK)) {
                continue;
            }
            if (ip.getTarget().getIdentifier().equals(ntbkId)) {
                return ip; 
            }
        }
        return null;
    }

}

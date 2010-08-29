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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.sourceforge.safr.jaas.permission.Action;
import net.sourceforge.safr.jaas.permission.PermissionManagerImpl;
import net.sourceforge.safr.sample.permission.domain.PermissionAssignment;

import org.junit.Before;
import org.junit.Test;

public class PermissionServiceImplTest {

    private PermissionManagerImpl permissionManager;
    
    private PermissionServiceImpl notebookPermissionService;
    
    @Before
    public void setUp() throws Exception {
        permissionManager = new PermissionManagerImpl();
        notebookPermissionService = new PermissionServiceImpl();
        notebookPermissionService.setPermissionManager(permissionManager);
    }

    @Test
    public void testApplyPermissionAssignments() {
        PermissionAssignment assignment = null;

        // create new assignment
        assignment = new PermissionAssignment("u1", "o1", "1", Action.WRITE);
        notebookPermissionService.applyPermissionAssignments(assignment);
        assertEquals("wrong number of permissions", 1, 
                permissionManager.getPermissions(assignment.createUserPrincipal()).size());
        assertTrue("wrong permissions content", 
                permissionManager.getPermissions(assignment.createUserPrincipal()).contains(assignment.createInstancePermission()));
        
        // upgrade assignment 
        assignment = new PermissionAssignment("u1", "o1", "1", Action.MANAGE);
        notebookPermissionService.applyPermissionAssignments(assignment);
        assertEquals("wrong number of permissions", 1, 
                permissionManager.getPermissions(assignment.createUserPrincipal()).size());
        assertTrue("wrong permissions content", 
                permissionManager.getPermissions(assignment.createUserPrincipal()).contains(assignment.createInstancePermission()));
        
        // same assignment
        assignment = new PermissionAssignment("u1", "o1", "1", Action.MANAGE);
        notebookPermissionService.applyPermissionAssignments(assignment);
        assertEquals("wrong number of permissions", 1, 
                permissionManager.getPermissions(assignment.createUserPrincipal()).size());
        assertTrue("wrong permissions content", 
                permissionManager.getPermissions(assignment.createUserPrincipal()).contains(assignment.createInstancePermission()));
        
        // cancel assignment
        assignment = new PermissionAssignment("u1", "o1", "1", null);
        notebookPermissionService.applyPermissionAssignments(assignment);
        assertEquals("wrong number of permissions", 0, 
                permissionManager.getPermissions(assignment.createUserPrincipal()).size());
        
    }
    
}

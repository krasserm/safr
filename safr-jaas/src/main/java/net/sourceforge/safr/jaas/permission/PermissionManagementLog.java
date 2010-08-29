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
import java.security.Principal;

import net.sourceforge.safr.jaas.principal.RolePrincipal;
import net.sourceforge.safr.jaas.principal.UserPrincipal;

/**
 * Allows for committing grant- and remove tasks as a unit of work.
 * 
 * @author Martin Krasser
 */
public interface PermissionManagementLog {

    /**
     * Add a task for granting an instance permission to given principal.
     * 
     * @param principal a {@link UserPrincipal} or {@link RolePrincipal}
     * @param permission an instance permission.
     */
    void addGrantTask(Principal principal, InstancePermission permission);
    
    /**
     * Add a task for revoking an instance permission from given principal.
     * 
     * @param principal a {@link UserPrincipal} or {@link RolePrincipal}
     * @param permission an instance permission.
     */
    void addRevokeTask(Principal principal, InstancePermission permission);
    
    /**
     * Executes added grant and revoke tasks.
     * 
     * @throws AccessControlException if current user doesn't have enough
     *         privileges to execute one or more tasks.
     */
    void commit();
    
}

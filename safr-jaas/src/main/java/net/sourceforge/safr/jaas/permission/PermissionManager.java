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

import java.security.Permission;
import java.security.Principal;
import java.util.Collection;

import javax.security.auth.Subject;


/**
 * @author Martin Krasser
 */
public interface PermissionManager {

    void checkPermission(Permission permission);
    
    boolean implies(Permission permission, Subject subject);
    
    boolean implies(Permission permission, Principal... principals);
    
    void grantPermission(Principal principal, InstancePermission permission);

    void revokePermission(Principal principal, InstancePermission permission);

    Collection<InstancePermission> getPermissions(Principal principal);
    
    PermissionManagementLog newPermissionManagementLog();

}

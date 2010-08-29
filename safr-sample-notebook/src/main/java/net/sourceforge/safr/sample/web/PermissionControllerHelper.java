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
package net.sourceforge.safr.sample.web;

import java.util.ArrayList;
import java.util.Collection;

import net.sourceforge.safr.sample.notebook.domain.Notebook;
import net.sourceforge.safr.sample.permission.domain.PermissionAssignment;
import net.sourceforge.safr.sample.permission.service.PermissionService;
import net.sourceforge.safr.sample.usermgnt.domain.User;
import net.sourceforge.safr.sample.usermgnt.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Martin Krasser
 */
@Component
class PermissionControllerHelper {

    @Autowired
    private PermissionService permissionService;
    
    @Autowired
    private UserService userService;

    public PermissionService getPermissionService() {
        return permissionService;
    }

    public UserService getUserService() {
        return userService;
    }

    public Collection<PermissionAssignment> getAssignments(Notebook notebook) {
        Collection<PermissionAssignment> assignments = new ArrayList<PermissionAssignment>();
        Collection<User> users = getUserService().findUsers();
        for (User user : users) {
            if (user.getId().equals("root")) {
                continue;
            }
            assignments.add(getAssignment(notebook, user.getId()));
        }
        return assignments;
    }

    public PermissionAssignment getAssignment(Notebook notebook, String assigneeId) {
        PermissionAssignment pa = getPermissionService().getPermissionAssignment(assigneeId, notebook);
        if (pa == null) {
            return new PermissionAssignment(assigneeId, notebook.getOwner().getId(), notebook.getId(), null);
        } else {
            return pa;
        }
    }

}

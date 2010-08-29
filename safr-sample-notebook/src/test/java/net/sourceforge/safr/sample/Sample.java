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
package net.sourceforge.safr.sample;

import java.security.AccessControlException;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sourceforge.safr.jaas.permission.Action;
import net.sourceforge.safr.jaas.permission.InstancePermission;
import net.sourceforge.safr.jaas.permission.PermissionManager;
import net.sourceforge.safr.jaas.permission.Target;
import net.sourceforge.safr.jaas.principal.RolePrincipal;
import net.sourceforge.safr.jaas.principal.UserPrincipal;
import net.sourceforge.safr.sample.notebook.domain.Entry;
import net.sourceforge.safr.sample.notebook.domain.Notebook;
import net.sourceforge.safr.sample.notebook.service.NotebookService;
import net.sourceforge.safr.sample.usermgnt.domain.Role;
import net.sourceforge.safr.sample.usermgnt.domain.User;
import net.sourceforge.safr.sample.usermgnt.service.UserService;

/**
 * @author Martin Krasser
 */
@Component
public class Sample {

    public static final String MSG_NO_READ_ACCESS = "no read access";
    public static final String MSG_NO_WRITE_ACCESS = "no write access";
    
    private static final String WILDCARD = Target.WILDCARD;
    private static final String NOTEBOOK = Notebook.class.getName();

    @Autowired
    private UserService userService;

    @Autowired
    private NotebookService notebookService;
    
    @Autowired
    private PermissionManager permissionManager;
    
    public void createNotebook(String notebookId) {
        User me = userService.currentUser();
        Notebook nb = new Notebook(notebookId, me);
        notebookService.createNotebook(nb);
    }
    
    public void createNotebookEntry(String notebookId, String text) {
        assert (notebookId.equals("nb1")) 
            || (notebookId.equals("nb2"))
            || (notebookId.equals("public")); 
        Notebook nb = notebookService.findNotebook(notebookId);
        if (nb == null) {
            throw new SampleException(MSG_NO_READ_ACCESS);
        }
        try {
            nb.addEntry(new Entry(text));
        } catch (AccessControlException e) {
            throw new SampleException(MSG_NO_WRITE_ACCESS);
        }
    }
    
    public void grantAccessToNotebook(String notebookId, Action action, User user) {
        User me = userService.currentUser();
        Target t = new Target(me.getId(), NOTEBOOK, notebookId);
        InstancePermission p = new InstancePermission(t, action);
        permissionManager.grantPermission(new UserPrincipal(user.getId()), p);
    }
    
    public void revokeAccessToNotebook(String notebookId, Action action, User user) {
        User me = userService.currentUser();
        Target t = new Target(me.getId(), NOTEBOOK, notebookId);
        InstancePermission p = new InstancePermission(t, action);
        permissionManager.revokePermission(new UserPrincipal(user.getId()), p);
    }
    
    public void assignNotebookPermission(String userId) {
        Target t = new Target(userId, NOTEBOOK, WILDCARD);
        permissionManager.grantPermission(
                new UserPrincipal(userId), 
                new InstancePermission(t, Action.AUTH));
    }
    
    Subject createSubjectForUser(String userId) {
        User user = userService.findUser(userId);
        Subject subject = new Subject();
        addUserPrincipal(subject, user);
        addRolePrincipals(subject, user);
        return subject;
    }

    private static void addUserPrincipal(Subject subject, User user) {
        subject.getPrincipals().add(new UserPrincipal(user.getId()));
    }
    
    private static void addRolePrincipals(Subject subject, User user) {
        for (Role role : user.getRoles()) {
            subject.getPrincipals().add(new RolePrincipal(role.getId()));
        }
    }
    
}

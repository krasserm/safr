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
package net.sourceforge.safr.sample.permission.domain;

import net.sourceforge.safr.jaas.permission.Action;
import net.sourceforge.safr.jaas.permission.InstancePermission;
import net.sourceforge.safr.jaas.permission.Target;
import net.sourceforge.safr.jaas.principal.UserPrincipal;
import net.sourceforge.safr.sample.notebook.domain.Notebook;

/**
 * Defines a user's (i.e. permission assignee's) assignment to a
 * notebook-specific {@link InstancePermission}.
 * 
 * @see InstancePermission
 * 
 * @author Martin Krasser
 */
public class PermissionAssignment {

    private String assigneeId;
    
    private String ownerId;
    
    private String notebookId;
    
    private Action action;

    public PermissionAssignment() {
        this(null);
    }
    
    public PermissionAssignment(String assigneId) {
        this(assigneId, null, null, null);
    }
    
    /**
     * Creates a new PermissionAssignment instance.
     * 
     * @param assigneeId user identifier of the permission assignee.
     * @param ownerId user identifier of the notebook owner (permission target context)
     * @param notebookId instance identifier of the notebook (permission target identifier).
     * @param action access action or <code>null</code> if access shall be
     *        denied.
     */
    public PermissionAssignment(String assigneeId, String ownerId, String notebookId, Action action) {
        this.assigneeId = assigneeId;
        this.ownerId = ownerId;
        this.notebookId = notebookId;
        this.action = action;
    }

    /**
     * Creates a new PermissionAssignment instance.
     * 
     * @param assigneeId user identifier of the permission assignee.
     * @param instancePermission notebook-specific instance permission.
     */
    public PermissionAssignment(String assigneeId, InstancePermission instancePermission) {
        String classifier = instancePermission.getTarget().getClassifier();
        if (!classifier.equals(Notebook.class.getName())) {
            throw new IllegalArgumentException(
                    "invalid instance permission classifier: " + classifier + 
                    ", must be: " + Notebook.class.getName());
        }
        this.assigneeId = assigneeId;
        this.ownerId = instancePermission.getTarget().getContext();
        this.notebookId = instancePermission.getTarget().getIdentifier();
        this.action = instancePermission.getAction();
        
    }
    
    public Action getAction() {
        return action;
    }

    public String getOwnerId() {
        return ownerId;
    }
    
    public String getNotebookId() {
        return notebookId;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setNotebookId(String notebookId) {
        this.notebookId = notebookId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    public boolean isNotebookIdWildcard() {
        return Target.WILDCARD.equals(notebookId);
    }
    
    public String getActionString() {
        if (action == null) {
            return "NONE";
        } else {
            return action.toString();
        }
    }

    public void setActionString(String actionString) {
        if (actionString.equals("NONE")) {
            action = null;
        } else {
            action = Action.valueOf(actionString);
        }
    }
    
    public String[] getActionStrings() {
        return new String[] {
            "NONE",
            Action.READ.toString(),
            Action.WRITE.toString(),
            Action.MANAGE.toString(),
            Action.AUTH.toString(),
        };
    }
    
    /**
     * Creates a {@link UserPrincipal} from the <code>assigneeId</code> of
     * this permission assignment.
     * 
     * @return a {@link UserPrincipal} instance.
     */
    public UserPrincipal createUserPrincipal() {
        return new UserPrincipal(assigneeId);
    }
    
    /**
     * Creates a new {@link InstancePermission} from this permission assignment.
     * 
     * @return a new {@link InstancePermission} object or <code>null</code> if
     *         this permission's <code>action</code> property is
     *         <code>null</code>.
     */
    public InstancePermission createInstancePermission() {
        if (action == null) {
            return null;
        }
        Target t = new Target(ownerId, Notebook.class.getName(), notebookId);
        return new InstancePermission(t, action);
    }
    
}

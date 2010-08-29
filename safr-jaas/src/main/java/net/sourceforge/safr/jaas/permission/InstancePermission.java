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

/**
 * @author Martin Krasser
 */
public class InstancePermission extends Permission {

    private static final long serialVersionUID = -5663298326792712531L;

    private Target target;
    
    private Action action;
    
    public InstancePermission(Target target, Action action) {
        super(target.toString());
        this.target = target;
        this.action = action;
    }
    
    public Target getTarget() {
        return target;
    }

    public Action getAction() {
        return action;
    }
    
    public InstancePermission createAuthorizationPermission() {
        return new InstancePermission(target, Action.AUTH);
    }
    
    @Override
    public boolean implies(Permission permission) {
        if (!(permission instanceof InstancePermission)) {
            return false;
        }
        InstancePermission p = (InstancePermission)permission;
        return target.implies(p.target) && action.implies(p.action);
    }

    @Override
    public String getActions() {
        return action.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof InstancePermission)) {
            return false;
        }
        InstancePermission p = (InstancePermission)obj;
        return target.equals(p.target) && (action == p.action); 
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + target.hashCode();
        result = 37 * result + action.hashCode();
        return result;
    }

}

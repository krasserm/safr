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
package net.sourceforge.safr.core.annotation.info;

import static net.sourceforge.safr.core.attribute.SecureAttribute.Action.CREATE;
import static net.sourceforge.safr.core.attribute.SecureAttribute.Action.CUSTOM_AFTER;
import static net.sourceforge.safr.core.attribute.SecureAttribute.Action.CUSTOM_AROUND;
import static net.sourceforge.safr.core.attribute.SecureAttribute.Action.CUSTOM_BEFORE;
import static net.sourceforge.safr.core.attribute.SecureAttribute.Action.DELETE;
import static net.sourceforge.safr.core.attribute.SecureAttribute.Action.EXECUTE;
import static net.sourceforge.safr.core.attribute.SecureAttribute.Action.NONE;
import static net.sourceforge.safr.core.attribute.SecureAttribute.Action.READ;
import static net.sourceforge.safr.core.attribute.SecureAttribute.Action.UPDATE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.safr.core.annotation.Secure;
import net.sourceforge.safr.core.annotation.SecureAction;
import net.sourceforge.safr.core.attribute.SecureAttribute;

/**
 * Internal representation of a {@link Secure} annotation.
 * 
 * @author Martin Krasser
 */
public class SecureAnnotationInfo implements SecureAttribute {

    private List<Action> actions;
    
    public SecureAnnotationInfo(Secure annotation) {
        initActions(annotation);
    }
    
    public List<Action> getActions() {
        return actions;
    }
    
    @Override
    public String toString() {
        return "actions=" + actions;
    }

    private void initActions(Secure annotation) {
        this.actions = new ArrayList<Action>();
        for (SecureAction action : annotation.value()) {
            actions.add(getAction(action));
        }
        this.actions = Collections.unmodifiableList(actions);
    }
    
    private static Action getAction(SecureAction action) {
        if (action == SecureAction.NONE) {
            return NONE;
        } else if (action == SecureAction.CREATE) {
            return CREATE;
        } else if (action == SecureAction.READ) {
            return READ;
        } else if (action == SecureAction.UPDATE) {
            return UPDATE;
        } else if (action == SecureAction.DELETE) {
            return DELETE;
        } else if (action == SecureAction.EXECUTE) {
            return EXECUTE;
        } else if (action == SecureAction.CUSTOM_BEFORE) {
            return CUSTOM_BEFORE;
        } else if (action == SecureAction.CUSTOM_AROUND) {
            return CUSTOM_AROUND;
        } else if (action == SecureAction.CUSTOM_AFTER) {
            return CUSTOM_AFTER;
        } else {
            throw new RuntimeException("Unsupported action: " + action);
        }
    }
    
}

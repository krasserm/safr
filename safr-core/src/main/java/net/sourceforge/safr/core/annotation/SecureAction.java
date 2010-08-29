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
package net.sourceforge.safr.core.annotation;

import net.sourceforge.safr.core.invocation.MethodInvocation;
import net.sourceforge.safr.core.invocation.ProceedingInvocation;
import net.sourceforge.safr.core.provider.AccessManager;

/**
 * Represents an action that is executed on a target object (i.e. target
 * instance). These actions are related to methods defined on the
 * {@link AccessManager} interface.
 * 
 * @author Martin Krasser
 */
public enum SecureAction {

    /**
     * Explicitly no action. Can be used to drop the effect of inherited
     * security annotations.
     */
    NONE,

    /**
     * Relates to {@link AccessManager#checkCreate(Object)}.
     */
    CREATE,

    /**
     * Relates to {@link AccessManager#checkRead(Object)}.
     */
    READ,

    /**
     * Relates to {@link AccessManager#checkUpdate(Object)}.
     */
    UPDATE,

    /**
     * Relates to {@link AccessManager#checkDelete(Object)}.
     */
    DELETE,

    /**
     * Relates to
     * {@link AccessManager#checkExecute(MethodInvocation)}.
     */
    EXECUTE,

    /**
     * Relates to
     * {@link AccessManager#checkCustomBefore(MethodInvocation)}.
     */
    CUSTOM_BEFORE,

    /**
     * Relates to
     * {@link AccessManager#checkCustomAround(ProceedingInvocation)}.
     */
    CUSTOM_AROUND,

    /**
     * Relates to
     * {@link AccessManager#checkCustomAfter(MethodInvocation, Object)}.
     */
    CUSTOM_AFTER;

}

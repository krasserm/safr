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
package net.sourceforge.safr.sample.provider;

import java.security.AccessControlException;

import net.sourceforge.safr.core.invocation.MethodInvocation;
import net.sourceforge.safr.core.invocation.ProceedingInvocation;
import net.sourceforge.safr.core.provider.AccessManager;

/**
 * @author Martin Krasser
 */
public class AccessManagerSupport implements AccessManager {

    public void checkCreate(Object obj) {
        throw new AccessControlException("unable to handle authorization decision request");
    }

    public void checkRead(Object obj) {
        throw new AccessControlException("unable to handle authorization decision request");
    }

    public void checkUpdate(Object obj) {
        throw new AccessControlException("unable to handle authorization decision request");
    }

    public void checkDelete(Object obj) {
        throw new AccessControlException("unable to handle authorization decision request");
    }

    public void checkExecute(MethodInvocation invocation) {
        throw new AccessControlException("unable to handle authorization decision request");
    }

    public void checkCustomBefore(MethodInvocation invocation) {
        throw new AccessControlException("unable to handle authorization decision request");
    }

    public Object checkCustomAround(ProceedingInvocation invocation) throws Throwable {
        throw new AccessControlException("unable to handle authorization decision request");
    }

    public Object checkCustomAfter(MethodInvocation invocation, Object result) {
        throw new AccessControlException("unable to handle authorization decision request");
    }

}

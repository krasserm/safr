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
package net.sourceforge.safr.core.integration.support;

import java.security.AccessControlException;

import net.sourceforge.safr.core.annotation.SecureAction;
import net.sourceforge.safr.core.invocation.MethodInvocation;
import net.sourceforge.safr.core.invocation.ProceedingInvocation;
import net.sourceforge.safr.core.provider.AccessManager;
import net.sourceforge.safr.core.spring.annotation.AuthorizationServiceProvider;

/**
 * @author Martin Krasser
 */
@AuthorizationServiceProvider
public class TestAccessManager implements AccessManager {

    private CheckHistory checkHistory;

    private boolean readCheck;
    
    public TestAccessManager() {
        this.checkHistory = new CheckHistory();
        this.readCheck = false;
    }
    
    public CheckHistory getCheckHistory() {
        return checkHistory;
    }
    
    public void setReadCheck(boolean readCheck) {
        this.readCheck = readCheck;
    }
    
    public void checkRead(Object obj) {
        checkHistory.add(new Check(obj, SecureAction.READ));
        if (readCheck && obj.equals("y")) {
            throw new AccessControlException("read access to y denied");
        }
    }

    public void checkCreate(Object obj) {
        checkHistory.add(new Check(obj, SecureAction.CREATE));
    }

    public void checkUpdate(Object obj) {
        checkHistory.add(new Check(obj, SecureAction.UPDATE));
    }

    public void checkDelete(Object obj) {
        checkHistory.add(new Check(obj, SecureAction.DELETE));
    }

    public void checkExecute(MethodInvocation invocation) {
        checkHistory.add(new Check(invocation.getTarget(), SecureAction.EXECUTE, invocation));
    }

    public void checkCustomBefore(MethodInvocation invocation) {
        checkHistory.add(new Check(invocation.getTarget(), SecureAction.CUSTOM_BEFORE, invocation));
    }

    public Object checkCustomAround(ProceedingInvocation invocation) throws Throwable {
        checkHistory.add(new Check(invocation.getTarget(), SecureAction.CUSTOM_AROUND, invocation));
        return invocation.proceed();
    }

    public Object checkCustomAfter(MethodInvocation invocation, Object result) {
        checkHistory.add(new Check(invocation.getTarget(), SecureAction.CUSTOM_AFTER, invocation));
        return result;
    }

}

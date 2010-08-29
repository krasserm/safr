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
package net.sourceforge.safr.core.interceptor;

import java.lang.reflect.Method;

import net.sourceforge.safr.core.attribute.EncryptAttribute;
import net.sourceforge.safr.core.attribute.FilterAttribute;
import net.sourceforge.safr.core.attribute.SecureAttribute;
import net.sourceforge.safr.core.attribute.SecurityAttributeSource;
import net.sourceforge.safr.core.attribute.SecureAttribute.Action;
import net.sourceforge.safr.core.filter.ResultFilterFactory;
import net.sourceforge.safr.core.invocation.MethodInvocation;
import net.sourceforge.safr.core.invocation.ProceedingInvocation;
import net.sourceforge.safr.core.provider.AccessManager;
import net.sourceforge.safr.core.provider.CryptoProvider;

/**
 * @author Martin Krasser
 */
public abstract class InterceptorSupport {

    private SecurityAttributeSource source;
    
    private AccessManager accessManager;
    
    private CryptoProvider cryptoProvider;

    private ResultFilterFactory removeFilterFactory;
    
    private ResultFilterFactory copyFilterFactory;
    
    public SecurityAttributeSource getSecurityAttributeSource() {
        return source;
    }
    
    public void setSecurityAttributeSource(SecurityAttributeSource source) {
        this.source = source;
    }
    
    public AccessManager getAccessManager() {
        return accessManager;
    }

    public void setAccessManager(AccessManager accessManager) {
        this.accessManager = accessManager;
    }

    public CryptoProvider getCryptoProvider() {
        return cryptoProvider;
    }

    public void setCryptoProvider(CryptoProvider cryptoProvider) {
        this.cryptoProvider = cryptoProvider;
    }

    public ResultFilterFactory getRemoveFilterFactory() {
        return removeFilterFactory;
    }
    
    public void setRemoveFilterFactory(ResultFilterFactory removeFilterFactory) {
        this.removeFilterFactory = removeFilterFactory;
    }

    public ResultFilterFactory getCopyFilterFactory() {
        return copyFilterFactory;
    }
    
    public void setCopyFilterFactory(ResultFilterFactory copyFilterFactory) {
        this.copyFilterFactory = copyFilterFactory;
    }

    public boolean isConfigured() {
        return accessManager != null;
    }
    
    protected Object encrypt(EncryptAttribute ea, Object context, Object value) {
        if (value == null) {
            return null;
        }
        return getCryptoProvider().encrypt(value, context, ea.getHints());
    }
    
    protected Object decrypt(EncryptAttribute ea, Object context, Object value) {
        if (value == null) {
            return null;
        }
        return getCryptoProvider().decrypt(value, context, ea.getHints());
    }
    
    protected void beforeProceed(SecureAttribute attribute, MethodInvocation invocation) {
        if (attribute == null) {
            return;
        }
        if (attribute.getActions().contains(Action.CUSTOM_BEFORE)) {
            accessManager.checkCustomBefore(invocation);
        }
    }
    
    protected Object afterProceed(SecureAttribute attribute, MethodInvocation invocation, Object result) {
        if (attribute == null) {
            return result;
        }
        if (attribute.getActions().contains(Action.CUSTOM_AFTER)) {
            return accessManager.checkCustomAfter(invocation, result);
        }
        return result;
    }
    
    protected Object aroundProceed(SecureAttribute attribute, ProceedingInvocation invocation) throws Throwable {
        if (attribute == null) {
            return invocation.proceed();
        }
        if (attribute.getActions().contains(Action.CUSTOM_AROUND)) {
            return accessManager.checkCustomAround(invocation);
        }
        Object result = invocation.proceed(); 
        return result;
    }
    
    protected void checkMethodAction(SecureAttribute attribute, MethodInvocation invocation) {
        if (attribute == null) {
            return;
        }
        for (Action action : attribute.getActions()) {
            if (action == Action.EXECUTE) {
                // The attribute defines an EXECUTE action. In addition to the
                // object being executed the AccessManager also needs
                // information about the method beeing executed and its 
                // arguments. This information can be obtained from the 
                // invocation argument.
                accessManager.checkExecute(invocation);
            } else {
                // The attribute defines an action other than EXECUTE. This
                // case handles the action definitions CREATE, READ, UPDATE
                // and DELETE. The corresponding checkCreate(), checkRead(),
                // checkUpdate() and checkDelete() methods on AccessManager
                // are called via checkObjectAction(). The object passed as
                // argument to AccessManager methods is the object on which
                // the method was called. 
                checkObjectAction(action, invocation.getTarget());
            }
        }
    }
    
    protected Object filterResult(FilterAttribute attribute, Method method, Object result) {
        if (attribute == null) {
            return result;
        }
        ResultFilterFactory factory = getResultFilterFactory(attribute.isCopyResultCollection());
        return factory.getResultFilter(method, attribute).apply(result);
    }
    
    protected void checkParameterActions(SecureAttribute[] attributes, Object[] arguments) {
        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i] != null) {
                checkParameterAction(attributes[i], arguments[i]);
            }
        }
    }
    
    private void checkParameterAction(SecureAttribute attribute, Object argument) {
        for (Action action : attribute.getActions()) {
            checkObjectAction(action, argument);
        }
    }
    
    private void checkObjectAction(Action action, Object object) {
        if (object == null) {
            return;
        }
        switch (action) {
        case CREATE:
            accessManager.checkCreate(object); break;
        case READ:
            accessManager.checkRead(object); break;
        case UPDATE:
            accessManager.checkUpdate(object); break;
        case DELETE:
            accessManager.checkDelete(object); break;
        }
    }
    
    private ResultFilterFactory getResultFilterFactory(boolean copy) {
        return copy ? copyFilterFactory : removeFilterFactory;
    }
    
}

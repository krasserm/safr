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

import net.sourceforge.safr.core.attribute.FilterAttribute;
import net.sourceforge.safr.core.attribute.SecureAttribute;
import net.sourceforge.safr.core.invocation.AopAllianceProceedingInvocation;
import net.sourceforge.safr.core.invocation.ProceedingInvocation;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Martin Krasser
 */
public class SecurityInterceptor extends InterceptorSupport implements MethodInterceptor {

    public final Object invoke(MethodInvocation invocation) throws Throwable {
        SecureAttribute msa = getMethodSecureAttribute(invocation);
        SecureAttribute[] psa = getParameterSecureAttributes(invocation);
        
        ProceedingInvocation pi = new AopAllianceProceedingInvocation(invocation);
        
        // pre-processing
        beforeProceed(msa, pi);
        checkMethodAction(msa, pi);
        checkParameterActions(psa, pi.getArguments());
        
        // around-processing
        Object result = aroundProceed(msa, pi);
        
        // post-processing
        FilterAttribute mfa = getMethodFilterAttribute(invocation);
        result = filterResult(mfa, pi.getMethod(), result);
        return afterProceed(msa, pi, result);
    }

    private FilterAttribute getMethodFilterAttribute(MethodInvocation invocation) {
        return getSecurityAttributeSource().getMethodFilterAttribute(invocation.getMethod(), getTargetClass(invocation));
    }
    
    private SecureAttribute getMethodSecureAttribute(MethodInvocation invocation) {
        return getSecurityAttributeSource().getMethodSecureAttribute(invocation.getMethod(), getTargetClass(invocation));
    }
    
    private SecureAttribute[] getParameterSecureAttributes(MethodInvocation invocation) {
        return getSecurityAttributeSource().getParameterSecureAttributes(invocation.getMethod(), getTargetClass(invocation));
    }
    
    private Class<?> getTargetClass(MethodInvocation invocation) {
        return (invocation.getThis() != null) ? invocation.getThis().getClass() : null;
    }

}

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

import net.sourceforge.safr.core.annotation.Encrypt;
import net.sourceforge.safr.core.annotation.Filter;
import net.sourceforge.safr.core.annotation.Inherit;
import net.sourceforge.safr.core.annotation.Secure;
import net.sourceforge.safr.core.attribute.EncryptAttribute;
import net.sourceforge.safr.core.attribute.FilterAttribute;
import net.sourceforge.safr.core.attribute.SecureAttribute;
import net.sourceforge.safr.core.invocation.AspectJProceedingInvocation;
import net.sourceforge.safr.core.invocation.ProceedingInvocation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.FieldSignature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author Martin Krasser
 */
@Aspect
@SuppressWarnings("unused")
public class SecurityAspect extends InterceptorSupport {

    @Pointcut("@target(net.sourceforge.safr.core.annotation.SecureObject)")
    private void secureObjectAnnotatedClass() {}

    /* ------------------------------------------------------------------------
     * A limitation in AspectJ 1.6 parameter annotation matching requires the 
     * position of the annotated parameter to be defined explicitly. Currently, 
     * @Secure annotations are matched only if they are applied to the first 5 
     * parameters of a method. 
     * --------------------------------------------------------------------- */
    @Pointcut("execution(!private * *(@net.sourceforge.safr.core.annotation.Secure (*), ..))")
    private void param1() {}
    @Pointcut("execution(!private * *(*, @net.sourceforge.safr.core.annotation.Secure (*), ..))")
    private void param2() {}
    @Pointcut("execution(!private * *(*, *, @net.sourceforge.safr.core.annotation.Secure (*), ..))")
    private void param3() {}
    @Pointcut("execution(!private * *(*, *, *, @net.sourceforge.safr.core.annotation.Secure (*), ..))")
    private void param4() {}
    @Pointcut("execution(!private * *(*, *, *, *, @net.sourceforge.safr.core.annotation.Secure (*), ..))")
    private void param5() {}
    
    @Pointcut("param1() || param2() || param3() || param4() || param5()")
    private void secureAnnotatedParam() {}
    
    @Pointcut("execution(!private * *(..)) && @annotation(net.sourceforge.safr.core.annotation.Secure)")
    private void secureAnnotatedMethod() {}
    
    @Pointcut("execution(!private * *(..)) && @annotation(net.sourceforge.safr.core.annotation.Filter)")
    private void filterAnnotatedMethod() {} 
    
    @Pointcut("execution(!private * *(..)) && @annotation(net.sourceforge.safr.core.annotation.Inherit)")
    private void inheritAnnotatedMethod() {}
    
    @Pointcut("@annotation(net.sourceforge.safr.core.annotation.Encrypt)")
    private void encryptAnnotatedField() {}
    
    @Pointcut("secureObjectAnnotatedClass() && filterAnnotatedMethod()")
    private void filterAnnotatedDomainObjectMethod() {}
    
    @Pointcut("secureObjectAnnotatedClass() && (secureAnnotatedMethod() || secureAnnotatedParam())")
    private void secureAnnotatedDomainObjectMethod() {}
    
    @Pointcut("secureObjectAnnotatedClass() && inheritAnnotatedMethod()")
    private void inheritAnnotatedDomainObjectMethod() {}
    
    @Pointcut("secureObjectAnnotatedClass() && encryptAnnotatedField()")
    private void encryptAnnotatedDomainObjectField() {}
    
    @Pointcut("secureAnnotatedDomainObjectMethod() || filterAnnotatedDomainObjectMethod() || inheritAnnotatedDomainObjectMethod()")
    private void securityAnnotatedDomainObjectMethod() {}

    @Around("securityAnnotatedDomainObjectMethod()")
    public Object methodInvocation(ProceedingJoinPoint pjp) throws Throwable {
        if (!isConfigured()) {
            return pjp.proceed();
        }
        
        MethodSignature signature = (MethodSignature)pjp.getSignature();
        SecureAttribute msa = getMethodSecureAttribute(signature);
        SecureAttribute[] psa = getParameterSecureAttributes(signature);

        ProceedingInvocation pi = new AspectJProceedingInvocation(pjp);
        
        // pre-processing
        beforeProceed(msa, pi);
        checkMethodAction(msa, pi);
        checkParameterActions(psa, pi.getArguments());
        
        // around processing
        Object result = aroundProceed(msa, pi);
        
        // post-processing
        FilterAttribute mfa = getMethodFilterAttribute(signature);
        result = filterResult(mfa, pi.getMethod(), result);
        return afterProceed(msa, pi, result);
        
    }
    
    @Around("set(* *.*) && encryptAnnotatedDomainObjectField()")
    public Object setFieldAccess(ProceedingJoinPoint pjp) throws Throwable {
        FieldSignature signature = (FieldSignature)pjp.getSignature();
        EncryptAttribute ea = getFieldEncryptAttribute(signature);

        Object[] args = pjp.getArgs();
        // exchange original value with encrypted value
        args[0] = encrypt(ea, pjp.getTarget(), args[0]);
        // perform field access with encrypted value
        return pjp.proceed(args);
    }
    
    @Around("get(* *.*) && encryptAnnotatedDomainObjectField()")
    public Object getFieldAccess(ProceedingJoinPoint pjp) throws Throwable {
        FieldSignature signature = (FieldSignature)pjp.getSignature();
        EncryptAttribute ea = getFieldEncryptAttribute(signature);
        
        // obtain encrypted value from field
        Object value = pjp.proceed();
        // decrypt encrypted field value and return it
        return decrypt(ea, pjp.getTarget(), value);
    }
    
    private EncryptAttribute getFieldEncryptAttribute(FieldSignature signature) {
        return getSecurityAttributeSource().getFieldEncryptAttribute(signature.getField());
    }
    
    private FilterAttribute getMethodFilterAttribute(MethodSignature signature) {
        return getSecurityAttributeSource().getMethodFilterAttribute(signature.getMethod(), signature.getDeclaringType());
    }
    
    private SecureAttribute getMethodSecureAttribute(MethodSignature signature) {
        return getSecurityAttributeSource().getMethodSecureAttribute(signature.getMethod(), signature.getDeclaringType());
    }
 
    private SecureAttribute[] getParameterSecureAttributes(MethodSignature signature) {
        return getSecurityAttributeSource().getParameterSecureAttributes(signature.getMethod(), signature.getDeclaringType());
    }
    
}

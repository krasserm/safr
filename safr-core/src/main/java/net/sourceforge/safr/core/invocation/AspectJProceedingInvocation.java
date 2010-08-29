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
package net.sourceforge.safr.core.invocation;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * AspectJ specific implementation of the {@link ProceedingInvocation}
 * interface.
 * 
 * @author Martin Krasser
 */
public class AspectJProceedingInvocation extends AbstractMethodInvocation implements ProceedingInvocation {

    private ProceedingJoinPoint pjp;
    
    public AspectJProceedingInvocation(ProceedingJoinPoint pjp) {
        super(getMethod(pjp), pjp.getTarget(), pjp.getArgs());
        this.pjp = pjp;
    }

    public Object proceed() throws Throwable {
        return pjp.proceed();
    }

    private static Method getMethod(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature)pjp.getSignature();
        return signature.getMethod();
        
    }
}

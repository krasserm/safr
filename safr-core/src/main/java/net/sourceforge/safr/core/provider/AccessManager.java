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
package net.sourceforge.safr.core.provider;

import java.security.AccessControlException;

import net.sourceforge.safr.core.annotation.Filter;
import net.sourceforge.safr.core.annotation.Secure;
import net.sourceforge.safr.core.invocation.MethodInvocation;
import net.sourceforge.safr.core.invocation.ProceedingInvocation;

/**
 * Implemented by classes that handle authorization decision requests. These
 * requests arise from {@link Secure} and {@link Filter} annotations.
 * 
 * @author Martin Krasser
 */
public interface AccessManager {

    /**
     * Checks whether a "create" action is allowed for the given object. This
     * method relates to <code>@Secure(SecureAction.CREATE)</code> annotations.
     * 
     * @param obj instance to protect.
     * @throws AccessControlException if the implementation class decides
     *         against the execution of the intended action.
     */
    void checkCreate(Object obj);

    /**
     * Checks whether a "read" action is allowed for the given object. This
     * method relates to <code>@Filter</code> and 
     * <code>@Secure(SecureAction.READ)</code> annotations.
     * 
     * @param obj instance to protect.
     * @throws AccessControlException if the implementation class decides
     *         against the execution of the intended action.
     */
    void checkRead(Object obj);

    /**
     * Checks whether an "update" action is allowed for the given object. This
     * method relates to <code>@Secure(SecureAction.UPDATE)</code> annotations.
     * 
     * @param obj instance to protect.
     * @throws AccessControlException if the implementation class decides
     *         against the execution of the intended action.
     */
    void checkUpdate(Object obj);

    /**
     * Checks whether a "delete" action is allowed for the given object. This
     * method relates to <code>@Secure(SecureAction.DELETE)</code> annotations.
     * 
     * @param obj instance to protect.
     * @throws AccessControlException if the implementation class decides
     *         against the execution of the intended action.
     */
    void checkDelete(Object obj);

    /**
     * Checks whether a given method invocation is allowed. This method
     * relates to <code>@Secure(SecureAction.EXECUTE)</code> or simply 
     * <code>@Secure</code> annotations.
     * 
     * @param invocation a {@link MethodInvocation} instance.
     * @throws AccessControlException if the implementation class decides
     *         against the execution of the intended action.
     */
    void checkExecute(MethodInvocation invocation);

    /**
     * Allows implementation classes to perform custom permission checks before
     * a method invocation. This method relates to
     * <code>@Secure(SecureAction.CUSTOM_BEFORE)</code> annotations. Implementation
     * classes may modify invocation arguments.
     * 
     * @param invocation a {@link MethodInvocation} instance.
     * @throws AccessControlException if the implementation class decides
     *         against the execution of the intended action.
     */
    void checkCustomBefore(MethodInvocation invocation);

    /**
     * Allows implementation classes to perform custom permission checks before
     * and after a method invocation. This method relates to
     * <code>@Secure(SecureAction.CUSTOM_AROUND)</code> annotations. Implemention
     * classes may modify invocation arguments and the result returned from an
     * <code>invocation.proceed()</code> call.
     * 
     * @param invocation a {@link ProceedingInvocation} instance.
     * @return the original result returned from
     *         <code>invocation.proceed()</code> or a modified result.
     * @throws Throwable thrown by <code>invocation.proceed()</code>.
     * @throws AccessControlException if the implementation class decides
     *         against the execution of the intended action. This should only
     *         be done before a call to <code>invocation.proceed()</code>.
     */
    Object checkCustomAround(ProceedingInvocation invocation) throws Throwable;

    /**
     * Allows implementation classes to perform custom permission checks after a
     * method invocation. This method relates to
     * <code>@Secure(SecureAction.CUSTOM_AFTER)</code> annotations. Implemention
     * classes either return the result given by the <code>result</code> 
     * parameter or a modified result.
     * 
     * @param invocation a {@link MethodInvocation} instance.
     * @param result the invocation result.
     * @return either the original result given by the <code>result</code>
     *         parameter or a modified result.
     */
    Object checkCustomAfter(MethodInvocation invocation, Object result);

}

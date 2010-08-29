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

/**
 * A {@link MethodInvocation} that additionally defines a {@link #proceed()}
 * method. This interface abstracts from Spring AOP (AOP Alliance) and
 * AspectJ implementation details.
 * 
 * @author Martin Krasser
 */
public interface ProceedingInvocation extends MethodInvocation {

    /**
     * Proceeds with the next advice or method invocation.
     * 
     * @return the method invocation result eventually modified by other advices.
     * @throws Throwable thrown by the target method or other advices.
     */
    Object proceed() throws Throwable;
    
}

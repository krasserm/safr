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
package net.sourceforge.safr.sample;

import java.lang.reflect.Method;
import java.security.PrivilegedExceptionAction;

/**
 * @author Martin Krasser
 */
public class MethodRunner implements PrivilegedExceptionAction<Object> {

    private Object target;
    private String methodName;
    private Object[] args;
    
    public MethodRunner(Object target, String methodName, Object... args) {
        this.target = target;
        this.methodName = methodName;
        if (args == null) {
            this.args = new Object[] {};
        } else {
            this.args = args;
        }
    }
    
    public Object run() throws Exception {
        Method method = Sample.class.getMethod(methodName, createArgumentTypeArray(args));
        return method.invoke(target, args);
    }

    private static Class<?>[] createArgumentTypeArray(Object... args) {
        if (args == null) {
            return new Class[] {};
        }
        Class<?>[] types = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            types[i] = args[i].getClass();
        }
        return types;
    }
    
}

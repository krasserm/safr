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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is a hint to the AspectJ compiler that security annotations
 * shall be inherited from superclasses and interfaces. This annotation is only
 * necessary for classes processed by the AspectJ compiler. For beans managed by
 * a Spring application context, annotation inheritance is the default
 * behaviour. If other (method-level) security annotations are defined on the
 * same method inheritance is enabled by default and this annotation can be
 * omitted.
 * 
 * @author Martin Krasser
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( ElementType.METHOD)
public @interface Inherit {

}

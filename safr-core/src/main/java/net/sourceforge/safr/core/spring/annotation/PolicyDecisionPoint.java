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
package net.sourceforge.safr.core.spring.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import net.sourceforge.safr.core.provider.AccessManager;

import org.springframework.stereotype.Component;

/**
 * This annotation should be placed on classes implementing the
 * {@link AccessManager} interface if these classes shall be loaded by Spring
 * with <code>&lt;context:component-scan/&gt;</code>.
 * 
 * @author Martin Krasser
 * 
 * @deprecated use {@link AuthorizationServiceProvider} instead.
 */
@Retention(RetentionPolicy.RUNTIME)
@Component
@Deprecated
public @interface PolicyDecisionPoint {

    /**
     * The {@link AccessManager} bean name. 
     * 
     * @return the {@link AccessManager} bean name.
     */
    String value() default "accessManager";
    
}

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
package net.sourceforge.safr.jaas.login;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;

/**
 * @author Martin Krasser
 */
public class DefaultLoginContext {

    private static final String CLASS_NAME = DefaultLoginModule.class.getName();
    private static final String PACKAGE_NAME = DefaultLoginContext.class.getPackage().getName();
    private static final String CONFIG_NAME = PACKAGE_NAME + ".default";
    private static final Map<String, ?> EMPTY_MAP = new HashMap<String, Object>();
    
    private boolean loginSucceeded;
    
    private LoginContext context;
    
    public DefaultLoginContext(CallbackHandler handler) throws LoginException {
        Configuration config = new DefaultConfiguration(CLASS_NAME, LoginModuleControlFlag.REQUIRED, EMPTY_MAP);
        context = new LoginContext(CONFIG_NAME, new Subject(), handler, config);
    }
    
    public void login() throws LoginException {
        loginSucceeded = false;
        context.login();
        loginSucceeded = true;
    }
    
    public void logout() throws LoginException {
        context.logout();
    }
    
    public Subject getSubject() {
        if (!loginSucceeded) {
            return null;
        }
        return context.getSubject();
    }
    
}

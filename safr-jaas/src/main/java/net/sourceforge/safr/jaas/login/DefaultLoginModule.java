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

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

/**
 * @author Martin Krasser
 */
public class DefaultLoginModule implements LoginModule {

    private Subject subject;
    private Set<Principal> principals;
    private CallbackHandler handler;
    
    private boolean authenticated;
    private boolean committed;
        
    public void initialize(Subject subject, CallbackHandler handler, Map<String, ?> shared, Map<String, ?> options) {
        this.handler = handler;
        this.subject = subject;
        this.principals = new HashSet<Principal>();
        this.authenticated = false;
        this.committed = false;
    }

    public boolean login() throws LoginException {
        NameCallback ncb = new NameCallback("username: ");
        PasswordCallback pcb = new PasswordCallback("password: ", false);
        AuthenticationService service = getAuthenticationService(); 
        try {
            handler.handle(new Callback[] { ncb, pcb});
            principals.addAll(service.authenticate(ncb.getName(), pcb.getPassword()));            
            return authenticated = true;
        } catch (IOException e) {
            throw new LoginException(e.getMessage());
        } catch (UnsupportedCallbackException e) {
            throw new LoginException(e.getMessage());
        } finally {
            pcb.clearPassword();
        }
    }

    public boolean logout() throws LoginException {
        subject.getPrincipals().removeAll(principals);
        principals.clear();
        return true;
    }

    public boolean commit() throws LoginException {
        if (committed = authenticated) { // assignment
            subject.getPrincipals().addAll(principals);
        }
        return committed;
    }

    public boolean abort() throws LoginException {
        if (!authenticated) {
            return false;
        }
        logout();
        return true;
    }

    private static AuthenticationService getAuthenticationService() {
        return AuthenticationServiceHolder.getInstance().getAuthenticationService();
    }
    
}

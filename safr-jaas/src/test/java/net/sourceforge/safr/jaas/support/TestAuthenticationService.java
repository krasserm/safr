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
package net.sourceforge.safr.jaas.support;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.security.auth.login.LoginException;

import net.sourceforge.safr.jaas.login.AuthenticationService;
import net.sourceforge.safr.jaas.principal.UserPrincipal;

/**
 * @author Martin Krasser
 */
public class TestAuthenticationService implements AuthenticationService {

    private Set<String> usernameWhitelist;
    private Set<String> usernameBlacklist;
    
    public TestAuthenticationService() {
        this.usernameWhitelist = new HashSet<String>();
        this.usernameBlacklist = new HashSet<String>();
    }
    
    public Set<Principal> authenticate(String username, char[] password) throws LoginException {
        if (usernameWhitelist.contains(username)) {
            return createPrincipalsSet(username);
        }
        throw new LoginException("authentication failed for " + username);
    }

    public Set<String> getUsernameWhitelist() {
        return usernameWhitelist;
    }
    
    public Set<String> getUsernameBlacklist() {
        return usernameBlacklist;
    }
    
    private Set<Principal> createPrincipalsSet(String username) {
        HashSet<Principal> result = new HashSet<Principal>();
        result.add(new UserPrincipal(username));
        return result;
    }
    
}

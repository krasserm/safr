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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.security.Principal;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

import net.sourceforge.safr.jaas.principal.UserPrincipal;
import net.sourceforge.safr.jaas.support.TestAuthenticationService;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Martin Krasser
 */
public class DefaultLoginModuleTest {

    private static AuthenticationServiceHolder holder;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        TestAuthenticationService service = new TestAuthenticationService();
        holder = AuthenticationServiceHolder.getInstance();
        holder.setAuthenticationService(service);
        service.getUsernameWhitelist().add("user1");
        service.getUsernameBlacklist().add("user2");
    }

    @Test
    public void testLoginSuccess() throws LoginException {
        DefaultLoginContext context = new DefaultLoginContext(new DefaultCallbackHandler("user1", null));
        context.login();
        Subject subject = context.getSubject();
        assertEquals("wrong number of principals", 1, subject.getPrincipals().size());
        assertEquals("wrong number of user principals", 1, subject.getPrincipals(UserPrincipal.class).size());
        Principal principal = subject.getPrincipals().iterator().next();
        assertEquals("wrong principal name", "user1", principal.getName());
        context.logout();
        assertEquals("wrong number of principals", 0, subject.getPrincipals().size());
        assertEquals("wrong number of user principals", 0, subject.getPrincipals(UserPrincipal.class).size());
    }
    
    @Test
    public void testLoginFailure() throws LoginException {
        DefaultLoginContext context = new DefaultLoginContext(new DefaultCallbackHandler("user2", null));
        try {
            context.login();
            fail("unexpected successful login");
        } catch (LoginException e) {
            // ok
        }
        assertNull("subject not null", context.getSubject());
    }
    
}

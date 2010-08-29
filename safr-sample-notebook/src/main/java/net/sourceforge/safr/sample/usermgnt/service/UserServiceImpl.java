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
package net.sourceforge.safr.sample.usermgnt.service;

import java.security.AccessController;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

import org.springframework.stereotype.Service;

import net.sourceforge.safr.jaas.login.AuthenticationService;
import net.sourceforge.safr.jaas.login.AuthenticationServiceHolder;
import net.sourceforge.safr.jaas.principal.RolePrincipal;
import net.sourceforge.safr.jaas.principal.UserPrincipal;
import net.sourceforge.safr.sample.usermgnt.domain.Role;
import net.sourceforge.safr.sample.usermgnt.domain.User;

/**
 * @author Martin Krasser
 */
@Service
public class UserServiceImpl implements UserService, AuthenticationService {

    private Map<String, User> users;
    
    public UserServiceImpl() {
        this.users = new HashMap<String, User>();
    }
    
    public User currentUser() {
        return findUser(currentUserPrincipal().getName());
    }
    
    public User findUser(String id) {
        return users.get(id);
    }

    public Collection<User> findUsers() {
        return new ArrayList<User>(users.values());
    }

    public Set<Principal> authenticate(String username, char[] password) throws LoginException {
        User user = findUser(username);
        if (user == null) {
            throw new LoginException("user " + username + " doesn't exist");
        }
        if (!user.getId().equals(String.valueOf(password))) {
            throw new LoginException("wrong password for user " + username);
        }
        Set<Principal> principals = new HashSet<Principal>();
        principals.add(new UserPrincipal(user.getId()));
        for (Role role : user.getRoles()) {
            principals.add(new RolePrincipal(role.getId()));
        }
        return principals;
    }
    
    @PostConstruct
    public void bootstrap() {
        // needed by login module 
        AuthenticationServiceHolder.getInstance().setAuthenticationService(this);
        
        // setup sample users
        createUser("root").bootstrap();
        createUser("user1").bootstrap();
        createUser("user2").bootstrap();
        createUser("user3").bootstrap();
    }
    
    private User createUser(String id) {
        User user = new User(id);
        users.put(id, user);
        return user;
    }
    
    private static Principal currentUserPrincipal() {
        Subject s = Subject.getSubject(AccessController.getContext());
        return s.getPrincipals(UserPrincipal.class).iterator().next();
    }

}

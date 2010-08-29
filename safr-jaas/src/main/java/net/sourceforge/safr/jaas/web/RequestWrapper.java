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
package net.sourceforge.safr.jaas.web;

import java.security.Principal;
import java.util.Set;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import net.sourceforge.safr.jaas.principal.RolePrincipal;
import net.sourceforge.safr.jaas.principal.UserPrincipal;

/**
 * @author Martin Krasser
 */
public abstract class RequestWrapper extends HttpServletRequestWrapper {

    /**
     * Prefix for keys.
     */
    private static final String PREFIX = RequestWrapper.class.getPackage().getName();

    /**
     * Key to store {@link Subject} instances in {@link HttpSession}.
     */
    private static final String SUBJECT_KEY = PREFIX + ".authenticated";
    
    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * @see javax.servlet.http.HttpServletRequestWrapper#getAuthType()
     */
    public abstract String getAuthType();

    /**
     * Returns <code>true</code> if the request provides credentials for
     * authentication.
     * 
     * @return <code>true</code> if credentials for authentication are
     *         provided.
     */
    public abstract boolean hasCredentials();

    /**
     * Returns <code>true</code> if the request (current session) is already
     * authenticated.
     * 
     * @return <code>true</code> if the request (current session) is already
     *         authenticated.
     */
    public boolean isAuthenticated() {
        return getSession(true).getAttribute(SUBJECT_KEY) != null;
    }

    /**
     * Marks the request (current session) as authenticated.
     * 
     * @param subject to be stored in the current session.
     */
    public void setAuthenticated(Subject subject) {
        HttpSession session = getSession(true);
        session.setAttribute(SUBJECT_KEY, subject);
    }

    @Override
    public String getRemoteUser() {
        Principal up = getUserPrincipal();
        if (up == null) {
            return null;
        }
        return up.getName();
    }

    @Override
    public Principal getUserPrincipal() {
        Subject s = getSubject();
        if (s == null) {
            return null;
        }
        Set<UserPrincipal> principals = s.getPrincipals(UserPrincipal.class);
        if (principals.isEmpty()) {
            return null;
        }
        return principals.iterator().next();
    }

    @Override
    public boolean isUserInRole(String role) {
        Subject s = getSubject();
        if (s == null) {
            return false;
        }
        Set<RolePrincipal> principals = s.getPrincipals(RolePrincipal.class);
        if (principals.isEmpty()) {
            return false;
        }
        for (Principal p : principals) {
            if (p.getName().equals(role)) {
                return true;
            }
        }
        return false;
    }

    public Subject getSubject() {
        return (Subject) getSession(true).getAttribute(SUBJECT_KEY);
    }

}

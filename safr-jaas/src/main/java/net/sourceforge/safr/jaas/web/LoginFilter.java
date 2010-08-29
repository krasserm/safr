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

import java.io.IOException;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.safr.jaas.login.DefaultLoginContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Martin Krasser
 */
public abstract class LoginFilter implements Filter {

    private static final Log log = LogFactory.getLog(LoginFilter.class);
    
    private String logoutUrl;
    
    public void destroy() {
    }

    public void init(FilterConfig config) throws ServletException {
        logoutUrl = config.getInitParameter("logoutUrl");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        RequestWrapper requestWrapper = createRequestWrapper((HttpServletRequest) request);
        ResponseWrapper responseWrapper = createResponseWrapper((HttpServletResponse) response);

        String path = requestWrapper.getServletPath();
        
        if (path != null && path.equals(logoutUrl)) {
            requestWrapper.getSession(true).invalidate();
            challenge(requestWrapper, responseWrapper);
        } else if (requestWrapper.isAuthenticated()) {
            chain.doFilter(requestWrapper, responseWrapper);
        } else if (requestWrapper.hasCredentials()) {
            try {
                authenticate(requestWrapper);
                forward(requestWrapper, responseWrapper);
            } catch (LoginException e) {
                challenge(requestWrapper, responseWrapper);
            }
        } else {
            challenge(requestWrapper, responseWrapper);
        }

    }

    protected void authenticate(RequestWrapper request) throws LoginException {

        CallbackHandler handler = createCallbackHandler(request);
        DefaultLoginContext context = new DefaultLoginContext(handler);
        try {
            context.login();
        } catch (LoginException e) {
            log.info("login failed (message=" + e.getMessage() + ")");
            throw e;
        }
        request.setAuthenticated(context.getSubject());
    }

    protected abstract RequestWrapper createRequestWrapper(HttpServletRequest request);
    protected abstract ResponseWrapper createResponseWrapper(HttpServletResponse response);
    protected abstract CallbackHandler createCallbackHandler(RequestWrapper request);    
    
    protected abstract void forward(RequestWrapper request, ResponseWrapper response) throws IOException, ServletException;
    protected abstract void challenge(RequestWrapper request, ResponseWrapper response) throws IOException, ServletException ;
 
}

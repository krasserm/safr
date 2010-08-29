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
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import javax.security.auth.Subject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Martin Krasser
 */
public class PrivilegedActionFilter implements Filter {

    public void destroy() {
    }

    public void init(FilterConfig chain) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (isWrappedRequestResponse(request, response)) {
            RequestWrapper requestWrapper = (RequestWrapper)request;
            ResponseWrapper responseWrapper = (ResponseWrapper)response;
            Subject subject = requestWrapper.getSubject();
            if (subject == null) {
                chain.doFilter(request, response);
            } else {
                doFilterPrivileged(requestWrapper, responseWrapper, chain);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
    
    private void doFilterPrivileged(RequestWrapper request, ResponseWrapper response, FilterChain chain) throws IOException, ServletException {
        try {
            Subject.doAsPrivileged(request.getSubject(), new FilterAction(request, response, chain), null);
        } catch (PrivilegedActionException e) {
            Throwable cause = e.getCause().getCause();
            if (cause instanceof IOException) {
                throw (IOException)cause;
            } else if (cause instanceof ServletException) {
                throw (ServletException)cause;
            } else {
                throw new ServletException(e);
            }
        }
    }
    
    private static boolean isWrappedRequestResponse(ServletRequest request, ServletResponse response) {
        return (request instanceof RequestWrapper) && (response instanceof ResponseWrapper);
    }
    
    private static class FilterAction implements PrivilegedExceptionAction<Object> {

        private RequestWrapper request;
        private ResponseWrapper response;
        private FilterChain chain;
        
        public FilterAction(RequestWrapper request, ResponseWrapper response, FilterChain chain) {
            this.request = request;
            this.response = response;
            this.chain = chain;
        }
        
        public Object run() throws Exception {
            chain.doFilter(request, response);
            return null;
        }

    }
    
}

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
package net.sourceforge.safr.jaas.web.form;

import java.io.IOException;

import javax.security.auth.callback.CallbackHandler;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.safr.jaas.login.DefaultCallbackHandler;
import net.sourceforge.safr.jaas.web.LoginFilter;
import net.sourceforge.safr.jaas.web.RequestWrapper;
import net.sourceforge.safr.jaas.web.ResponseWrapper;

/**
 * @author Martin Krasser
 */
public class FormLoginFilter extends LoginFilter {

    private String loginUrl;
    
    public String getLoginUrl() {
        return loginUrl;
    }
    
    public void destroy() {
        super.destroy();
    }

    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        loginUrl = config.getInitParameter("loginUrl");
    }

    @Override
    protected RequestWrapper createRequestWrapper(HttpServletRequest request) {
        return new FormRequestWrapper(request);
    }

    @Override
    protected ResponseWrapper createResponseWrapper(HttpServletResponse response) {
        return new FormResponseWrapper(response);
    }

    @Override
    protected CallbackHandler createCallbackHandler(RequestWrapper request) {
        FormRequestWrapper frw = (FormRequestWrapper)request;
        String username = frw.getUsername();
        char[] password = frw.getPassword();
        return new DefaultCallbackHandler(username, password);
    }

    @Override
    protected void challenge(RequestWrapper request, ResponseWrapper response) throws IOException, ServletException {
        request.getRequestDispatcher(loginUrl).forward(request, response);
    }

    @Override
    protected void forward(RequestWrapper request, ResponseWrapper response) throws IOException, ServletException {
        request.getRequestDispatcher("/").forward(request, response);
    }

}

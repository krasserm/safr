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

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.safr.jaas.web.RequestWrapper;

/**
 * @author Martin Krasser
 */
public class FormRequestWrapper extends RequestWrapper {

    public FormRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getAuthType() {
        return HttpServletRequest.FORM_AUTH;
    }

    public boolean hasCredentials() {
        return getRequest().getParameter("j_username") != null &&
               getRequest().getParameter("j_password") != null;
    }

    public String getUsername() {
        return getRequest().getParameter("j_username");
    }

    public char[] getPassword() {
        return (getRequest().getParameter("j_password") != null) ? 
                getRequest().getParameter("j_password").toCharArray() : null;
    }

}

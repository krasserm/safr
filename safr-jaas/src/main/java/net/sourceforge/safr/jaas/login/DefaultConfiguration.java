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

import java.util.Map;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;

/**
 * @author Martin Krasser
 */
class DefaultConfiguration extends Configuration {
    
    private AppConfigurationEntry[] entries;
    
    public DefaultConfiguration(String loginModuleName, LoginModuleControlFlag controlFlag, Map<String, ?> options) {
        AppConfigurationEntry entry = new AppConfigurationEntry(loginModuleName, controlFlag, options);
        entries = new AppConfigurationEntry[] {entry};
    }
    
    @Override
    public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
        return entries;
    }

    @Override
    public void refresh() {
        // nothing to do
    }

}

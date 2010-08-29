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
package net.sourceforge.safr.jaas.policy;

import java.security.Policy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Martin Krasser
 */
public class InstancePolicyUtil {

    private static final Log LOG = LogFactory.getLog(InstancePolicyUtil.class);
    
    public static void installInstancePolicy(InstancePolicy ip) {
        Policy current = Policy.getPolicy();
        if (current instanceof InstancePolicy) {
            LOG.info("instance policy already installed");
        } else {
            ip.setDefaultPolicy(current);
            Policy.setPolicy(ip);
            LOG.info("instance policy installed");
        }
    }
    
    public static void uninstallInstancePolicy() {
        Policy current = Policy.getPolicy();
        if (current instanceof InstancePolicy) {
            InstancePolicy ip = (InstancePolicy)current;
            Policy.setPolicy(ip.getDefaultPolicy());
            LOG.info("instance policy uninstalled");
        } else {
            LOG.info("instance policy not installed");
        }
    }
}

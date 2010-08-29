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
package net.sourceforge.safr.core.integration.springaop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import net.sourceforge.safr.core.annotation.SecureAction;
import net.sourceforge.safr.core.integration.support.TestBase;

import org.junit.Test;

/**
 * @author Martin Krasser
 */
public class SecureParamTest extends TestBase {

    // -------------------------------------------------------------------
    //  parameter annotation
    // -------------------------------------------------------------------
    
    @Test
    public void testM01a() {
        service.m01a("1", "2");
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains("1", SecureAction.UPDATE));
    }
    
    @Test
    public void testM01b() {
        service.m01b("1", "2");
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains("2", SecureAction.UPDATE));
    }
    
    @Test
    public void testM01c() {
        service.m01c("1", "2");
        assertEquals("wrong check history size", 2, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains("1", SecureAction.UPDATE));
        assertTrue("wrong check history content", checkHistory.contains("2", SecureAction.UPDATE));
        
    }
    
    @Test
    public void testM01d() {
        service.m01d("1", "2");
        assertEquals("wrong check history size", 3, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains("1", SecureAction.UPDATE));
        assertTrue("wrong check history content", checkHistory.contains("1", SecureAction.CREATE));
        assertTrue("wrong check history content", checkHistory.contains("2", SecureAction.DELETE));
    }
    
}

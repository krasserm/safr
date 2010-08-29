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
public class InheritanceTest extends TestBase {

    // -------------------------------------------------------------------
    //  parameter annotation inheritance from interface
    // -------------------------------------------------------------------
    
    @Test
    public void testM02a() {
        service.m02a("1", "2");
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains("1", SecureAction.UPDATE));
    }
    
    @Test
    public void testM02b() {
        service.m02b("1", "2");
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains("1", SecureAction.CREATE));
    }
    
    @Test
    public void testM02c() {
        service.m02c("1", "2");
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains("1", SecureAction.UPDATE));
    }
    
    @Test
    public void testM02d() {
        service.m02d("1", "2");
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains("2", SecureAction.DELETE));
    }
    
    // -------------------------------------------------------------------
    //  parameter annotation inheritance from base class
    // -------------------------------------------------------------------
    
    @Test
    public void testM03a() {
        service.m03a("1", "2");
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains("1", SecureAction.UPDATE));
    }
    
    @Test
    public void testM03b() {
        service.m03b("1", "2");
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains("1", SecureAction.CREATE));
    }
    
    @Test
    public void testM03c() {
        service.m03c("1", "2");
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains("1", SecureAction.UPDATE));
    }
    
    @Test
    public void testM03d() {
        service.m03d("1", "2");
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains("2", SecureAction.DELETE));
    }
    
    // -------------------------------------------------------------------
    //  method annotation inheritance from interface (@Secure)
    // -------------------------------------------------------------------

    // -------------------------------------------------------------------
    //  method annotation inheritance from base class (@Secure)
    // -------------------------------------------------------------------

    // -------------------------------------------------------------------
    //  method annotation inheritance from interface (@Filter)
    // -------------------------------------------------------------------

    // -------------------------------------------------------------------
    //  method annotation inheritance from base class (@Filter)
    // -------------------------------------------------------------------

    // -------------------------------------------------------------------
    //  combination of annotation inheritance
    // -------------------------------------------------------------------
    
    @Test
    public void testM09a() {
        service.m09a("1");
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.containsSecureAction(SecureAction.EXECUTE));
    }
    
    @Test
    public void testM09b() {
        service.m09b("1");
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains("1", SecureAction.UPDATE));
    }
    
    @Test
    public void testM09c() {
        service.m09c("1");
        assertEquals("wrong check history size", 0, checkHistory.size());
    }

}

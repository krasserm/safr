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
package net.sourceforge.safr.core.integration.aspectj;

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
    //  parameter annotation inheritance
    // -------------------------------------------------------------------
    
    @Test
    public void testM02a() {
        domainObject.m02a("1", "2");
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains("1", SecureAction.UPDATE));
    }
    
    @Test
    public void testM02b() {
        domainObject.m02b("1", "2");
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains("1", SecureAction.CREATE));
    }
    
    @Test
    public void testM02c() {
        domainObject.m02c("1", "2");
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains("1", SecureAction.UPDATE));
    }
    
    @Test
    public void testM02d() {
        domainObject.m02d("1", "2");
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains("2", SecureAction.DELETE));
    }
    
    // -------------------------------------------------------------------
    //  method annotation inheritance (@Secure)
    // -------------------------------------------------------------------

    @Test
    public void testM03a() {
        domainObject.m03a();
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains(domainObject, SecureAction.UPDATE));
    }
    
    @Test
    public void testM03b() {
        domainObject.m03b();
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains(domainObject, SecureAction.CREATE));
    }
    
    // -------------------------------------------------------------------
    //  method annotation inheritance (@Filter)
    // -------------------------------------------------------------------

    // -------------------------------------------------------------------
    //  combination of annotation inheritance
    // -------------------------------------------------------------------
    
    @Test
    public void testM09a() {
        domainObject.m09a("1");
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains(domainObject, SecureAction.EXECUTE));
    }
    
    @Test
    public void testM09b() {
        domainObject.m09b("1");
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains("x", SecureAction.READ));
    }
    
}

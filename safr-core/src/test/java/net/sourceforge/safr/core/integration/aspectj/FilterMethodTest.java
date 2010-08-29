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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import net.sourceforge.safr.core.integration.support.TestBase;
import net.sourceforge.safr.core.integration.support.TestUtil;

import org.junit.Test;


/**
 * @author Martin Krasser
 */
public class FilterMethodTest extends TestBase {

    // -------------------------------------------------------------------
    //  method annotation (@Filter)
    // -------------------------------------------------------------------

    @Test
    public void testM09c() {
        getTestAccessManager().setReadCheck(true);
        assertEquals("x", domainObject.m09c("x"));
        try {
            service.m09d("y");
            fail("nullifyResult element on annotation ignored");
        } catch (AccessControlException e) {
            // test passed
        }
        getTestAccessManager().setReadCheck(false);
    }
    
    @Test
    public void testM20a() throws Exception {
        Collection<String> c = TestUtil.createStringCollection(HashSet.class, "x", "y", "z");
        Collection<String> r = domainObject.m20a(c);
        assertEquals("wrong result collection type", ArrayList.class, r.getClass());
        assertFalse(c == r);
        checkReadHistory("x", "y", "z");
    }

    @Test
    public void testM20b() throws Exception {
        Collection<String> c = TestUtil.createStringCollection(HashSet.class, "x", "y", "z");
        Collection<String> r = domainObject.m20b(c);
        assertEquals("wrong result collection type", HashSet.class, r.getClass());
        assertTrue(c == r);
        checkReadHistory("x", "y", "z");
    }

    @Test
    public void testM21a() {
        int result = domainObject.m21a();
        checkReadHistory(new Integer(result));        
    }
    
}

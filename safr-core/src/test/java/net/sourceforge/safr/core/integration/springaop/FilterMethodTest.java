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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import net.sourceforge.safr.core.filter.ResultFilterException;
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
    public void testM09d() {
        getTestAccessManager().setReadCheck(true);
        assertEquals("x", service.m09d("x"));
        try {
            service.m09d("y");
            fail("nullifyResult element on annotation ignored");
        } catch (AccessControlException e) {
            // test passed
        }
        getTestAccessManager().setReadCheck(false);
    }
    
    @Test
    public void testM20a_1() throws Exception {
        Collection<String> c = TestUtil.createStringCollection(HashSet.class, "x", "y", "z");
        Collection<String> r = service.m20a(c);
        assertEquals("wrong result collection type", ArrayList.class, r.getClass());
        assertFalse(c == r);
        checkReadHistory("x", "y", "z");
    }

    @Test
    public void testM20a_2() throws Exception {
        Collection<String> c = TestUtil.createStringCollection(HashSet.class, null, null, null);
        Collection<String> r = service.m20a(c);
        assertEquals("wrong result collection type", ArrayList.class, r.getClass());
        assertFalse(c == r);
        checkReadHistory(); // null pointers aren't checked
    }
    
    @Test
    public void testM20a_3() throws Exception {
        Collection<String> r = service.m20a(null);
        assertNull(r);
        checkReadHistory();        
    }
    
    @Test
    public void testM20b() throws Exception {
        List<String> c = TestUtil.createStringCollection(LinkedList.class, "x", "y", "z");
        List<String> r = service.m20b(c);
        assertEquals("wrong result collection type", ArrayList.class, r.getClass());
        assertFalse(c == r);
        checkReadHistory("x", "y", "z");
    }

    @Test
    public void testM20c() throws Exception {
        Set<String> c = TestUtil.createStringCollection(HashSet.class, "x", "y", "z");
        Set<String> r = service.m20c(c);
        assertEquals("wrong result collection type", HashSet.class, r.getClass());
        assertFalse(c == r);
        checkReadHistory("x", "y", "z");
    }

    @Test
    public void testM20d() throws Exception {
        SortedSet<String> c = TestUtil.createStringCollection(TreeSet.class, "x", "y", "z");
        SortedSet<String> r = service.m20d(c);
        assertEquals("wrong result collection type", TreeSet.class, r.getClass());
        assertFalse(c == r);
        checkReadHistory("x", "y", "z");
    }

    @Test
    public void testM20e() throws Exception {
        Vector<String> c = TestUtil.createStringCollection(Vector.class, "x", "y", "z");
        try {
            service.m20e(c);
            fail("method invoked without valid filter definition");
        } catch (ResultFilterException e) {
        }
    }

    @Test
    public void testM21a_1() throws Exception {
        Collection<String> c = TestUtil.createStringCollection(HashSet.class, "x", "y", "z");
        Collection<String> r = service.m21a(c);
        assertEquals("wrong result collection type", HashSet.class, r.getClass());
        assertTrue(c == r);
        checkReadHistory("x", "y", "z");
    }

    @Test
    public void testM21a_2() throws Exception {
        Collection<String> c = TestUtil.createStringCollection(HashSet.class, null, null, null);
        Collection<String> r = service.m21a(c);
        assertEquals("wrong result collection type", HashSet.class, r.getClass());
        assertTrue(c == r);
        checkReadHistory(); // null pointers aren't checked 
    }

    @Test
    public void testM21a_3() throws Exception {
        Collection<String> r = service.m21a(null);
        assertNull(r);
        checkReadHistory();
    }

    @Test
    public void testM21b() throws Exception {
        Vector<String> c = TestUtil.createStringCollection(Vector.class, "x", "y", "z");
        Vector<String> r = service.m21b(c);
        assertEquals("wrong result collection type", Vector.class, r.getClass());
        assertTrue(c == r);
        checkReadHistory("x", "y", "z");
    }

    @Test
    public void testM22a() throws Exception {
        Collection<String> c = TestUtil.createStringCollection(HashSet.class, "x", "y", "z");
        Collection<String> r = service.m22a(c);
        assertEquals("wrong result collection type", LinkedList.class, r.getClass());
        assertFalse(c == r);
        checkReadHistory("x", "y", "z");
    }

    @Test
    public void testM22b() throws Exception {
        Vector<String> c = TestUtil.createStringCollection(Vector.class, "x", "y", "z");
        Vector<String> r = service.m22b(c);
        assertEquals("wrong result collection type", Vector.class, r.getClass());
        assertFalse(c == r);
        checkReadHistory("x", "y", "z");
    }

    @Test
    public void testM29a() throws Exception {
        String[] a = new String[] {"x", "y", "z"};
        String[] r = service.m29a(a);
        assertFalse(a == r);
        checkReadHistory("x", "y", "z");
    }

}

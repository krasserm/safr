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
package net.sourceforge.safr.sample.hellosaf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.AccessControlException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * @author Martin Krasser
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/context.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class SampleTest {

    @Autowired
    private Service service;
    
    @Test
    public void testFindDomainObject() {
        DomainObject result = service.findDomainObject(1);
        assertNotNull(result);
        result = service.findDomainObject(11);
        assertNull(result);
    }

    @Test
    public void testFindDomainObjects() {
        List<DomainObject> result = service.findDomainObjects(2, 3, 4);
        assertEquals(3, result.size());
        result = service.findDomainObjects(2, 3, 14);
        assertEquals(2, result.size());
        assertTrue(result.contains(new DomainObject(2)));
        assertTrue(result.contains(new DomainObject(3)));
        result = service.findDomainObjects(12, 13, 14);
        assertEquals(0, result.size());
    }

    @Test
    public void testDeleteDomainObject() {
        service.deleteDomainObject(new DomainObject(5));
        try {
            service.deleteDomainObject(new DomainObject(15));
            fail();
        } catch (AccessControlException e) {
            // expected
        }
        
    }

    @Test
    public void testUpdate() {
        new DomainObject(6).update();
        try {
            new DomainObject(16).update();
            fail();
        } catch (AccessControlException e) {
            // expected
        }
    }
    
}

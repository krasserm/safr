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

import static org.junit.Assert.*;

import net.sourceforge.safr.core.annotation.SecureAction;
import net.sourceforge.safr.core.integration.support.TestBase;

import org.junit.Test;

/**
 * @author Martin Krasser
 */
public class OrderTest extends TestBase {

    // -------------------------------------------------------------------
    //  all annotations
    // -------------------------------------------------------------------

    @Test
    public void testM30a() {
        service.m30a("1", "2");
        assertEquals("wrong check history size", 7, checkHistory.size());
        assertTrue("wrong check", checkHistory.get(0).containsSecureAction(SecureAction.EXECUTE));
        assertTrue("wrong check", checkHistory.get(1).containsSecureAction(SecureAction.UPDATE));
        assertTrue("wrong check", checkHistory.get(2).contains("1", SecureAction.CREATE));
        assertTrue("wrong check", checkHistory.get(3).contains("1", SecureAction.READ));
        assertTrue("wrong check", checkHistory.get(4).contains("2", SecureAction.DELETE));
        assertTrue("wrong check", checkHistory.get(5).contains("x", SecureAction.READ));
        assertTrue("wrong check", checkHistory.get(6).contains("y", SecureAction.READ));
    }
    
}

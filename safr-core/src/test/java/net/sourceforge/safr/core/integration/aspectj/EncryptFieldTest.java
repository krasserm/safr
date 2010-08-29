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

import static net.sourceforge.safr.core.integration.support.Crypto.Operation.DECRYPT;
import static net.sourceforge.safr.core.integration.support.Crypto.Operation.ENCRYPT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.sourceforge.safr.core.integration.sample.DomainObjectD;
import net.sourceforge.safr.core.integration.sample.DomainObjectE;
import net.sourceforge.safr.core.integration.support.Crypto;
import net.sourceforge.safr.core.integration.support.TestBase;
import net.sourceforge.safr.core.util.HintMap;

import org.junit.Test;

/**
 * @author Martin Krasser
 */
public class EncryptFieldTest extends TestBase {
    
    @Test
    public void testSetPropertyNonNull() {
        DomainObjectD d = new DomainObjectD();
        DomainObjectE e = new DomainObjectE();
        d.setS("blah");
        assertEquals(1, cryptoHistory.size());
        assertTrue(cryptoHistory.contains(new Crypto(ENCRYPT, "blah", d)));
        d.setS("blub");
        assertEquals(2, cryptoHistory.size());
        assertTrue(cryptoHistory.contains(new Crypto(ENCRYPT, "blub", d)));
        e.setT("oink");
        assertTrue(cryptoHistory.contains(new Crypto(ENCRYPT, "oink", e, 
                new HintMap("algorithm=stupid", "purpose=test"))));
    }
    
    @Test
    public void testGetPropertyNonNull() {
        DomainObjectD obj = new DomainObjectD("blah");
        assertEquals(1, cryptoHistory.size());
        assertTrue(cryptoHistory.contains(new Crypto(ENCRYPT, "blah", obj)));
        assertEquals("blah", obj.getS());
        assertEquals(2, cryptoHistory.size());
        assertTrue(cryptoHistory.contains(new Crypto(DECRYPT, "halb", obj)));
    }
    
    @Test
    public void testAccessField() throws Exception {
        DomainObjectD obj = new DomainObjectD("blah");
        assertEquals(1, cryptoHistory.size());
        assertEquals("halb", obj.getSReflective());
        assertEquals(1, cryptoHistory.size());
        obj.setSReflective("blub");
        assertEquals(1, cryptoHistory.size());
        assertEquals("blub", obj.getSReflective());
        assertEquals(1, cryptoHistory.size());
    }
 
}

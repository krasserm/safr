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

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.sourceforge.safr.core.integration.sample.DomainObjectD;
import net.sourceforge.safr.core.integration.support.TestBase;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Martin Krasser
 */
public class PersistFieldTest extends TestBase {

    private static JAXBContext JAXB;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        JAXB = JAXBContext.newInstance("net.sourceforge.safr.core.integration.sample");
    }

    @Test
    public void testSetPropertyNonNull() throws Exception {
        DomainObjectD domainObjectD = new DomainObjectD();
        domainObjectD.setS("blah");
        String xml = toXml(domainObjectD);
        // XML contains pseudo-encrypted (reverted) value
        assertTrue(xml.contains("<s>halb</s>"));
        domainObjectD = fromXml(xml);
        // getter returns decrypted value
        assertEquals("blah", domainObjectD.getS());
    }
 
    private static String toXml(DomainObjectD d) throws Exception {
        Marshaller marshaller = JAXB.createMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(d, writer);
        return writer.getBuffer().toString();
        
    }

    private static DomainObjectD fromXml(String xml) throws Exception {
        Unmarshaller unmarshaller = JAXB.createUnmarshaller();
        return (DomainObjectD)unmarshaller.unmarshal(new StringReader(xml));
    }

}

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
package net.sourceforge.safr.sample.crypto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import net.sourceforge.safr.core.provider.CryptoProvider;
import net.sourceforge.safr.sample.crypto.domain.Customer;
import net.sourceforge.safr.sample.crypto.service.CustomerService;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * @author Martin Krasser
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/context.xml" })
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class SampleTest {

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private CryptoProvider cryptoProvider;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testEncrypt() {
        Customer customer = new Customer("111");
        // Field value gets encrypted (@Encrypt)
        customer.setCreditCardNumber("1234");
        customer.setName("Tester");
        
        // Hibernate stores encrypted field
        customerService.create(customer);

        // Read encrypted field value directly
        String ccn = readCreditCardNumber("111");
        // Assert that it is not clear text
        assertFalse(ccn.equals("1234"));
        // Explicit decryption using provider
        assertEquals("1234", cryptoProvider.decrypt(ccn, null, null));
        
        
        // Hibernate reads encrypted field
        customer = customerService.find("111");
        // Field value gets decrypted (@Encrypt)
        assertEquals("1234", customer.getCreditCardNumber());
    }
    
    private String readCreditCardNumber(String customerId) {
        return (String) jdbcTemplate.queryForObject(
                "SELECT CUSTOMER.CREDIT_CARD_NUMBER " +
                "FROM TEST.CUSTOMER CUSTOMER " +
                "WHERE CUSTOMER.ID = ?", 
                new Object[] {"111"}, String.class);        
    }
    
}

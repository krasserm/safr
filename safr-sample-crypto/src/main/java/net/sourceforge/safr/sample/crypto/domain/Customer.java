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
package net.sourceforge.safr.sample.crypto.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.sourceforge.safr.core.annotation.Encrypt;
import net.sourceforge.safr.core.annotation.SecureObject;

/**
 * @author Martin Krasser
 */
@Entity
@Table(name = "CUSTOMER", schema = "TEST")
@SecureObject
public class Customer {

    @Id
    @Column(name="ID")
    private String id;
    
    @Column(name="NAME")
    private String name;

    @Encrypt
    @Column(name="CREDIT_CARD_NUMBER")
    private String creditCardNumber;
 
    public Customer() {
        this(null);
    }
    
    public Customer(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }    
    
}

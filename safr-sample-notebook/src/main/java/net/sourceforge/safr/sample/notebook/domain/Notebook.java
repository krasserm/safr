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
package net.sourceforge.safr.sample.notebook.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import net.sourceforge.safr.core.annotation.Secure;
import net.sourceforge.safr.core.annotation.SecureAction;
import net.sourceforge.safr.core.annotation.SecureObject;
import net.sourceforge.safr.sample.usermgnt.domain.User;

/**
 * @author Martin Krasser
 */
@SecureObject
public class Notebook {

    private String id;
    
    private User owner;

    private List<Entry> entries;
    
    public Notebook(User owner) {
        this(UUID.randomUUID().toString(), owner);
    }
    
    public Notebook(String id, User owner) {
        this.entries = new ArrayList<Entry>();
        this.id = id;
        this.owner = owner;
    }
    
    public String getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    @Secure(SecureAction.UPDATE)
    public void addEntry(Entry entry) {
        entries.add(entry);
    }
    
    @Secure(SecureAction.UPDATE)
    public void removeEntry(Entry entry) {
        entries.remove(entry);
    }

    public void removeEntry(String entryId) {
        Entry entry = getEntry(entryId);
        if (entry != null) {
            removeEntry(entry);
        }
    }

    public Entry getEntry(String entryId) {
        for (Entry entry : entries) {
            if (entry.getId().equals(entryId)) {
                return entry;
            }
        }
        return null;
    }
    
    public List<Entry> getEntries() {
        return Collections.unmodifiableList(entries);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Notebook)) {
            return false;
        }
        Notebook n = (Notebook)obj;
        return id.equals(n.id); 
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}

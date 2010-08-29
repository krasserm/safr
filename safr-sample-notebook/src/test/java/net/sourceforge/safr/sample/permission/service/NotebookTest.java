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
package net.sourceforge.safr.sample.permission.service;

import static org.junit.Assert.assertEquals;
import net.sourceforge.safr.sample.notebook.domain.Entry;
import net.sourceforge.safr.sample.notebook.domain.Notebook;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Martin Krasser
 */
public class NotebookTest {

    private Entry entry;
    
    private Notebook notebook;
    
    @Before
    public void setUp() throws Exception {
        entry = new Entry(null);
        notebook = new Notebook(null);
        notebook.addEntry(entry);
    }

    @Test
    public void testRemoveEntryEntry() {
        assertEquals(1, notebook.getEntries().size());
        notebook.removeEntry(new Entry("fake", ""));
        assertEquals(1, notebook.getEntries().size());
        notebook.removeEntry(new Entry(entry.getId(), ""));
        assertEquals(0, notebook.getEntries().size());
    }

    @Test
    public void testRemoveEntryString() {
        assertEquals(1, notebook.getEntries().size());
        notebook.removeEntry("fake");
        assertEquals(1, notebook.getEntries().size());
        notebook.removeEntry(entry.getId());
        assertEquals(0, notebook.getEntries().size());
    }

}

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
package net.sourceforge.safr.sample.notebook.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sourceforge.safr.core.annotation.Filter;
import net.sourceforge.safr.sample.notebook.domain.Notebook;
import net.sourceforge.safr.sample.usermgnt.service.UserService;

/**
 * @author Martin Krasser
 */
@Service
public class NotebookServiceImpl implements NotebookService {

    private Map<String, Notebook> notebooks;
    
    private Notebook publicNotebook;
    
    @Autowired
    private UserService userService; 
    
    public NotebookServiceImpl() {
        this.notebooks = new HashMap<String, Notebook>();
        this.publicNotebook = new Notebook(PUBLIC_NOTEBOOK_ID, null);
    }
    
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    public void createNotebook(Notebook notebook) {
        notebooks.put(notebook.getId(), notebook);
    }

    public void deleteNotebook(Notebook notebook) {
        if (notebook != null) {
            notebooks.remove(notebook.getId());
        }
    }

    public Notebook findNotebook(String id) {
        if (id.equals(PUBLIC_NOTEBOOK_ID)) {
            return publicNotebook;
        }
        return notebooks.get(id);
    }

    public Collection<Notebook> findNotebooks() {
        return notebooks.values();
    }
        
    @Filter(copyResultCollection=false)
    public Collection<Notebook> findNotebooksByUserId(String userId) {
        // Override annotation defined on interface for this method. Since 
        // we're creating a new result collection here, there's no need that 
        // the security filter creates a copy again.
        ArrayList<Notebook> result = new ArrayList<Notebook>(1);
        for (Notebook notebook : notebooks.values()) {
            if (notebook.getOwner().getId().equals(userId)) {
                result.add(notebook);
            }
        }
        return result;
    }

    @PostConstruct
    public void bootstrap() {
        // local createNotebook() calls are not intercepted by security proxy 
        createNotebook(new Notebook("nb1-user1", userService.findUser("user1")));
        createNotebook(new Notebook("nb1-user2", userService.findUser("user2")));
        createNotebook(new Notebook("nb1-user3", userService.findUser("user3")));
    }
    
}

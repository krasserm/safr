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
package net.sourceforge.safr.sample.web;

import net.sourceforge.safr.sample.notebook.domain.Notebook;
import net.sourceforge.safr.sample.notebook.service.NotebookService;
import net.sourceforge.safr.sample.usermgnt.domain.User;
import net.sourceforge.safr.sample.usermgnt.service.UserService;
import net.sourceforge.safr.sample.web.helper.NotebookIdBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Martin Krasser
 */
@Controller
@RequestMapping("/listNotebooks.htm")
public class ListNotebooksController {

    @Autowired
	private NotebookService notebookService;

    @Autowired
	private UserService userService;
	
    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET)
    public String handleGet(@ModelAttribute("nbid")NotebookIdBean bean, ModelMap model) {
        model.put("notebooks", notebookService.findNotebooks());
        return "notebookList";
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST)
    public String handlePost(@ModelAttribute("nbid")NotebookIdBean bean, ModelMap model) {
        
        User current = userService.currentUser();
        Notebook notebook = new Notebook(bean.getIdentifier(), current);
        notebookService.createNotebook(notebook);
        model.put("notebooks", notebookService.findNotebooks());
        return "notebookList";
    }
    
}

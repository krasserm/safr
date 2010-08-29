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

import java.util.Collection;

import net.sourceforge.safr.sample.notebook.domain.Notebook;
import net.sourceforge.safr.sample.notebook.service.NotebookService;
import net.sourceforge.safr.sample.permission.domain.PermissionAssignment;
import net.sourceforge.safr.sample.permission.service.PermissionService;
import net.sourceforge.safr.sample.usermgnt.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Martin Krasser
 */
@Controller
@RequestMapping("/detailPermission.htm")
public class DetailPermissionController {

    @Autowired
    private NotebookService notebookService;
    
    @Autowired
    private PermissionControllerHelper helper;
    
    public PermissionService getPermissionService() {
        return helper.getPermissionService();
    }

    public UserService getUserService() {
        return helper.getUserService();
    }

    public NotebookService getNotebookService() {
        return notebookService;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET)
    public String handleGet(@RequestParam("notebookId")String notebookId, ModelMap model) {
        Notebook notebook = notebookService.findNotebook(notebookId);
        model.put("notebook", notebook);
        return "permissionDetails";
    }

    @ModelAttribute("assignment")
    protected PermissionAssignment permissionAssignment(
            @RequestParam("assigneeId")String assigneeId,
            @RequestParam("notebookId")String notebookId) {
        Notebook notebook = notebookService.findNotebook(notebookId);
        PermissionAssignment assignment = helper.getAssignment(notebook, assigneeId);
        return assignment;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST)
    public String handlePost(
            @ModelAttribute("assignment")PermissionAssignment assignment, 
            @RequestParam("notebookId")String id, ModelMap model) {
        
        getPermissionService().applyPermissionAssignments(assignment);
        Notebook notebook = getNotebookService().findNotebook(id);
        Collection<PermissionAssignment> assignments = helper.getAssignments(notebook);
        model.put("notebook", notebook);
        model.put("assignments", assignments);
        model.put("notebook", notebook);
        return "permissionList";

    }
    
}

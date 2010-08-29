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
package net.sourceforge.safr.sample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.security.PrivilegedActionException;

import javax.security.auth.Subject;

import net.sourceforge.safr.jaas.permission.Action;
import net.sourceforge.safr.sample.usermgnt.domain.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * @author Martin Krasser
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/context.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class SampleTest {

    @Autowired
    private Sample sample;

    @Test
    public void testScenarios() throws Exception {
        runSampleMethodAs("user1", "createNotebook", "nb1");
        runSampleMethodAs("user2", "createNotebook", "nb2");
        runSampleMethodAs("user1", "createNotebookEntry", "nb1", "entry1");
        try {
            runSampleMethodAs("user2", "createNotebookEntry", "nb1", "entry2");
            fail("executed scenario without permissions");
        } catch (SampleException e) {
            assertEquals("wrong security message", Sample.MSG_NO_READ_ACCESS, e.getMessage());
        }
        runSampleMethodAs("user1", "grantAccessToNotebook", "nb1", Action.WRITE, new User("user2"));
        runSampleMethodAs("user2", "createNotebookEntry", "nb1", "entry2");
        runSampleMethodAs("user1", "revokeAccessToNotebook", "nb1", Action.WRITE, new User("user2"));
        runSampleMethodAs("user1", "grantAccessToNotebook", "nb1", Action.READ, new User("user2"));
        try {
            runSampleMethodAs("user2", "createNotebookEntry", "nb1", "entry2");
            fail("executed scenario without permissions");
        } catch (SampleException e) {
            assertEquals("wrong security message", Sample.MSG_NO_WRITE_ACCESS, e.getMessage());
        }
        runSampleMethodAs("user1", "createNotebookEntry", "public", "pub1");
        runSampleMethodAs("user2", "createNotebookEntry", "public", "pub2");
        
    }
    
    private void runSampleMethodAs(String userId, String methodName, Object... args) throws Exception {
        MethodRunner runner = new MethodRunner(sample, methodName, args);
        Subject subject = sample.createSubjectForUser(userId);
        try {
            Subject.doAsPrivileged(subject, runner, null);
        } catch (PrivilegedActionException e) {
            throw (Exception)e.getCause().getCause();
        }
    }
    
}

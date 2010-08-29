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
package net.sourceforge.safr.core.integration.sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sourceforge.safr.core.annotation.Filter;
import net.sourceforge.safr.core.annotation.Secure;
import net.sourceforge.safr.core.annotation.SecureAction;
import net.sourceforge.safr.core.annotation.SecureObject;


/**
 * @author Martin Krasser
 */
@SecureObject
public class DomainObjectA {

    // -------------------------------------------------------------------
    //  parameter annotation
    // -------------------------------------------------------------------
    
    public void m01a(
            @Secure(SecureAction.UPDATE) String p1, 
            /* not annotated */ String p2) {}
    public void m01b(
            /* not annotated */ String p1, 
            @Secure(SecureAction.UPDATE) String p2) {}
    public void m01c(
            @Secure(SecureAction.UPDATE) String p1, 
            @Secure(SecureAction.UPDATE) String p2) {}
    public void m01d(
            @Secure({SecureAction.UPDATE, SecureAction.CREATE}) String p1, 
            @Secure(SecureAction.DELETE) String p2) {}
    
    // -------------------------------------------------------------------
    //  parameter annotation inheritance
    // -------------------------------------------------------------------
    
    public void m02a( // subclass inherits annotations
            @Secure(SecureAction.UPDATE) String p1, 
            String p2) {}
    public void m02b( // subclass overrides annotations
            @Secure(SecureAction.UPDATE) String p1, 
            String p2) {}
    public void m02c( // subclass defines annotations
            String p1, 
            String p2) {}
    public void m02d( // subclass overrides annotations (different location)
            @Secure(SecureAction.UPDATE) String p1, 
            String p2) {}
    
    // -------------------------------------------------------------------
    //  method annotation inheritance (@Secure)
    // -------------------------------------------------------------------

    @Secure(SecureAction.CREATE)
    public void m03a() {}

    @Secure(SecureAction.CREATE)
    public void m03b() {}

    // -------------------------------------------------------------------
    //  method annotation inheritance (@Filter)
    // -------------------------------------------------------------------

    // -------------------------------------------------------------------
    //  combination of annotation inheritance
    // -------------------------------------------------------------------

    @Filter
    public String m09a(String p1) {
        return "x";
    }
    
    @Filter
    public String m09b(String p1) {
        return "x";
    }
    
    @Filter(nullifyResult=false)
    public String m09c(String p1) {
        return p1;
    }
    
    // -------------------------------------------------------------------
    //  method annotation (@Secure)
    // -------------------------------------------------------------------

    @Secure
    public void m10a() {}
    @Secure(SecureAction.NONE)
    public void m10b() {}
    @Secure(SecureAction.EXECUTE)
    public void m10c() {}
    @Secure(SecureAction.CREATE)
    public void m10d() {}
    @Secure({SecureAction.NONE,SecureAction.EXECUTE,SecureAction.CREATE})
    public void m10e() {}
    
    // -------------------------------------------------------------------
    //  method annotation (@Filter)
    // -------------------------------------------------------------------

    @Filter
    public Collection<String> m20a(Collection<String> c) {
        return c;
    }
    
    @Filter(copyResultCollection=false)
    public Collection<String> m20b(Collection<String> c) {
        return c;
    }
    
    @Filter
    public int m21a() {
        return 1;
    }
    
    // -------------------------------------------------------------------
    //  all annotations
    // -------------------------------------------------------------------

    @Filter @Secure({SecureAction.EXECUTE,SecureAction.UPDATE})
    public List<String> m30a(
            @Secure({SecureAction.CREATE,SecureAction.READ})String p1, 
            @Secure(SecureAction.DELETE)String p2) {
        ArrayList<String> result = new ArrayList<String>();
        result.add("x");
        result.add("y");
        return result;
    }
    
}

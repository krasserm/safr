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
import java.util.Set;
import java.util.SortedSet;
import java.util.Vector;

import org.springframework.stereotype.Service;

import net.sourceforge.safr.core.annotation.Secure;
import net.sourceforge.safr.core.annotation.SecureAction;


/**
 * @author Martin Krasser
 */
@Service
public class ServiceImpl extends ServiceBase {

    // -------------------------------------------------------------------
    //  parameter annotation
    // -------------------------------------------------------------------
    
    public void m01a(String p1, String p2) {}
    public void m01b(String p1, String p2) {}
    public void m01c(String p1, String p2) {}
    public void m01d(String p1, String p2) {}
    
    // -------------------------------------------------------------------
    //  parameter annotation inheritance from interface
    // -------------------------------------------------------------------
    
    public void m02a( // inherit annotation
            String p1, 
            String p2) {}
    public void m02b( // override annotation
            @Secure(SecureAction.CREATE) String p1, 
            String p2) {}
    public void m02c( // define annotation
            @Secure(SecureAction.UPDATE) String p1, 
            /* not annotated */ String p2) {}
    public void m02d( // override annotation
            String p1, 
            @Secure(SecureAction.DELETE) String p2) {}

    // -------------------------------------------------------------------
    //  parameter annotation inheritance from base class
    // -------------------------------------------------------------------
    
    public void m03a( // inherit annotation
            String p1, 
            String p2) {}
    public void m03b( // override annotation
            @Secure(SecureAction.CREATE) String p1, 
            String p2) {}
    public void m03c( // define annotation
            @Secure(SecureAction.UPDATE) String p1, 
            String p2) {}
    public void m03d( // override annotation
            String p1, 
            @Secure(SecureAction.DELETE) String p2) {}

    // -------------------------------------------------------------------
    //  method annotation inheritance from interface (@Secure)
    // -------------------------------------------------------------------

    // -------------------------------------------------------------------
    //  method annotation inheritance from base class (@Secure)
    // -------------------------------------------------------------------

    // -------------------------------------------------------------------
    //  method annotation inheritance from interface (@Filter)
    // -------------------------------------------------------------------

    // -------------------------------------------------------------------
    //  method annotation inheritance from base class (@Filter)
    // -------------------------------------------------------------------

    // -------------------------------------------------------------------
    //  combination of annotation inheritance
    // -------------------------------------------------------------------
    
    public String m09a(String p1) {
        return "x";
    } // inherit annotation from base class
    
    public String m09b(@Secure(SecureAction.UPDATE) String p1) { 
        return "x";
    } // override annotation from base class
    
    @Secure(SecureAction.NONE) 
    public String m09c(String p1) { 
        return "x";
    } // drop otherwise inherited annotations
    
    public String m09d(String p1) { 
        return p1;
    }

    // -------------------------------------------------------------------
    //  method annotation (@Secure)
    // -------------------------------------------------------------------

    public void m10a() {}
    
    // -------------------------------------------------------------------
    //  method annotation (@Filter)
    // -------------------------------------------------------------------

    public Collection<String> m20a(Collection<String> c) {
        return c;
    }
    
    public List<String> m20b(List<String> c) {
        return c;
    }

    public Set<String> m20c(Set<String> c) {
        return c;
    }
    
    public SortedSet<String> m20d(SortedSet<String> c) {
        return c;
    }
    
    public Vector<String> m20e(Vector<String> c) {
        return c;
    }
    
    // ---
    
    public Collection<String> m21a(Collection<String> c) {
        return c;
    }
    public Vector<String> m21b(Vector<String> c) {
        return c;
    }
    
    // ---
    
    public Collection<String> m22a(Collection<String> c) {
        return c;
    }
    
    public Vector<String> m22b(Vector<String> c) {
        return c;
    }
    
    // ---
    
    public String[] m29a(String[] a) {
        return a;
    }

    // -------------------------------------------------------------------
    //  all annotations
    // -------------------------------------------------------------------

    public List<String> m30a(String p1, String p2) {
        ArrayList<String> result = new ArrayList<String>();
        result.add("x");
        result.add("y");
        return result;
    }
    
}

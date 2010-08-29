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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.Vector;

import net.sourceforge.safr.core.annotation.Filter;
import net.sourceforge.safr.core.annotation.Secure;
import net.sourceforge.safr.core.annotation.SecureAction;


/**
 * @author Martin Krasser
 */
public interface Service {

    // -------------------------------------------------------------------
    //  parameter annotation
    // -------------------------------------------------------------------
    
    void m01a(
            @Secure(SecureAction.UPDATE) String p1, 
            String p2);
    void m01b(
            String p1, 
            @Secure(SecureAction.UPDATE) String p2);
    void m01c(
            @Secure(SecureAction.UPDATE) String p1, 
            @Secure(SecureAction.UPDATE) String p2);
    void m01d(
            @Secure({SecureAction.UPDATE, SecureAction.CREATE}) String p1, 
            @Secure(SecureAction.DELETE) String p2);
    
    // -------------------------------------------------------------------
    //  parameter annotation inheritance from interface
    // -------------------------------------------------------------------
    
    void m02a( // implementation class inherits annotations
            @Secure(SecureAction.UPDATE) String p1, 
            String p2);
    void m02b( // implementation class overrides annotations
            @Secure(SecureAction.UPDATE) String p1, 
            String p2);
    void m02c( // implementation class defines annotations
            String p1, 
            String p2);
    void m02d( // implementation class overrides annotations (different location)
            @Secure(SecureAction.UPDATE) String p1, 
            String p2);
    
    // -------------------------------------------------------------------
    //  parameter annotation inheritance from base class
    // -------------------------------------------------------------------
    
    void m03a(String p1, String p2);
    void m03b(String p1, String p2);
    void m03c(String p1, String p2);
    void m03d(String p1, String p2);
    
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
    
    @Filter
    String m09a(String p1); // abstract class overrides annotations
    
    @Filter
    String m09b(String p1); // abstract class overrides annotations

    @Filter
    String m09c(String p1); // implementation class overrides annotations

    @Filter(nullifyResult=false)
    String m09d(String p1);

    // -------------------------------------------------------------------
    //  method annotation (@Secure)
    // -------------------------------------------------------------------

    @Secure({SecureAction.NONE,SecureAction.EXECUTE,SecureAction.CREATE})
    void m10a();
    
    // -------------------------------------------------------------------
    //  method annotation (@Filter)
    // -------------------------------------------------------------------

    @Filter 
    Collection<String> m20a(Collection<String> c);
    
    @Filter
    List<String> m20b(List<String> c);
    
    @Filter 
    Set<String> m20c(Set<String> c);
    
    @Filter 
    SortedSet<String> m20d(SortedSet<String> c);
    
    @Filter 
    Vector<String> m20e(Vector<String> c);

    // ---
    
    @Filter(copyResultCollection=false) 
    Collection<String> m21a(Collection<String> c);
    
    @Filter(copyResultCollection=false) 
    Vector<String> m21b(Vector<String> c);

    // ---
    
    @Filter(resultCollectionClass=LinkedList.class)
    Collection<String> m22a(Collection<String> c);
    
    @Filter(resultCollectionClass=Vector.class)
    Vector<String> m22b(Vector<String> c);
    
    // ---
    
    @Filter
    String[] m29a(String[] a); 

    // -------------------------------------------------------------------
    //  all annotations
    // -------------------------------------------------------------------

    @Filter @Secure({SecureAction.EXECUTE,SecureAction.UPDATE})
    public List<String> m30a(
            @Secure({SecureAction.CREATE,SecureAction.READ})String p1, 
            @Secure(SecureAction.DELETE)String p2);
    
}

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

import net.sourceforge.safr.core.annotation.Inherit;
import net.sourceforge.safr.core.annotation.Secure;
import net.sourceforge.safr.core.annotation.SecureAction;
import net.sourceforge.safr.core.annotation.SecureObject;

/**
 * @author Martin Krasser
 */
@SecureObject
public class DomainObjectB extends DomainObjectA {

    // -------------------------------------------------------------------
    //  parameter annotation
    // -------------------------------------------------------------------

    // ...
    
    // -------------------------------------------------------------------
    //  parameter annotation inheritance
    // -------------------------------------------------------------------
    
    @Override @Inherit
    public void m02a( // inherit annotations
            String p1, 
            String p2) {}
    @Override 
    public void m02b( // override annotations
            @Secure(SecureAction.CREATE) String p1, 
            String p2) {}
    @Override 
    public void m02c( // define annotations
            @Secure(SecureAction.UPDATE) String p1, 
            String p2) {}
    @Override 
    public void m02d( // override annotations (different location)
            String p1, 
            @Secure(SecureAction.DELETE) String p2) {}
    
    // -------------------------------------------------------------------
    //  method annotation inheritance (@Secure)
    // -------------------------------------------------------------------

    @Override @Secure(SecureAction.UPDATE)
    public void m03a() {}

    @Override @Inherit
    public void m03b() {}

    // -------------------------------------------------------------------
    //  method annotation inheritance (@Filter)
    // -------------------------------------------------------------------

    // -------------------------------------------------------------------
    //  combination of annotation inheritance
    // -------------------------------------------------------------------

    @Override @Secure 
    public String m09a(String p1) { 
        return "x";
    }
    
    // -------------------------------------------------------------------
    //  method annotation (@Secure)
    // -------------------------------------------------------------------

    // -------------------------------------------------------------------
    //  method annotation (@Filter)
    // -------------------------------------------------------------------

}

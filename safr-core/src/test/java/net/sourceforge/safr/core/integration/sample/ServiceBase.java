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

import net.sourceforge.safr.core.annotation.Secure;
import net.sourceforge.safr.core.annotation.SecureAction;

/**
 * @author Martin Krasser
 */
public abstract class ServiceBase implements Service {

    // -------------------------------------------------------------------
    //  parameter annotation
    // -------------------------------------------------------------------
    
    // -------------------------------------------------------------------
    //  parameter annotation inheritance from interface
    // -------------------------------------------------------------------
    
    // -------------------------------------------------------------------
    //  parameter annotation inheritance from base class
    // -------------------------------------------------------------------
    
    public void m03a( // implementation class inherits annotations
            @Secure(SecureAction.UPDATE) String p1, 
            String p2) {}
    public void m03b( // implementation class overrides annotations
            @Secure(SecureAction.UPDATE) String p1, 
            String p2) {}
    public void m03c( // implementation class defines annotations
            String p1, 
            String p2) {}
    public void m03d( // implementation class overrides annotations (different location)
            @Secure(SecureAction.UPDATE) String p1, 
            String p2) {}
    
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
        
    @Secure
    public abstract String m09a(String p1); // implementation class inherits annotation
    
    @Secure
    public abstract String m09b(String p1); // implementation class overrides annotation
    
    // -------------------------------------------------------------------
    //  method annotation (@Secure)
    // -------------------------------------------------------------------

    // -------------------------------------------------------------------
    //  method annotation (@Filter)
    // -------------------------------------------------------------------

    // -------------------------------------------------------------------
    //  all annotations
    // -------------------------------------------------------------------

}

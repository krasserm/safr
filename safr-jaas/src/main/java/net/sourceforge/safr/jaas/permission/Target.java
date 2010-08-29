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
package net.sourceforge.safr.jaas.permission;

/**
 * @author Martin Krasser
 */
public class Target {

    public static final String WILDCARD = "*";
    
    private String context;
    
    private String classifier;
    
    private String identifier;

    public Target() {
        this(WILDCARD, WILDCARD, WILDCARD);
    }
    
    public Target(String context) {
        this(context, WILDCARD, WILDCARD);
    }
    
    public Target(String context, String classifier) {
        this(context, classifier, WILDCARD);
    }
    
    public Target(String context, String classifier, String identifier) {
        assert context != null;
        assert classifier != null;
        assert identifier != null;
        this.context = context;
        this.classifier = classifier;
        this.identifier = identifier;
    }

    public String getContext() {
        return context;
    }

    public String getClassifier() {
        return classifier;
    }

    public String getIdentifier() {
        return identifier;
    }
 
    public boolean implies(Target target) {
        return implies(context, target.context)
            && implies(classifier, target.classifier)
            && implies(identifier, target.identifier); 
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Target)) {
            return false;
        }
        Target t = (Target)obj;
        return context.equals(t.context) 
            && classifier.equals(t.classifier)
            && identifier.equals(t.identifier);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + context.hashCode();
        result = 37 * result + classifier.hashCode();
        result = 37 * result + identifier.hashCode();
        return result;
    }
    
    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("context=").append(context).append(',');
        buf.append("classifier=").append(classifier).append(',');
        buf.append("identifier=").append(identifier);
        return buf.toString();
    }

    private static boolean implies(String p, String tp) {
        if (p.equals(WILDCARD)) {
            return true;
        }
        return p.equals(tp);
    }
    
}

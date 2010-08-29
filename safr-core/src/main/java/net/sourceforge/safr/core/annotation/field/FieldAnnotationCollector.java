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
package net.sourceforge.safr.core.annotation.field;

import java.lang.reflect.Field;

import net.sourceforge.safr.core.annotation.Encrypt;
import net.sourceforge.safr.core.annotation.info.EncryptAnnotationInfo;
import net.sourceforge.safr.core.attribute.EncryptAttribute;
import net.sourceforge.safr.core.attribute.field.FieldAttributeCollector;
import net.sourceforge.safr.core.attribute.field.FieldAttributeContainer;

/**
 * @author Martin Krasser
 */
public class FieldAnnotationCollector extends FieldAttributeCollector {

    @Override
    public boolean visit(Field f) {
        FieldAttributeContainer container = getFieldAttributeContainer();
        container.clear();
        container.setFieldEncryptAttribute(getFieldEncryptAttribute(f));
        return container.isAnyFieldAttributeDefined();
    }

    private static EncryptAttribute getFieldEncryptAttribute(Field f) {
        return createEncryptAttribute(f.getAnnotation(Encrypt.class));
    }

    private static EncryptAttribute createEncryptAttribute(Encrypt annotation) {
        if (annotation == null) {
            return null;
        }
        return new EncryptAnnotationInfo(annotation);
    }
    
}

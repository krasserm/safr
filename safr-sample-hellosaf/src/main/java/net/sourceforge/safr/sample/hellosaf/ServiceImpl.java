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
package net.sourceforge.safr.sample.hellosaf;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin Krasser
 */
@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    public DomainObject findDomainObject(long id) {
        return new DomainObject(id);
    }

    public List<DomainObject> findDomainObjects(long... ids) {
        ArrayList<DomainObject> result = new ArrayList<DomainObject>();
        for (long id : ids) {
            result.add(new DomainObject(id));
        }
        return result;
    }

    public void deleteDomainObject(DomainObject obj) {
        // delete domain object (from database) ...
    }

}

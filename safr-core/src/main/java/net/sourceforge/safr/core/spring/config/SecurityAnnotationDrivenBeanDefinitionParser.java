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
package net.sourceforge.safr.core.spring.config;

import net.sourceforge.safr.core.annotation.SecurityAnnotationSource;
import net.sourceforge.safr.core.filter.CopyFilterFactory;
import net.sourceforge.safr.core.filter.RemoveFilterFactory;
import net.sourceforge.safr.core.interceptor.SecurityAspect;
import net.sourceforge.safr.core.interceptor.SecurityInterceptor;
import net.sourceforge.safr.core.provider.support.NoopAccessManager;
import net.sourceforge.safr.core.provider.support.NoopCryptoProvider;
import net.sourceforge.safr.core.spring.advice.SecurityAttributeSourceAdvisor;

import org.springframework.aop.config.AopNamespaceUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author Martin Krasser
 */
public class SecurityAnnotationDrivenBeanDefinitionParser extends AbstractBeanDefinitionParser {

    private static final String EMPTY_STRING = "";
    
    private static final String ACCESS_MANAGER_ATTRIBUTE = "access-manager";
    private static final String CRYPTO_PROVIDER_ATTRIBUTE = "crypto-provider";
    private static final String INTERCEPTOR_ORDER_ATTRIBUTE = "interceptor-order";
    private static final String SUPPORT_ASPECTJ_ATTRIBUTE = "support-aspectj"; 
    
    private static final String SECURITY_ATTRIBUTE_SOURCE_PROPERTY = "securityAttributeSource";
    private static final String ACCESS_MANAGER_PROPERTY = "accessManager";
    private static final String CRYPTO_PROVIDER_PROPERTY = "cryptoProvider";
    private static final String SECURITY_INTERCEPTOR_PROPERTY = "securityInterceptor";
    private static final String REMOVE_FILTER_FACTORY_PROPERTY = "removeFilterFactory";
    private static final String COPY_FILTER_FACTORY_PROPERTY = "copyFilterFactory";
    private static final String ORDER_PROPERTY = "order";
    
    private RootBeanDefinition noopAccessManagerDefinition = new RootBeanDefinition(NoopAccessManager.class);
    private RootBeanDefinition noopCryptoProviderDefinition = new RootBeanDefinition(NoopCryptoProvider.class);
    
    @Override
    protected boolean shouldGenerateId() {
        return true;
    }

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        AopNamespaceUtils.registerAutoProxyCreatorIfNecessary(parserContext, element);

        String accessManagerName = element.getAttribute(ACCESS_MANAGER_ATTRIBUTE);
        String cryptoProviderName = element.getAttribute(CRYPTO_PROVIDER_ATTRIBUTE);
        String interceptorOrder = element.getAttribute(INTERCEPTOR_ORDER_ATTRIBUTE);
        String useAspectJ = element.getAttribute(SUPPORT_ASPECTJ_ATTRIBUTE);
        
        RootBeanDefinition removeFilterFactoryDefinition = new RootBeanDefinition(RemoveFilterFactory.class);
        removeFilterFactoryDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        removeFilterFactoryDefinition.setSource(parserContext.extractSource(element));
        setConstructorArgument(removeFilterFactoryDefinition, createAccessManagerValue(accessManagerName));

        RootBeanDefinition copyFilterFactoryDefinition = new RootBeanDefinition(CopyFilterFactory.class);
        copyFilterFactoryDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        copyFilterFactoryDefinition.setSource(parserContext.extractSource(element));
        setConstructorArgument(copyFilterFactoryDefinition, createAccessManagerValue(accessManagerName));
        
        RootBeanDefinition interceptorDefinition = new RootBeanDefinition(SecurityInterceptor.class);
        interceptorDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        interceptorDefinition.setSource(parserContext.extractSource(element));
        interceptorDefinition.getPropertyValues().addPropertyValue(REMOVE_FILTER_FACTORY_PROPERTY, removeFilterFactoryDefinition);
        interceptorDefinition.getPropertyValues().addPropertyValue(COPY_FILTER_FACTORY_PROPERTY, copyFilterFactoryDefinition);
        setPropertyValue(interceptorDefinition, SECURITY_ATTRIBUTE_SOURCE_PROPERTY, createSecurityAttributeSourceValue());
        setPropertyValue(interceptorDefinition, ACCESS_MANAGER_PROPERTY, createAccessManagerValue(accessManagerName));
        setPropertyValue(interceptorDefinition, CRYPTO_PROVIDER_PROPERTY, createCryptoProviderValue(cryptoProviderName));
        
        RootBeanDefinition advisorDefinition = new RootBeanDefinition(SecurityAttributeSourceAdvisor.class);
        advisorDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        advisorDefinition.setSource(parserContext.extractSource(element));
        advisorDefinition.getPropertyValues().addPropertyValue(SECURITY_INTERCEPTOR_PROPERTY, interceptorDefinition);
        advisorDefinition.getPropertyValues().addPropertyValue(ORDER_PROPERTY, interceptorOrder);
        
        if (Boolean.parseBoolean(useAspectJ)) {
            RootBeanDefinition aspectDefinition = new RootBeanDefinition(SecurityAspect.class);
            aspectDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            aspectDefinition.setSource(parserContext.extractSource(element));
            aspectDefinition.setFactoryMethodName("aspectOf");
            aspectDefinition.getPropertyValues().addPropertyValue(REMOVE_FILTER_FACTORY_PROPERTY, removeFilterFactoryDefinition);
            aspectDefinition.getPropertyValues().addPropertyValue(COPY_FILTER_FACTORY_PROPERTY, copyFilterFactoryDefinition);
            setPropertyValue(aspectDefinition, SECURITY_ATTRIBUTE_SOURCE_PROPERTY, createSecurityAttributeSourceValue());
            setPropertyValue(aspectDefinition, ACCESS_MANAGER_PROPERTY, createAccessManagerValue(accessManagerName));
            setPropertyValue(aspectDefinition, CRYPTO_PROVIDER_PROPERTY, createCryptoProviderValue(cryptoProviderName));
            parserContext.getRegistry().registerBeanDefinition(SecurityAspect.class.getName(), aspectDefinition);
        }
        
        return advisorDefinition;
    }
    
    private Object createSecurityAttributeSourceValue() {
        return new RootBeanDefinition(SecurityAnnotationSource.class);
    }
    
    private Object createAccessManagerValue(String accessManagerName) {
        if (accessManagerName.equals(EMPTY_STRING)) {
            return noopAccessManagerDefinition;
        } else {
            return new RuntimeBeanReference(accessManagerName);
        }
    }

    private Object createCryptoProviderValue(String cryptoProviderName) {
        if (cryptoProviderName.equals(EMPTY_STRING)) {
            return noopCryptoProviderDefinition;
        } else {
            return new RuntimeBeanReference(cryptoProviderName);
        }
    }

    private void setPropertyValue(BeanDefinition beanDefinition, String propertyName, Object propertyValue) {
        beanDefinition.getPropertyValues().addPropertyValue(propertyName, propertyValue);
    }

    private void setConstructorArgument(BeanDefinition beanDefinition, Object constructorArgument) {
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(constructorArgument);
    }

}

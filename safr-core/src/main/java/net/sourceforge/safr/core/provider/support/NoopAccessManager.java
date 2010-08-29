package net.sourceforge.safr.core.provider.support;

import net.sourceforge.safr.core.invocation.MethodInvocation;
import net.sourceforge.safr.core.invocation.ProceedingInvocation;
import net.sourceforge.safr.core.provider.AccessManager;

public class NoopAccessManager implements AccessManager {

    public void checkCreate(Object obj) {
        // nothing to do
    }

    public void checkRead(Object obj) {
        // nothing to do
    }

    public void checkUpdate(Object obj) {
        // nothing to do
    }

    public void checkDelete(Object obj) {
        // nothing to do
    }

    public void checkExecute(MethodInvocation invocation) {
        // nothing to do
    }

    public void checkCustomBefore(MethodInvocation invocation) {
        // nothing to do
    }

    public Object checkCustomAround(ProceedingInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

    public Object checkCustomAfter(MethodInvocation invocation, Object result) {
        return result;
    }

}

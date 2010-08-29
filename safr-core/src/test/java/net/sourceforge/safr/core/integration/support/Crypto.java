package net.sourceforge.safr.core.integration.support;

import java.util.Map;

import net.sourceforge.safr.core.util.HintMap;

public class Crypto {

    public enum Operation {ENCRYPT, DECRYPT}

    private Operation operation;
    
    private Object value;
    
    private Object context;
    
    private Map<String, String> hints;
    
    public Crypto(Operation operation, Object value, Object context) {
        this(operation, value, context, new HintMap());
    }
    
    public Crypto(Operation operation, Object value, Object context, Map<String, String> hints) {
        this.operation = operation;
        this.value = value;
        this.context = context;
        this.hints = hints;
    }
    
    public Operation getOperation() {
        return operation;
    }
    
    public Object getValue() {
        return value;
    }
    
    public Object getContext() {
        return context;
    }

    public Map<String, String> getHints() {
        return hints;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Crypto)) {
            return false;
        }
        Crypto that = (Crypto)obj;
        return this.operation.equals(that.operation)
            && this.context.equals(that.context)
            && this.value.equals(that.value)
            && this.hints.equals(that.hints);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + operation.hashCode();
        result = 37 * result + context.hashCode();
        result = 37 * result + value.hashCode();
        result = 37 * result + hints.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return operation + ": value=" + value + " context=" + context;
    }
    
}

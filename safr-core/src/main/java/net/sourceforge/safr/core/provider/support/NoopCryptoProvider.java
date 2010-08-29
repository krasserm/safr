package net.sourceforge.safr.core.provider.support;

import java.util.Map;

import net.sourceforge.safr.core.provider.CryptoProvider;

public class NoopCryptoProvider implements CryptoProvider {

    public Object encrypt(Object value, Object context, Map<String, String> hints) {
        return value;
    }

    public Object decrypt(Object value, Object context, Map<String, String> hints) {
        return value;
    }

}

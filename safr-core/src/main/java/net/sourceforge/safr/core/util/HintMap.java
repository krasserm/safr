package net.sourceforge.safr.core.util;

import java.util.HashMap;

@SuppressWarnings("serial")
public class HintMap extends HashMap<String, String> {

    public HintMap(String... hints) {
        super();
        putHints(hints);
    }
    
    public void putHints(String... hints) {
        for (String hint: hints) {
            putHint(hint);
        }
    }
    
    public void putHint(String hint) {
        String[] pair = hint.split("=");
        if (pair.length == 1) {
            put(pair[0].trim(), null);
        } else {
            put(pair[0].trim(), pair[1].trim());
        }
    }

}

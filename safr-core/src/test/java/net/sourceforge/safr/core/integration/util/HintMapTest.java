package net.sourceforge.safr.core.integration.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import net.sourceforge.safr.core.util.HintMap;

import org.junit.Before;
import org.junit.Test;

public class HintMapTest {

    private HintMap hints;
    
    @Before
    public void setUp() throws Exception {
        hints = new HintMap();
        hints.putHints(" a = b ", "c");
    }

    @Test
    public void testContent() {
        assertEquals(2, hints.size());
        assertEquals("b", hints.get("a"));
        assertTrue(hints.containsKey("c"));
        assertNull(hints.get("c"));
    }
    
}

package ut.com.geneculling.javakata;

import org.junit.Test;
import com.geneculling.javakata.api.MyPluginComponent;
import com.geneculling.javakata.impl.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}
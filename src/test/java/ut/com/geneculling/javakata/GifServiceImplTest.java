package ut.com.geneculling.javakata;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.activeobjects.test.TestActiveObjects;
import net.java.ao.EntityManager;

import com.geneculling.javakata.gifrepo.Gif;
import com.geneculling.javakata.gifrepo.GifService;
import com.geneculling.javakata.gifrepo.GifServiceImpl;
import net.java.ao.test.jdbc.Data;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import net.java.ao.test.junit.ActiveObjectsJUnitRunner;
import static org.junit.Assert.*;

import net.java.ao.test.jdbc.DatabaseUpdater;

import java.util.List;


@RunWith(ActiveObjectsJUnitRunner.class)
@Data(GifServiceImplTest.GifServiceImplTestDatabaseUpdater.class)
public class GifServiceImplTest {

    private EntityManager entityManager;
    private ActiveObjects ao;
    private GifService gifService;

    private static final String GIF_NAME = "This is a todo";
    private static final String GIF_URL = "https://lh3.googleusercontent.com/JdOmjntJTchtZH2swhqqauhJ6K-fZ5_Ufuthk_Dpmz75D5GRweK9XyknN9garC9pIjclL7pLjI8Yj4PTcjwI5hM_-dv6ffREuufNHw=s660";


    @Before
    public void setUp() throws Exception {
        assertNotNull(entityManager);
        ao = new TestActiveObjects(entityManager);
        gifService = new GifServiceImpl(new TestActiveObjects(entityManager));
        gifService.clear();
    }


    @Test
    public void testAdd() throws Exception {
        assertEquals(0, ao.find(Gif.class).length);
        final Gif add = gifService.add(GIF_NAME, GIF_URL);
        assertFalse(add.getID() == 0);

        final Gif[] Gifs = ao.find(Gif.class);
        assertEquals(1, Gifs.length);
        assertEquals(GIF_NAME, Gifs[0].getName());
        assertEquals(GIF_URL, Gifs[0].getUrl());
    }

    @Test
    public void testAll() throws Exception {
        assertTrue(gifService.all().isEmpty());
        final Gif gif = ao.create(Gif.class);

        gif.setName(GIF_NAME);
        gif.setUrl(GIF_URL);
        gif.save();

        final List<Gif> all = gifService.all();
        assertEquals(1, all.size());
        assertEquals(gif.getID(), all.get(0).getID());
    }




    public static class GifServiceImplTestDatabaseUpdater implements DatabaseUpdater {
        @Override
        public void update(EntityManager entityManager) throws Exception {
            entityManager.migrate(Gif.class);

            final Gif gif = entityManager.create(Gif.class);
            gif.setName(GIF_NAME);
            gif.setUrl(GIF_URL);
            gif.save();
        }
    }




}
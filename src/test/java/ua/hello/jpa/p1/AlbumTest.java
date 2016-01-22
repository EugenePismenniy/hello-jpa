package ua.hello.jpa.p1;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.hello.jpa.p1.entity.Album;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author dn100985pev on 22.01.16 15:07.
 */
public class AlbumTest {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void setupJpa() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.hsqldb.music-unit");
		//this.entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.postgres.music-unit");
	}


	@Test
	public void testSaveGetAlbum() {

		Album album = new Album();
		album.setTitle("Best Rock Hits");
		album.setTracks(Arrays.asList("Hello", "GoodBay", "World"));
		Long albumId;

		EntityManager em = entityManagerFactory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(album);
			em.getTransaction().commit();

			albumId = album.getAlbumId();

			assertNotNull(albumId);
			System.out.printf("album Id = '%s'\n", album.getAlbumId());

			List<String> tracks = album.getTracks();
			assertNotNull(tracks);
			assertTrue(tracks.size() == 3);


		} finally {
			em.close();
		}


		em = entityManagerFactory.createEntityManager();
		try {
			album = em.find(Album.class, albumId);
			assertNotNull(album);
			assertEquals("Best Rock Hits", album.getTitle());

			List<String> tracks = album.getTracks();
			assertNotNull(tracks);
			assertTrue(tracks.size() == 3);

			for (String track : tracks) {
				System.out.printf("track = '%s'\n", track);
			}

		} finally {
			em.close();
		}
	}


	@Test
	public void testSaveGetAlbum2() {


		Album album = new Album();
		album.setTitle("The Best of the best!");
		album.setTracks(Arrays.asList("A,B,C,D".split(",")));
		EntityManager em = entityManagerFactory.createEntityManager();
		try {

			em.getTransaction().begin();
			em.persist(album);
			em.getTransaction().commit();

			assertNotNull(album.getAlbumId());


		} finally {
			em.close();
		}

		em = entityManagerFactory.createEntityManager();
		try {

			Album a = em.find(Album.class, album.getAlbumId());

			assertNotNull(a.getTracks());
			assertTrue(a.getTracks().size() == 4);


			em.getTransaction().begin();

			a.setTracks(Collections.singletonList("New"));

			em.getTransaction().commit();

		} finally {
			em.close();
		}


		em = entityManagerFactory.createEntityManager();
		try {

			Album a = em.find(Album.class, album.getAlbumId());

			assertNotNull(a.getTracks());
			assertTrue(a.getTracks().size() == 1);

			assertEquals("New", a.getTracks().get(0));

		} finally {
			em.close();
		}

	}


	@After
	public void shutdown() {
		if (entityManagerFactory != null) {
			entityManagerFactory.close();
		}
	}


}

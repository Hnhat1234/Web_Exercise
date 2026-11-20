package Service;

import jakarta.persistence.EntityManager;

import java.util.List;

import Model.Video;

public class VideosDaoImpl implements VideosDao {

	@Override
	public boolean checkNameExists(String username, String name) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			List<Video> result = em.createQuery(
					"SELECT v FROM Video v WHERE v.username = :username AND v.video_name = :name",
					Video.class)
					.setParameter("username", username)
					.setParameter("name", name)
					.setMaxResults(1)
					.getResultList();
			return !result.isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			em.close();
		}
	}

	@Override
	public boolean uploadVideo(String username, String video_name) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(new Video(username, video_name));
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			return false;
		} finally {
			em.close();
		}
	}

	@Override
	public List<String> getTenVideos(String username) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em.createQuery(
					"SELECT v.video_name FROM Video v WHERE v.username = :username ORDER BY v.uploaded_at DESC",
					String.class)
					.setParameter("username", username)
					.setMaxResults(10)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}

	@Override
	public List<String> getFullVideos(String username) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em.createQuery(
					"SELECT v.video_name FROM Video v WHERE v.username = :username ORDER BY v.uploaded_at DESC",
					String.class)
					.setParameter("username", username)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}
}

package Service;

import jakarta.persistence.EntityManager;

import java.util.List;

import Model.Video;

public class VideosDaoImpl implements VideosDao {

	@Override
	public boolean checkNameExists(String email, String name) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			List<Video> result = em.createQuery(
					"SELECT v FROM Video v WHERE v.email = :email AND v.video_name = :name",
					Video.class)
					.setParameter("email", email)
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
	public boolean uploadVideo(String email, String video_name) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(new Video(email, video_name));
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
	public List<String> getTenVideos(String email) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em.createQuery(
					"SELECT v.video_name FROM Video v WHERE v.email = :email ORDER BY v.uploaded_at DESC",
					String.class)
					.setParameter("email", email)
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
	public List<String> getFullVideos(String email) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em.createQuery(
					"SELECT v.video_name FROM Video v WHERE v.email = :email ORDER BY v.uploaded_at DESC",
					String.class)
					.setParameter("email", email)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}
}

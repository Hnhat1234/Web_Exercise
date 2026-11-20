package Service;

import java.security.NoSuchAlgorithmException;

import Model.OTP;
import Model.UserProfile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class ProfileDaoImpl implements ProfileDao {
	public UserProfile getInfo(String username) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			UserProfile result = em.createQuery("SELECT u FROM UserProfile u WHERE u.username = :username",
					UserProfile.class
		    ).setParameter("username", username)
			 .getSingleResult();
			if (result == null) {
				return null;
			}
			return result;
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			em.close();
		}
		return null;
	}
	public boolean createProfile(String username, String full_name, String phone_number, String email) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			if (em.find(UserProfile.class, username) == null) {
				em.persist(new UserProfile(username, full_name, phone_number, email));
			}
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		} finally {
			em.close();
		}
		return false;
	}
	public boolean updateInfo(String username, String full_name, String phone_number, String email) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			Query query = em.createQuery("UPDATE UserProfile u SET u.full_name = :full_name, u.phone_number = :phone_number, u.email = :email WHERE u.username = :username");
			query.setParameter("full_name", full_name);
			query.setParameter("phone_number", phone_number);
			query.setParameter("email", email);
			query.setParameter("username", username);

			int r = query.executeUpdate();
			if (r == 0) {
				em.getTransaction().rollback();
				return false;
			}

			Query account = em.createQuery("UPDATE Account a SET a.email = :email WHERE a.username = :username");
			account.setParameter("email", email);
			account.setParameter("username", username);
			account.executeUpdate();

			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		} finally {
			em.close();
		}
		return false;
	}
	public boolean updateImage(String username, String image) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			Query query = em.createQuery("UPDATE UserProfile u SET u.image = :image WHERE u.username = :username");
			query.setParameter("image", image);
			query.setParameter("username", username);

			int r = query.executeUpdate();
			if (r == 0) {
				em.getTransaction().rollback();
				return false;
			}
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		} finally {
			em.close();
		}
		return false;
	}
	public String getOTP(String username, String email) {
		int otp = (int) (Math.random() * (9999 - 1000) + 1000);
		String otp_str = Integer.toString(otp);
		String concat = username + email + otp_str;
		String hash = null;
		try {
			hash = Hash.toHexString(Hash.getSHA(concat));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(new OTP(email, hash));
			em.getTransaction().commit();
			return otp_str;
		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			return null;
		} finally {
			em.close();
		}
	}
	public String getHash(String email) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			OTP result = em.find(OTP.class, email);
			return result == null ? null : result.getHash();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}
}

package Service;

import Model.OTP;
import Model.Account;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import jakarta.persistence.EntityManager;

public class RegisterDaoImpl implements RegisterDao {

	@Override
	public boolean exists(String email, String username) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			List<Account> result = em.createQuery(
					"SELECT a FROM Account a WHERE a.email = :email OR a.username = :username",
					Account.class)
					.setParameter("email", email)
					.setParameter("username", username)
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
	public boolean emailExists(String email) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em.find(Account.class, email) != null;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			em.close();
		}
	}

	@Override
	public boolean register(String email, String username, String password) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(new Account(email, username, password));
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
	public String generatedOTP(String email, String password) {
		int otp = (int) (Math.random() * (9999 - 1000) + 1000);
		String otp_str = Integer.toString(otp);
	
		String concat = email + password + otp_str;
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
	
	@Override
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
	
	@Override
	public boolean removeOTP(String email) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			OTP result = em.find(OTP.class, email);
			if (result != null) {
				em.remove(result);
			}
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
	public boolean updatePassword(String email, String password) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			Account account = em.find(Account.class, email);
			if (account == null) {
				em.getTransaction().rollback();
				return false;
			}
			account.setPassword(password);
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
}

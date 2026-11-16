package Service;

import Model.Account;

import jakarta.persistence.EntityManager;

public class RegisterDaoImpl implements RegisterDao {

	@Override
	public boolean exists(String username) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em.find(Account.class, username) != null;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			em.close();
		}
	}

	@Override
	public boolean register(String username, String password) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(new Account(username, password));
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

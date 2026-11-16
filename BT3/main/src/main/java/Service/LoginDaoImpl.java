package Service;

import java.util.List;

import Model.Account;

import jakarta.persistence.EntityManager;

public class LoginDaoImpl implements LoginDao {

	@Override
	public Account login(String username, String password) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			List<Account> result = em.createQuery(
					"SELECT a FROM Account a WHERE a.username = :username",
					Account.class)
					.setParameter("username", username)
					.setMaxResults(1)
					.getResultList();

			if (result.isEmpty()) {
				return null;
			}

			Account account = result.get(0);
			return Hash.checkPassword(password, account.getPassword()) ? account : null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}
}

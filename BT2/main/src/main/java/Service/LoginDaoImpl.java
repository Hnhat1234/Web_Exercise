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
					"SELECT a FROM Account a WHERE a.username = :username AND a.password = :password",
					Account.class)
					.setParameter("username", username)
					.setParameter("password", password)
					.setMaxResults(1)
					.getResultList();
			return result.isEmpty() ? null : result.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}

	public String get() {
		return "Test";
	}

	public static void main(String args[]) {
		LoginDaoImpl c = new LoginDaoImpl();
		Account account = c.login("hnhatn", "123456");
		System.out.printf("%s", account == null ? "null" : account.getUsername());
	}
}

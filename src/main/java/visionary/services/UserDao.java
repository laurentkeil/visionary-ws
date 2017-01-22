package visionary.services;

import visionary.models.User;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

/**
 * Service layer for the users in the spring webservice managing data-access to DB.
 * Hibernate generate methods like a CRUD repository, used in the controller
 */
@Transactional
public interface UserDao extends CrudRepository<User, Long> {

	/**
     * This method will find an User instance in the database by its email.
     * Note that this method is not implemented and its working code will be
     * automatically generated from its signature by Spring Data JPA.
	 * @param email
	 * @return a user
	 */
	public User findByEmail(String email);
}
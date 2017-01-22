package visionary.services;

import visionary.models.Filter;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import java.lang.String;
import java.util.List;

/**
 * Service layer for the filters in the spring webservice managing data-access to DB.
 * Hibernate generate methods like a CRUD repository, used in the controller
 */
@Transactional
public interface FilterDao extends CrudRepository<Filter, Long> {
	
	/**
	 * @param parameter
	 * @return a list of filters assiocated with the parameter
	 */
	public List<Filter> findByParameter(String parameter);
}
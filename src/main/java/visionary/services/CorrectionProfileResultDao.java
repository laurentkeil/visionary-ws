package visionary.services;

import visionary.models.CorrectionProfileResult;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import java.lang.String;
import java.util.List;

/**
 * Service layer for the correction profile results in the spring webservice managing data-access to DB.
 * Hibernate generate methods like a CRUD repository, used in the controller
 */
@Transactional
public interface CorrectionProfileResultDao extends CrudRepository<CorrectionProfileResult, Long> {

	/**
	 * @param type
	 * @return a list of correction profile results assiocated with the type
	 */
	public List<CorrectionProfileResult> findByType(String type);
	
}
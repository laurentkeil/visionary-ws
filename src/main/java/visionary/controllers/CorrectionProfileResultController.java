package visionary.controllers;

import visionary.services.CorrectionProfileResultDao;
import visionary.models.CorrectionProfileResult;
import visionary.models.Filter;

import java.util.List;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller Rest providing an api restful containing rest methods to create, read, update and delete correction profile results of users
 * Documentation of the Api is available thanks to JSONDoc plugin
 */
@Api(name = "correction-profile-results", description = "Methods for managing the correction profiles of the users resulted from correction tool and used by the color correction extension anderton", group = "Users and their profiles", visibility = ApiVisibility.PUBLIC)
@ApiVersion(since = "1.0", until = "1.2.10")
@RestController
@RequestMapping("/correction-profile-results")
public class CorrectionProfileResultController {

	// ----------------------------
	// PRIVATE FIELDS / repository
	// ----------------------------

	@Autowired
	private CorrectionProfileResultDao correctionProfileResultDao;

	// ---------
	// API CRUD
	// ---------

	/**
	 * Creating a filter instance associated with idCorrecProfile.
	 * @param idCorrecProfile : The id of the correction profile result associate with filterToAdd.
	 * @param filterToAdd : The filter in JSON to create associated with idCorrecProfile.
	 * @return A response describing if the filter is successfully created or not.
	 */
	@ApiMethod(
	        description = "Creating a filter instance associated with idCorrecProfile."
	)
	@RequestMapping(value = "/{idCorrecProfile}/filter", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFilter(@ApiPathParam(description = "the id of the correction profile result associate with filterToAdd") @PathVariable(value = "idCorrecProfile") long idCorrecProfile, @RequestBody(required=true) Filter filterToAdd) {
		try {
			CorrectionProfileResult correc = correctionProfileResultDao.findOne(idCorrecProfile);
			if(correc == null) {
		        return ResponseUtils.catchNotFound("correction profile result");
			} else if(filterToAdd != null) {
				List<Filter> oldFilterList = correc.getFilters();
				oldFilterList.add(filterToAdd);
				correc.setFilters(oldFilterList);
				correctionProfileResultDao.save(correc);
				return new ResponseEntity<String>("Filter succesfully created !", HttpStatus.CREATED);
			} else {
				return ResponseUtils.catchBadRequest(new Exception(), "Body of filter to add is null.");
			}
		} catch (Exception ex) {
			return ResponseUtils.catchBadRequest(ex, "Error when creating filter : ");
		}
	}
	
	/**
	 * Retrieve every correction profile results in a JSON format with get method provided by api restful.
	 * @return every correction profile results in JSON
	 */
	@ApiMethod(
	        description = "Return every correction profile results in JSON."
	)
	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	public List<CorrectionProfileResult> getAllCorrectionProfileResult() {
		return (List<CorrectionProfileResult>) this.correctionProfileResultDao.findAll();
	}

	/**
	 * @param idCorrecProfile : the id of the correction profile result.
	 * @return one specific correction profile result in JSON if idCorrecProfile exist, an error 404 Not Found else.
	 */
	@ApiMethod(
	        description = "Retrieve informations about a correction profile result with his id."
	)
	@RequestMapping(value="/{idCorrecProfile}", method=RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<?> getById(@ApiPathParam(description = "The id of the correction profile result") @PathVariable(value = "idCorrecProfile") long idCorrecProfile) {
		try {
			CorrectionProfileResult correc = correctionProfileResultDao.findOne(idCorrecProfile);
			if(correc == null) {
		        return ResponseUtils.catchNotFound("correction profile result");
			} else {
				return new ResponseEntity<CorrectionProfileResult>(correc, HttpStatus.OK);
			}
		} catch (Exception ex) {
			return ResponseUtils.catchBadRequest(ex, "Error to retrieve the correction profile result : ");
		}
	}

	/**
	 * @param type : the type of the correction profile results wanted.
	 * @return some specific correction profile results in JSON.
	 */
	@ApiMethod(
	        description = "Retrieve informations about some correction profile results with their type."
	)
	@RequestMapping(value="/by-type/{type}", method=RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<?> getByType(@ApiPathParam(description = "The type of the correction profile results wanted") @PathVariable(value = "type") String type) {
		try {
			List<CorrectionProfileResult> correcs = correctionProfileResultDao.findByType(type);
			return new ResponseEntity<List<CorrectionProfileResult>>(correcs, HttpStatus.OK);
		} catch (Exception ex) {
			return ResponseUtils.catchBadRequest(ex, "Error to retrieve some correction profile results : ");
		}
	}
	

	/**
	 * @param idCorrecProfile : the id of the correction profile result.
	 * @return the correction profile result's filters in JSON.
	 */
	@ApiMethod(
	        description = "Retrieve informations about correction profile result's filters with his id."
	)
	@RequestMapping(value="/{idCorrecProfile}/filters", method=RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<?> getFilters(@ApiPathParam(description = "the id of the correction profile result to display filters assiocated") @PathVariable(value = "idCorrecProfile") long idCorrecProfile) {
		try {
			CorrectionProfileResult correc = correctionProfileResultDao.findOne(idCorrecProfile);
			if(correc == null) {
		        return ResponseUtils.catchNotFound("correction profile result");
			} else {
				return new ResponseEntity<List<Filter>>(correc.getFilters(), HttpStatus.OK);
			}
		} catch (Exception ex) {
			return ResponseUtils.catchBadRequest(ex, "Error to retrieve the correction profile result's filters : ");
		}
	}


	/**
	 * Update the correction profile result having the passed id.
	 * @param idCorrecProfile : The id of the correction profile result to update.
	 * @param correcToPut : the correction profile result with his fields modified.
	 * @return A response describing if the correction profile result is successfully updated or not.
	 */
	@ApiMethod(
	        description = "Update the correction profile result having the passed id."
	)
	@RequestMapping(value = "/{idCorrecProfile}", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> update(@ApiPathParam(description = "the id of the correction profile result to update") @PathVariable(value = "idCorrecProfile") long idCorrecProfile, @RequestBody(required=true) CorrectionProfileResult correcToPut) {
		try {
			CorrectionProfileResult correc = correctionProfileResultDao.findOne(idCorrecProfile);
			if(correc == null) {
		        return ResponseUtils.catchNotFound("correction profile result");
			} else {
				/* Put the field only if it has passed in the body of correcToPut */
				if(correcToPut.getType() != null) {
					correc.setType(correcToPut.getType());
				}
				if(correcToPut.getFilters() != null) {
					//Delete TODO
					correc.setFilters(correcToPut.getFilters());
				}
				correctionProfileResultDao.save(correc);
				return new ResponseEntity<String>("Correction profile result successfully updated !", HttpStatus.CREATED);
			}
		}
		catch (Exception ex) {
			return ResponseUtils.catchBadRequest(ex, "Error updating the user : ");
		}
	}

	/**
	 * Delete the correction profile result having the passed id.
	 * @param idCorrecProfile : The id of the correction profile result to delete.
	 * @return A response describing if the correction profile result is successfully deleted or not.
	 */
	@ApiMethod(
	        description = "Delete the correction profile result having the passed id."
	)
	@RequestMapping(value = "/{idCorrecProfile}", method = RequestMethod.DELETE, headers = "Accept=application/json") 
	public ResponseEntity<String> delete(@ApiPathParam(description = "The id of the correction profile result to delete.") @PathVariable(value = "idCorrecProfile") long idCorrecProfile) {
		try {
			CorrectionProfileResult correc = correctionProfileResultDao.findOne(idCorrecProfile);
			if(correc == null) {
				return ResponseUtils.catchNotFound("Correction Profile Result");
			} else {
				correctionProfileResultDao.delete(correc);
				return new ResponseEntity<String>("Correction Profile Result succesfully deleted !", HttpStatus.OK);
			}
		} catch (Exception ex) {
			return ResponseUtils.catchBadRequest(ex, "Error to delete the Correction Profile Result : ");
		}
	}

}
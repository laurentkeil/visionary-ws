package visionary.controllers;

import visionary.services.FilterDao;
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
 * Controller Rest providing an api restful containing rest methods to create, read, update and delete filters' correction profile results of users
 * Documentation of the Api is available thanks to JSONDoc plugin
 */
@Api(name = "filters", description = "Methods for managing the users' filters used in the correction profile associated", group = "Users and their profiles", visibility = ApiVisibility.PUBLIC)
@ApiVersion(since = "1.0", until = "1.2.10")
@RestController
@RequestMapping("/filters")
public class FilterController {

	// ------------------------
	// PRIVATE FIELDS / repo
	// ------------------------

	@Autowired
	private FilterDao filterDao;

	// ------------------------
	// API CRUD
	// ------------------------

	/**
	 * Retrieve every filters in a JSON format with get method provided by api restful.
	 * @return the fitlers in JSON
	 */
	@ApiMethod(
	        description = "Return every fitlers in JSON."
	)
	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Filter> getAllFilter() {
		return (List<Filter>) this.filterDao.findAll();
	}


	/**
	 * @param idFilter : the id of the filter.
	 * @return one specific filter in JSON if idFilter exist, an error 404 Not Found else.
	 */
	@ApiMethod(
	        description = "Retrieve informations about an user with his id."
	)
	@RequestMapping(value="/{idFilter}", method=RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<?> getById(@ApiPathParam(description = "The id of the filter") @PathVariable(value = "idFilter") long idFilter) {
		try {
			Filter filter = filterDao.findOne(idFilter);
			if(filter == null) {
		        return ResponseUtils.catchNotFound("filter");
			} else {
				return new ResponseEntity<Filter>(filter, HttpStatus.OK);
			}
		} catch (Exception ex) {
			return ResponseUtils.catchBadRequest(ex, "Error to retrieve the filter : ");
		}
	}

	/**
	 * @param parameter : the parameter of the filters wanted.
	 * @return some specific filters in JSON.
	 */
	@ApiMethod(
	        description = "Retrieve informations about some filters with their parameter."
	)
	@RequestMapping(value="/by-parameter/{parameter}", method=RequestMethod.GET, headers = "Accept=application/json")
	public  ResponseEntity<?>  getByParameter(@ApiPathParam(description = "the parameter of the filters wanted.") @PathVariable(value = "parameter") String parameter) {
		try {
			List<Filter> filters = filterDao.findByParameter(parameter);
			return new ResponseEntity<List<Filter>>(filters, HttpStatus.OK);
		} catch (Exception ex) {
			return ResponseUtils.catchBadRequest(ex, "Error to retrieve some filters : ");
		}
	}

	/**
	 * Update the filter having the passed id.
	 * @param idFilter : The id of the filter to update.
	 * @param filterToPut : the filter with his fields modified.
	 * @return A response describing if the filter is successfully updated or not.
	 */
	@ApiMethod(
	        description = "Update the fitler having the passed id."
	)
	@RequestMapping(value = "/{idFilter}", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> update(@ApiPathParam(description = "The id of the filter to update.") @PathVariable(value = "idFilter") long idFilter, @RequestBody Filter filterToPut) {
		try {
			Filter filter = filterDao.findOne(idFilter);
			if(filter == null) {
		        return ResponseUtils.catchNotFound("filter");
			} else {
				if(filterToPut.getParameter() != null) {
					filter.setParameter(filterToPut.getParameter());
				}
				if(filterToPut.getValue() != null) {
					filter.setValue(filterToPut.getValue()); 
				}
				filterDao.save(filter);
				return new ResponseEntity<String>("Filter succesfully updated !", HttpStatus.CREATED);
			}
		} catch (Exception ex) {
			return ResponseUtils.catchBadRequest(ex, "Error updating the filter : ");
		}
	}


	/**
	 * Delete the filter having the passed id.
	 * @param idFilter : The id of the filter to delete
	 * @return A response describing if the filter is successfully deleted or not.
	 */
	@ApiMethod(
	        description = "Delete the filter having the passed id."
	)
	@RequestMapping(value = "/{idFilter}", method = RequestMethod.DELETE, headers = "Accept=application/json") 
	public ResponseEntity<String> delete(@ApiPathParam(description = "The id of the filter to delete.") @PathVariable(value = "idFilter") long idFilter) {
		try {
			Filter filter = filterDao.findOne(idFilter);
			if(filter == null) {
				return ResponseUtils.catchNotFound("filter");
			} else {
				filterDao.delete(filter);
				return new ResponseEntity<String>("Filter succesfully deleted !", HttpStatus.OK);
			}
		} catch (Exception ex) {
			return ResponseUtils.catchBadRequest(ex, "Error to deleter filter : ");
		}
	}

}
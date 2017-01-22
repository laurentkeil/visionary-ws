package visionary.controllers;

import visionary.services.CorrectionProfileResultDao;
import visionary.services.UserDao;
import visionary.models.CorrectionProfileResult;
import visionary.models.User;

import java.util.List;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.pojo.ApiVisibility;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.annotation.ApiVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller Rest providing an api restful containing rest methods to create, read, update and delete users
 * Documentation of the Api is available thanks to JSONDoc plugin
 */
@Api(name = "user", description = "Methods for managing users", group = "Users and their profiles", visibility = ApiVisibility.PUBLIC)
@ApiVersion(since = "1.0", until = "1.2.10")
@RestController
@RequestMapping("/users")
public class UserController {

	// ----------------------------
	// PRIVATE FIELDS / repository
	// ----------------------------

	@Autowired
	private UserDao userDao;
	@Autowired
	private CorrectionProfileResultDao correcDao;
	
	// ---------
	// API CRUD
	// ---------

	/**
	 * Creating a user instance with post method provided by the restful api.
	 * @param user : the user to create in a JSON format.
	 * @return A response describing if the user is successfully created or not.
	 */
	@ApiMethod(
	        description = "Creating a user instance provided in JSON."
	)
	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> create(@RequestBody(required=true) User user) {
		try {
			userDao.save(user);
			return new ResponseEntity<String>("User successfully created !", HttpStatus.CREATED);
		} catch (Exception ex) {
			return ResponseUtils.catchBadRequest(ex, "Error when creating the user : ");
		}
	}

	/**
	 * Creating a correction profile result instance associated with idUser.
	 * @param idUser : The id of the user associate with correcToAdd.
	 * @param correcToAdd : The correction profile result in JSON to create, associated with idUser.
	 * @return A response describing if the correction profile result is successfully created or not.
	 */
	@ApiMethod(
	        description = "Creating a correction profile results instance provided in JSON associated with idUser."
	)
	@RequestMapping(value = "/{idUser}/correction-profile-result", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createCorrectionProfileResult(@ApiPathParam(description = "the id of the user associate with correcToAdd.") @PathVariable(value = "idUser") long idUser, @RequestBody(required=true) CorrectionProfileResult correcToAdd) {
		try {
			User user = userDao.findOne(idUser);
			if(user == null) {
		        return ResponseUtils.catchNotFound("user");
			} else if(correcToAdd != null) {
				List<CorrectionProfileResult> oldCorrecList = user.getCorrectionProfileResults();
				oldCorrecList.add(correcToAdd);
				user.setCorrectionProfileResults(oldCorrecList);
				userDao.save(user);
				return new ResponseEntity<String>("Correction profile result successfully created !", HttpStatus.CREATED);
			} else {
				return ResponseUtils.catchBadRequest(new Exception(), "Body of correction profile result to add is null.");
			}
		}
		catch (Exception ex) {
			return ResponseUtils.catchBadRequest(ex, "Error when creating the correction profile result : ");
		}
	}

	/**
	 * Retrieve every users in a JSON format with get method provided by api restful.
	 * @return the users in JSON
	 */
	@ApiMethod(
	        description = "Return every users in JSON."
	)
	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	public List<User> getAllUsers() {
		return (List<User>) this.userDao.findAll();
	}
	
	/**
	 * @param idUser : the id of the user.
	 * @return one specific user in JSON if idUser exist, an error 404 Not Found else.
	 */
	@ApiMethod(
	        description = "Retrieve informations about an user with his id."
	)
	@RequestMapping(value="/{idUser}", method=RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<?> getById(@ApiPathParam(description = "The id of the user") @PathVariable(value = "idUser") long idUser) {
		try {
			User user = userDao.findOne(idUser);
			if(user == null) {
		        return ResponseUtils.catchNotFound("user");
			} else {
				return new ResponseEntity<User>(user, HttpStatus.OK);
			}
		} catch (Exception ex) {
			return ResponseUtils.catchBadRequest(ex, "Error to retrieve the user : ");
		}
	}
	
	/**
	 * @param email : The email to find one specific user (. have to be transform to -dot-).
	 * @return one specific user in JSON if email exist, or an error 404 Not Found else.
	 */
	@ApiMethod(
		        description = "Retrieve informations about an user with his email."
	)
	@RequestMapping(value="/by-email/{email}", method=RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<?> getByEmail(@ApiPathParam(description = "The email of the user (. have to be transform to -dot-)") @PathVariable(value = "email") String email) {
		try {
			email = email.replace("-dot-", ".");
			User user = userDao.findByEmail(email);
			if(user == null) {
		        return ResponseUtils.catchNotFound("user");
			} else {
				return new ResponseEntity<User>(user, HttpStatus.OK);
			}
		} catch (Exception ex) {
			return ResponseUtils.catchBadRequest(ex, "Error to retrieve the user : ");
		}
	}
	
	/**
	 * @param idUser : the id of the user
	 * @return the user's correction profile results, or an error 404 Not Found else.
	 */
	@ApiMethod(
	        description = "Retrieve informations about every user's correction profile results with his id_user."
	)
	@RequestMapping(value="/{idUser}/correction-profile-results", method=RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<?> getCorrecProfiles(@ApiPathParam(description = "the id of the user") @PathVariable(value = "idUser") long idUser) {
		try {
	    	User user = userDao.findOne(idUser);
			if(user == null) {
		        return ResponseUtils.catchNotFound("user");
			} else {
				return new ResponseEntity<List<CorrectionProfileResult>>(user.getCorrectionProfileResults(), HttpStatus.OK);
			}
		} catch (Exception ex) {
			return ResponseUtils.catchBadRequest(ex, "Error to retrieve the user's correction profile results : ");
		}
	}

	/**
	 * Update the user having the passed id.
	 * @param idUser : The id of the user to update.
	 * @param userToPut : the user with his fields modified.
	 * @return A response describing if the user is successfully updated or not.
	 */
	@ApiMethod(
	        description = "Update the user having the passed id."
	)
	@RequestMapping(value = "/{idUser}", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> update(@ApiPathParam(description = "the id of the user") @PathVariable(value = "idUser") long idUser, @RequestBody(required=true) User userToPut) {
		try {
			User user = userDao.findOne(idUser);
			if(user == null) {
		        return ResponseUtils.catchNotFound("user");
			} else {
				/* Put the field only if it has passed in the body of userToPut */
				if(userToPut.getEmail() != null) {
					user.setEmail(userToPut.getEmail());
				}
				if(userToPut.getName() != null) {
					user.setName(userToPut.getName());
				}
				if(userToPut.getFirstname() != null) {
					user.setFirstname(userToPut.getFirstname());
				}
				if(userToPut.getAge() != null) {
					user.setAge(userToPut.getAge());
				}
				if(userToPut.getSex() != null) {
					user.setSex(userToPut.getSex());
				}
				if(userToPut.getCorrectionProfileResults() != null) {
					//Delete TODO
					long idCorrecToDelete = user.getCorrectionProfileResults().get(0).getIdCorrectionProfileResult();
					CorrectionProfileResult correcToDelete = correcDao.findOne(idCorrecToDelete);
					correcDao.delete(correcToDelete);
					user.setCorrectionProfileResults(userToPut.getCorrectionProfileResults());
				}
				userDao.save(user);
				return new ResponseEntity<String>("User successfully updated !", HttpStatus.CREATED);
			}
		} catch (Exception ex) {
			return ResponseUtils.catchBadRequest(ex, "Error updating the user : ");
		}
	}

	/**
	 * Delete the user having the passed id.
	 * @param idUser : The id of the user to delete
	 * @return A response describing if the user is successfully deleted or not.
	 */
	@ApiMethod(
		        description = "Delete the user having the passed id."
	)
	@RequestMapping(value = "/{idUser}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> delete(@ApiPathParam(description = "the id of the user") @PathVariable(value = "idUser") long idUser) {
		try {
			User user = userDao.findOne(idUser);
			if(user == null) {
				return ResponseUtils.catchNotFound("user");
			} else {
				userDao.delete(user);
				return new ResponseEntity<String>("User successfully deleted !", HttpStatus.OK);
			}
		} catch (Exception ex) {
			return ResponseUtils.catchBadRequest(ex, "Error to deleter user : ");
		}
	}

}
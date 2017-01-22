package visionary.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject
@Entity
@Table(name = "correctionProfileResult")
public class CorrectionProfileResult {

    @ApiObjectField(description = "An autogenerated id (unique for each user in the db)", required=true)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idCorrectionProfileResult;
	
    @ApiObjectField(description = "The type of the correction, \"undefined\" if no filters attached", required=true)
	@NotNull
	private String type;

    @ApiObjectField(description = "The correction profile result's filters linked by id_correction_profile_result", required=false)
    @OneToMany(fetch = FetchType.EAGER, /*mappedBy = "correctionProfileResult",*/ cascade = {CascadeType.ALL})
	@JoinColumn(name="idCorrectionProfileResult")
	private List<Filter> filters;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser", insertable=false, updatable=false, nullable=false)
    //private User user;*/

	public CorrectionProfileResult() {
	}

	public CorrectionProfileResult(long idCorrectionProfileResult) {
		this.idCorrectionProfileResult = idCorrectionProfileResult;
	}
	
	/**
	 * constructor of CorrectionProfileResult from JSON data to mysql entity
	 * @param type
	 * @param filters
	 */
	public CorrectionProfileResult(String type, List<Filter> filters, User user) {
		this.type = type;
		this.filters = filters;
		//this.user = user;
	}
	
    /**
     * @return the idCorrectionProfileResult of the user's correction profile result in mysql db
	 */
    public long getIdCorrectionProfileResult() {
		return idCorrectionProfileResult;
	}
    
    /**
     * @return type
     */
    public String getType() {
		return type;
	}
    
    /**
     * @param type the type of the correction to set
     */
    public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the filters
	 */
	public List<Filter> getFilters() {
		return filters;
	}

	/**
	 * @param filters the filters to set
	 */
	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}
	
	/*public void setUser(User user) {
		this.user = user;
	}*/
}

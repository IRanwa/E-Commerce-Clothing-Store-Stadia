package ModelClasses;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Getter;
import lombok.Setter;

@Entity
public class MainSubCategory implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@Getter @Setter MainSubCategoryIdentity MainSubCategoryId;
	 
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="mainId",referencedColumnName="id")
	@MapsId("main_id")
	@Getter @Setter MainCategory mainCategory;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="subId",referencedColumnName="id")
	@MapsId("sub_id")
	@Getter @Setter SubCategory subCategory;
}

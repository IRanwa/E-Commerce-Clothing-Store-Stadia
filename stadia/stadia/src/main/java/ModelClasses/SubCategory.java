package ModelClasses;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
public class SubCategory implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy="subCategory",cascade=CascadeType.ALL)
	@Getter @Setter List<MainSubCategory> mainSubCategory;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter int id;
	
	@Getter @Setter String subCatTitle;
	
	@Getter @Setter String subCatDesc;
	
	@Getter @Setter String subCatImg;
}

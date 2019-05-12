package DTOClasses;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import ModelClasses.MainCategory;
import ModelClasses.SubCategory;
import lombok.Getter;
import lombok.Setter;

public class MainSubCategoryDTO {
	
	@Getter @Setter long id;
	
	@Getter @Setter MainCategory mainCat;
	
	@Getter @Setter SubCategory subCat;
	
	@Getter @Setter int mainCatId;
	
	@Getter @Setter int subCatId;
}

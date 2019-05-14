package DTOClasses;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;

import DTOClasses.MainSubCategoryDTO;
import ModelClasses.MainCategory;
import ModelClasses.SubCategory;
import lombok.Getter;
import lombok.Setter;

@Entity
public class CategoryDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Getter @Setter List<MainSubCategoryDTO> mainSubCategory;
	
	@Getter @Setter MainCategory mainCategory;
	
	@Getter @Setter SubCategory subcategory;
}

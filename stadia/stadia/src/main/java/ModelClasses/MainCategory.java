package ModelClasses;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import DTOClasses.MainSubCategoryDTO;
import EnumClasses.Gender;
import lombok.Getter;
import lombok.Setter;

@Entity
public class MainCategory implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy="mainCategory",cascade=CascadeType.ALL)
	@Getter @Setter List<MainSubCategory> mainSubCategory;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter int id;
	
	@Getter @Setter String mainCatTitle;
	
	@Getter @Setter String mainCatDesc;
	
	@Getter @Setter String mainCatImg;
	
	@Enumerated(EnumType.STRING)
	@Getter @Setter Gender type;
}

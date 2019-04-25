package ModelClasses;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Product implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy="product")
	@Getter @Setter List<ProductSizes> productSizes;
	
	
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumns({
		@JoinColumn(name="mainCatId",referencedColumnName="mainId"),
		@JoinColumn(name="subCatId",referencedColumnName="subId")
	})
	@Getter @Setter MainSubCategory mainSubCategory;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter long id;
	
	@Getter @Setter String title;
	
	@Getter @Setter String description;
	
	@Getter @Setter double price;
	
	@Getter @Setter Date createdDate;
	
	@Getter @Setter Date modifyDate;
}

package ModelClasses;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Sizes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy="sizes")
	@Getter @Setter List<ProductSizes> productSizes;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter long id;
	
	@Getter @Setter String size;
	
	@Getter @Setter String description;
}

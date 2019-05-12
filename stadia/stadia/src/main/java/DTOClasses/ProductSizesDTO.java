package DTOClasses;

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
public class ProductSizesDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Getter @Setter ProductDTO product;
	
	@Getter @Setter SizesDTO sizes;
	
	@Getter @Setter long prodId;
	
	@Getter @Setter long sizeId;
	
	@Getter @Setter int quantity;
}


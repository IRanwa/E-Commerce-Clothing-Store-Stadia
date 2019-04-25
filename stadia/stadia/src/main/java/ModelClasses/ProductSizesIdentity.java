package ModelClasses;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class ProductSizesIdentity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Getter @Setter long prodId;
	
	@Getter @Setter long sizeId;
}

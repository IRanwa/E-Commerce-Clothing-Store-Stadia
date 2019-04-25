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
public class ProductSizes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="prodId",referencedColumnName="id")
	@MapsId("prod_id")
	@Getter @Setter Product product;
	
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="sizeId",referencedColumnName="id")
	@MapsId("size_id")
	@Getter @Setter Sizes sizes;
	
	@EmbeddedId
	@Getter @Setter ProductSizesIdentity ProductSizesId;
	
	@Getter @Setter int quantity;
}

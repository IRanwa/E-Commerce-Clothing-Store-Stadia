package ModelClasses;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Getter;
import lombok.Setter;

@Entity
public class ProductSizes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter long id;
	
	@ManyToOne
	@JoinColumn(name="prodId",referencedColumnName="id")
	@Getter @Setter Product product;
	
	@ManyToOne
	@JoinColumn(name="sizeId",referencedColumnName="id")
	@Getter @Setter Sizes sizes;
	
	@Getter @Setter int quantity;
}

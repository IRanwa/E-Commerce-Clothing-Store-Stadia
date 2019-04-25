package ModelClasses;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class OrderProducts implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="orderId",referencedColumnName="id")
	@MapsId("order_id")
	@Getter @Setter Orders orders;
	
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="prodId",referencedColumnName="id")
	@MapsId("prod_id")
	@Getter @Setter Product product;
	
	@OneToOne(mappedBy="orderProducts")
	@Getter @Setter Rating rating;
	
	@EmbeddedId
	@Getter @Setter OrderProductsIdentity OrderProductsId;
	
	@Getter @Setter int quantity;
	
}

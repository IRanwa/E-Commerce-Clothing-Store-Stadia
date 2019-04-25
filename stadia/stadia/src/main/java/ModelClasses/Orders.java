package ModelClasses;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import EnumClasses.OrderStatus;
import EnumClasses.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Orders implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="email",referencedColumnName="email") 
	@Getter @Setter User user;
	
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name="shippingId",referencedColumnName="id")
	@Getter @Setter Address shippingAddress;
	
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name="billingId",referencedColumnName="id")
	@Getter @Setter Address billingAddress;
	
	@OneToMany(mappedBy="orders")
	@Getter @Setter List<OrderProducts> orderProducts;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter long id;
	
	@Enumerated(EnumType.STRING)
	@Getter @Setter private OrderStatus status;
	
	@Getter @Setter Date purchasedDate;
	
	@Getter @Setter Date deliverDate;
	
	@Getter @Setter Date orderCompleteDate;
	
	@Enumerated(EnumType.STRING)
	@Getter @Setter PaymentMethod paymentMethod;

}

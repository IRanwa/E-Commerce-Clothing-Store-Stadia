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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import EnumClasses.AddressType;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Address implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy="shippingAddress")
	@Getter @Setter List<Orders> ordersShipping;
	
	@OneToMany(mappedBy="billingAddress")
	@Getter @Setter List<Orders> ordersBilling;
	
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="email",referencedColumnName="email")
	@Getter @Setter User user;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter long id;
	
	@Getter @Setter String fName;
	
	@Getter @Setter String lName;
	
	@Getter @Setter String contactNo;
	
	@Getter @Setter String address;
	
	@Getter @Setter String city;
	
	@Getter @Setter String province;
	
	@Getter @Setter String zipCode;
	
	@Getter @Setter String country;
	
	@Enumerated(EnumType.STRING)
	@Getter @Setter AddressType addType;

}

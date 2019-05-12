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
public class MainSubCategory implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter Long id;
	 
	@ManyToOne
	@JoinColumn(name="mainId",referencedColumnName="id")
	@Getter @Setter MainCategory mainCategory;
	
	@ManyToOne
	@JoinColumn(name="subId",referencedColumnName="id")
	@Getter @Setter SubCategory subCategory;
}

package ModelClasses;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class MainSubCategoryIdentity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Getter @Setter int mainId;
	
	@Getter @Setter int subId;
}

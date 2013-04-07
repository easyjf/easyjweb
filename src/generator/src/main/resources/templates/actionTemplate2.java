package $!{packageName}.mvc;
import com.easyjf.container.annonation.Inject;
import com.easyjf.core.dao.GenericDAO;
import com.easyjf.web.tools.CrudAction;
import $!{packageName}.domain.$!{domainName};

##set ($domain = $!domainName.toLowerCase())
public class $!{domainName}Action extends CrudAction {
	@Inject(name="$!{domainName.toLowerCase()}Dao")
	private GenericDAO<$!{domainName}> dao;
	
	public void setDao(GenericDAO<$!{domainName}> dao) {
		this.dao = dao;
	}

	protected Class entityClass() {		
		return ${domainName}.class;
	}
	public GenericDAO getDao() {	
		return dao;
	}	
	
}
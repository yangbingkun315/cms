package net.zn.ddxj.config.shiro.freemarker;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

import java.io.IOException;
import java.util.Map;

import net.zn.ddxj.entity.CmsUser;

/**
 * <p>Equivalent to {@link org.apache.shiro.web.tags.PermissionTag}</p>
 */
public abstract class PermissionTag extends SecureTag {
    String getName(Map params) {
        return getParam(params, "name");
    }
    
    @Override
    protected void verifyParameters(Map params) throws TemplateModelException {
        String permission = getName(params);

        if (permission == null || permission.length() == 0) {
            throw new TemplateModelException("The 'name' tag attribute must be set.");
        }
    }

    @Override
    public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
        String p = getName(params);

        boolean show = showTagBody(p);
        if (show) {
            renderBody(env, body);
        }
    }

    protected boolean isPermitted(String p) {
    	CmsUser user = (CmsUser)getSubject().getPrincipal();
    	if(user != null && user.getUserName().equals("admin")){//超级管理员具备所有权限
			return true;
		}else{
			return getSubject() != null && getSubject().isPermitted(p);
		}
    }

    protected abstract boolean showTagBody(String p);
}

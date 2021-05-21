package com.fr.exam.config;

import com.fr.exam.entity.DO.BizSignList;
import com.fr.exam.service.IBizSignListService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author furao
 * @desc
 * @date 2021/2/19
 * @package com.fr.exam.config
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    IBizSignListService bizSignListService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String idCard = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        List<BizSignList> list = bizSignListService.getBizSignListByIdCard(idCard);
        if(list!=null && !StringUtils.isEmpty(list.get(0).getIdCard())){
            simpleAuthorizationInfo.addRole("考生");
            simpleAuthorizationInfo.addStringPermission("/examPage/**");
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (StringUtils.isEmpty(authenticationToken.getPrincipal())) {
            return null;
        }
        String idCard = authenticationToken.getPrincipal().toString();
        List<BizSignList> list = bizSignListService.getBizSignListByIdCard(idCard);
        if(list!=null &&list.size()!=0 && !StringUtils.isEmpty(list.get(0).getIdCard())) {
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(idCard, list.get(0).getPassword(), getName());
            return simpleAuthenticationInfo;
        }else{
            return null;
        }
    }
}

package com.syfri.userservice.service.impl.system;

import com.syfri.userservice.model.system.AccountRoleVO;
import com.syfri.userservice.model.system.RoleVO;
import com.syfri.userservice.utils.Constants;
import com.syfri.userservice.utils.CurrentUserUtil;
import com.syfri.userservice.utils.JwtUtil;
//import org.apache.shiro.crypto.RandomNumberGenerator;
//import org.apache.shiro.crypto.SecureRandomNumberGenerator;
//import org.apache.shiro.crypto.hash.SimpleHash;
//import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syfri.baseapi.service.impl.BaseServiceImpl;
import com.syfri.userservice.dao.system.AccountDAO;
import com.syfri.userservice.model.system.AccountVO;
import com.syfri.userservice.service.system.AccountService;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service("accountService")
public class AccountServiceImpl extends BaseServiceImpl<AccountVO> implements AccountService {

	//指定加密算法为MD5
	private String algorithmName = "MD5";

	//指定加密迭代次数
	private int hashIterations = 1;

	@Autowired
	private AccountDAO accountDAO;

	@Override
	public AccountDAO getBaseDAO() {
		return accountDAO;
	}

	/*采用MD5对密码进行加密.*/
	@Override
	public AccountVO getPasswordEncry(AccountVO accountVO) {
		/*
		RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
		accountVO.setSalt(randomNumberGenerator.nextBytes().toHex());
		String newPassword = new SimpleHash(algorithmName, accountVO.getPassword(), ByteSource.Util.bytes(accountVO.getSalt()),hashIterations).toHex();
		accountVO.setPassword(newPassword);
		*/
		return accountVO;
	}

	/*新增：向账户表中增加账户.*/
	@Override
	public int doInsertAccountByVO(AccountVO accountVO){
		accountVO.setCreateName(CurrentUserUtil.getCurrentUserName());
		accountVO.setCreateId(CurrentUserUtil.getCurrentUserId());
		accountVO = this.getPasswordEncry(accountVO);
		//accountVO = ((AccountService) AopContext.currentProxy()).getPasswordEncry(accountVO);
		return accountDAO.doInsertByVO(accountVO);
	}

	/*修改：修改账户表*/
	@Override
	public int doUpdateAccountByVO(AccountVO accountVO){
		if(StringUtils.isEmpty(accountVO.getPassword())){
			accountVO.setPassword(null);
		}else{
			accountVO.setPassword(JwtUtil.md5(accountVO.getPassword() + "-" + Constants.PWD_KEY));
//			accountVO = this.getPasswordEncry(accountVO);
		}
		return accountDAO.doUpdateByVO(accountVO);
	}

	/*向用户角色中间表批量插入数据.*/
	@Override
	public int doInsertAccountRolesBatch(String userid, List<RoleVO> roles){
		List<AccountRoleVO> accountRoles = new ArrayList<>();
		if(roles!=null && roles.size()>0){
			for(RoleVO role : roles){
				AccountRoleVO temp = new AccountRoleVO();
				temp.setUserid(userid);
				temp.setRoleid(role.getRoleid());
				temp.setCreateId(CurrentUserUtil.getCurrentUserId());
				temp.setCreateName(CurrentUserUtil.getCurrentUserName());
				accountRoles.add(temp);
			}
			return accountDAO.doBatchInsertAccountRoles(accountRoles);
		}else{
			//如果新增用户没有选择角色，则默认给用户一个初始角色
			AccountRoleVO accountRoleVO = new AccountRoleVO();
			accountRoleVO.setUserid(userid);
			accountRoleVO.setRoleid("753803FC34FC4424AB6778B0F3132AAF");
			accountRoleVO.setCreateId(CurrentUserUtil.getCurrentUserId());
			accountRoleVO.setCreateName(CurrentUserUtil.getCurrentUserName());
			return accountDAO.doInsertAccoutRoleInitial(accountRoleVO);
		}
	}

	/*--删除：删除账户同时删除其角色(中间表).--*/
	public int doDeleteAccountRoles(String userid){
		return accountDAO.doDeleteAccountRoles(userid);
	}

	/*--根据不同deptid查询用户账户 by li.xue 2018/10/17 10:11.--*/
	public List<AccountVO> doSearchListByVO2(AccountVO accountVO){
		return accountDAO.doSearchListByVO2(accountVO);
	}
}
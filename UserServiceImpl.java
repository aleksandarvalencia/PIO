package com.projects.pio.service;

import com.projects.pio.model.ComboValues;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.projects.pio.model.User;
import com.projects.pio.model.LoginUser;
import com.projects.pio.model.JsTreeNode;

@Service("userService")
public class UserServiceImpl implements UserService {

	UserDao userDao;

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User findById(Integer id) {
		return userDao.findById(id);
	}
        
        @Override
	public String DisplayNameByUserName(String username,String password) {
		return userDao.DisplayNameByUserName(username,password);
	}
        
         @Override
	public Integer DisplayLogStatusByUserName(String username,String password) {
		return userDao.DisplayLogStatusByUserName(username,password);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}
        
        @Override
	public List<JsTreeNode> getTreeView(Integer idAuth) {
		return userDao.getTreeView(idAuth);
	}

        @Override
        public List<User> executeProcUser(String p_akcija, Integer p_idUser, String p_firstname,String p_secondname,
                String p_username,String p_password,Integer p_idAuth, Integer p_status,Integer p_logged)
        {
            return userDao.executeProcUser(p_akcija, p_idUser, p_firstname, p_secondname, p_username, p_password, p_idAuth, p_status,p_logged);
        }
        
        @Override
        public List<ComboValues>  getAuthList()
        {
            return userDao.getAuthList();
        }
	
        
        @Override
	public LoginUser UserLogin(String username, String password) {
		return userDao.UserLogin(username,password);
	}
        
        @Override
        @UserDaoImpl.Property(key="property.key", defaultValue="default")
        public Integer getidAuth() 
        {
            return userDao.getidAuth();
        }
        
        
        @Override
        @UserDaoImpl.Property(key="property.key", defaultValue="default")
        public Integer getidUser() 
        {
            return userDao.getidUser();
        }
        
        @Override
        @UserDaoImpl.Property(key="property.key", defaultValue="default")
        public Integer getlogStatus() 
        {
            return userDao.getlogStatus();
        }
        @Override
        @UserDaoImpl.Property(key="property.key", defaultValue="default")
        public void setlogStatus(Integer lg) 
        {
           userDao.setlogStatus(lg);
        }

}
package com.projects.pio.service;

import com.projects.pio.model.ComboValues;
import java.util.List;

import com.projects.pio.model.User;
import com.projects.pio.model.JsTreeNode;
import com.projects.pio.model.LoginUser;

public interface UserDao {

	User findById(Integer id);
        
        List<JsTreeNode> getTreeView(Integer idAuth);
        
        String DisplayNameByUserName(String username,String password);
        
        LoginUser UserLogin(String username, String password);

	List<User> findAll();
        
        List<ComboValues>  getAuthList();

	//void save(User user);
        
       // void insert(User user);

	//void update(User user);

	//void delete(Integer id);
        //void delete(User user);
        
        void setidAuth(Integer idAuth);
        
        Integer getidAuth() ;
        
        void setlogStatus(Integer log);
        
        Integer getlogStatus() ;
        
        
        void setidUser(Integer idUser);
        
        Integer getidUser() ;
        
        List<User> executeProcUser(String p_akcija, Integer p_idUser, String p_firstname,String p_secondname,
                String p_username,String p_password,Integer p_idAuth, Integer p_status,Integer p_logged);
        
        Integer DisplayLogStatusByUserName(String username,String password);

}
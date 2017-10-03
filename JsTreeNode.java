/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.pio.model;

import java.util.List;

/**
 *
 * @author bd101009
 */

public class JsTreeNode
{
    public JsTreeNode()
    {
    }
     String id;
     String idAuth;
     String itemTitle;
     String itemAction;
     String itemLevel;
     String idParent;
     String idTv;

    public String getIdParent() {
        return idParent;
    }

    public void setIdParent(String idParent) {
        this.idParent = idParent;
    }

    public String getIdTv() {
        return idTv;
    }

    public void setIdTv(String idTv) {
        this.idTv = idTv;
    }


    public String getIdAuth() {
        return idAuth;
    }

    public void setIdAuth(String idAuth) {
        this.idAuth = idAuth;
    }
     String text;

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemAction() {
        return itemAction;
    }

    public void setItemAction(String itemAction) {
        this.itemAction = itemAction;
    }

    public String getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(String itemLevel) {
        this.itemLevel = itemLevel;
    }

   
     
     //String path;
     
    Attributes data;
    TData metadata;

    public TData getMetadata() {
        return metadata;
    }

    public void setMetadata(TData metadata) {
        this.metadata = metadata;
    }

    public Attributes getData() {
        return data;
    }

    public void setData(Attributes data) {
        this.data = data;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /* public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }*/
    
    /*public TData getXdata() {
        return xdata;
    }

    public void setXdata(TData xdata) {
        this.xdata = xdata;
    }*/
    //String state;

   /* public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }*/
   
    List<JsTreeNode> children;
    public List<JsTreeNode> getChildren() 
    {
        return children;
    }

    public void setChildren(List<JsTreeNode> children) 
    {
            this.children = children;
    }

}




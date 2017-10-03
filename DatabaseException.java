/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.pio.exceptions;

/**
 *
 * @author bd101009
 */
public class DatabaseException extends RuntimeException { 
 
 
 	/** 
 	 * Unique ID for Serialized object 
 	 */ 
 	private static final long serialVersionUID = 4657491283614455649L; 
 
 
 	public DatabaseException(String msg) { 
 		super(msg); 
 	} 

 
 	public DatabaseException(String msg, Throwable t) { 
 		super(msg, t); 
 	} 
 
 } 


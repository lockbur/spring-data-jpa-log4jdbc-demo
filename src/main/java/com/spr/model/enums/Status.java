/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spr.model.enums;

/**
 * 
 * @author lcockbur@aboutdata.me
 */
public enum Status {

	UNASSIGNED("Œ¥¥¶¿Ì"), ERROR("ERROR");

	private String key;

	private Status(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}

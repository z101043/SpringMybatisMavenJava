package org.ranestar.test.domain;

import java.util.Date;

import lombok.Data;

@Data
public class User {
	
	private String id;
	private String name;
	private Date reg_date;
	
}

package kr.kwangan2.domain;

import lombok.Data;

@Data
public class Comm {
	
	private int commId;
	private String commTitle;
	private String commContent;
	private int fkblogId;

}

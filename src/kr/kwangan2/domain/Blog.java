package kr.kwangan2.domain;

import java.util.List;

import lombok.Data;

@Data
public class Blog {
	
	private int blogId;
	private String blogName;
	private String blogTitle;
	private Author author;
	private List<Comm> comms;
	private int fkAuthorId;
	
	public Blog() {
	}
	
	public Blog(Integer blogId) {
		this.blogId = blogId;
	}
	
}

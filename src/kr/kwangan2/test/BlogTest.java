package kr.kwangan2.test;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import kr.kwangan2.domain.Author;
import kr.kwangan2.domain.Blog;
import kr.kwangan2.domain.Comm;
import kr.kwangan2.domain.CommSearcher;

public class BlogTest {

	public static void main(String[] args) {
		
		String resource = "kr/kwangan2/conf/sqlMapConfig.xml";
		Reader reader;
		SqlSession session = null;
		
		try {
			reader = Resources.getResourceAsReader(resource);
			SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
			session = sqlMapper.openSession();
			
			// list (Blog, Author, Comm)
			List<Blog> blogs = session.selectList("kr.kwangan2.conf.Blog.selectBlog");
			
			for (Blog blog : blogs) {
				System.out.println("-------------------------------------------");
				System.out.println("blog id: " + blog.getBlogId());
				Author author = blog.getAuthor();
				System.out.println("\t" + author);
				List<Comm> comms = blog.getComms();
				
				for (Comm comm : comms) {
					System.out.println("\t" + comm);
				}	// for (Comm)
				
				System.out.println("-------------------------------------------");
			}	// for (Blog)
			
			CommSearcher commSearcher = new CommSearcher();
			commSearcher.setFkblogId(2);
			commSearcher.setSearchKey("content");
			commSearcher.setSearchValue("comm");
			
			List<Comm> comms = session.selectList("selectCommsByBlogId", commSearcher);
			
			for (Comm comm : comms) {
				System.out.println(comm);
			}
			
			// update (Comm)
			Comm comm = new Comm();
			comm.setCommId(3);
			comm.setCommTitle("update title");
			comm.setCommContent("update content");
			comm.setFkblogId(21);
			
			int result = session.update("updateComm", comm);
			
			if (result>0) {
				System.out.println("Comm 업데이트 성공");
			}
			
			// insert (Comm)
			comm.setCommTitle("insert title");
			comm.setCommContent("insert content");
			
			int result2 = session.insert("insertComm", comm);
			
			if (result2>0) {
				System.out.println("Comm 추가 성공");
			}
						
			// delete (Comm)
			
			ArrayList<Integer> commIdlist = new ArrayList<Integer>();
			commIdlist.add(2);
			commIdlist.add(2);
			commIdlist.add(3);
			commIdlist.add(21);
			
			// update (Author)
			Author author = new Author();
			author.setAuthorId(2);
			author.setAuthorName("update author");
			
			int authorupdateresult = session.update("updateAuthor", author);
			
			if (authorupdateresult>0) {
				System.out.println("Author 업데이트 성공");
			}
			
			// insert (Author)
			author.setAuthorName("insert author");
			
			int authorinsertresult = session.update("insertAuthor", author);
			
			if (authorinsertresult>0) {
				System.out.println("Author 추가 성공");
			}
			
			// delete (Author)
			int authordeleteresult = session.delete("deleteAuthor", 1);
			
			if (authordeleteresult>0) {
				System.out.println("Author 삭제 성공");
			}
			
			// update (Blog)
			Blog blog = new Blog();
			blog.setBlogId(21);
			blog.setBlogName("update blog name");
			blog.setBlogTitle("update blog title");
			blog.setFkAuthorId(2);
			
			int blogupdateresult = session.update("updateBlog", blog);
			
			if (authorupdateresult>0) {
				System.out.println("Blog 업데이트 성공");
			}
			
			// insert (Blog)
			blog.setBlogName("insert blog name");
			blog.setBlogTitle("insert blog title");
			blog.setFkAuthorId(2);
			
			int bloginsertresult = session.insert("insertBlog", blog);
			
			if (bloginsertresult>0) {
				System.out.println("Blog 추가 성공");
			}
			
			List<Comm> commList = session.selectList("selectCommsByCommIds", commIdlist);
			
			for (Comm comm2 : commList) {
				System.out.println(comm2);
			}
			
			session.commit();
			
		} catch (IOException e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			if (session!=null) session.close();
		}
	
	}	// main

}	// class

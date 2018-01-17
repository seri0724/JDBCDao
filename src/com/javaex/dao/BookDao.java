package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.AuthorVo;
import com.javaex.vo.BookVo;

public class BookDao {
	
	public void insertBook(BookVo bvo) {
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
		    // 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		    // 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

		    // 3. SQL문 준비 / 바인딩 / 실행
			String query = "INSERT INTO book VALUES (seq_book_id.nextval, ? , ? , ? , ?)";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, bvo.getTitle());
			pstmt.setString(2, bvo.getPubs());
			pstmt.setString(3, bvo.getPub_date());
			pstmt.setInt(4, bvo.getAuthor_id());
			
			int count = pstmt.executeUpdate();
			
		    // 4.결과처리
			System.out.println(count+"건 저장완료");
			
		} catch(ClassNotFoundException e) {
		    System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch(SQLException e) {
		    System.out.println("error:" + e);
		} finally {
		   
		    // 5. 자원정리
		    try {        
		        if(pstmt != null) {
		            pstmt.close();
		        }
		        if(conn != null) {
		            conn.close();
		        }
		    } catch (SQLException e) {
		        System.out.println("error:" + e);
		    }

		}
	}
	public List<BookVo> selectBookList() {
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<BookVo> bookList = new ArrayList<BookVo>();
		
		try {
		    // 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		    // 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		    
			// 3. SQL문 준비 / 바인딩 / 실행
		    String query = " select b.book_id, b.title, b.pubs, b.pub_date, b.author_id, a.author_name, a.author_desc "+ 
		    		       " from book b, author a "+
		    		       " where b.author_id = a.author_id ";
		    
			pstmt = conn.prepareStatement(query);
		    rs = pstmt.executeQuery();
							    
		    // 4.결과처리
		    while(rs.next()) {
		    	BookVo bvo = new BookVo();
		    	
		    	int book_id = rs.getInt("book_id");
		    	String title = rs.getString("title");
		    	String pubs = rs.getString("pubs");
		    	String pub_date = rs.getString("pub_date");
		    	int author_id = rs.getInt("author_id");
		    	String authorName = rs.getString("author_name");
		    	String authorDesc = rs.getString("author_desc");
		    	
		    	bvo.setBook_id(book_id);
		    	bvo.setTitle(title);
		    	bvo.setPubs(pubs);
		    	bvo.setPub_date(pub_date);
		    	bvo.setAuthor_id(author_id);
		    	
		    	bookList.add(bvo);	
		    	
		    }		    
		    	
		} catch (ClassNotFoundException e) {
		    System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} finally {
		   
		    // 5. 자원정리
		    try {
		        if (rs != null) {
		            rs.close();
		        }                
		        if (pstmt != null) {
		            pstmt.close();
		        }
		        if (conn != null) {
		            conn.close();
		        }
		    } catch (SQLException e) {
		        System.out.println("error:" + e);
		    }

		}
		return bookList;
	}

}

	

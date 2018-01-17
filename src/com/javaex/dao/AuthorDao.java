package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.AuthorVo;

public class AuthorDao {

	public void insertAuthor(AuthorVo vo) {
		
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
					if(vo.getAuthorId()==0) {
						String query = "INSERT INTO author VALUES (seq_author_id.nextval, ? , ?)";
						pstmt = conn.prepareStatement(query);
						pstmt.setString(1, vo.getAuthorName());
						pstmt.setString(2, vo.getAuthorDesc());
					}else {
						String query = "INSERT INTO author VALUES (? , ? , ?)";
						pstmt = conn.prepareStatement(query);
						pstmt.setInt(1, vo.getAuthorId());
						pstmt.setString(2, vo.getAuthorName());
						pstmt.setString(3, vo.getAuthorDesc());
					}
					
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
	
	public List<AuthorVo> selectAuthorList() {
		
				// 0. import java.sql.*;
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				
				List<AuthorVo> authorList = new ArrayList<AuthorVo>();
				
				try {
				    // 1. JDBC 드라이버 (Oracle) 로딩
					Class.forName("oracle.jdbc.driver.OracleDriver");
					
				    // 2. Connection 얻어오기
					String url = "jdbc:oracle:thin:@localhost:1521:xe";
					conn = DriverManager.getConnection(url, "webdb", "webdb");
				    
					// 3. SQL문 준비 / 바인딩 / 실행
				    String query = " select author_id, author_name, author_desc "+ 
				    		       " from author ";
				    
					pstmt = conn.prepareStatement(query);
				    rs = pstmt.executeQuery();
					
				    				    
				    // 4.결과처리
				    while(rs.next()) {
				    	AuthorVo vo = new AuthorVo();
				    	int authorId = rs.getInt("author_id");
				    	String authorName = rs.getString("author_name");
				    	String authorDesc = rs.getString("author_desc");
				    	vo.setAuthorId(authorId);
				    	vo.setAuthorName(authorName);
				    	vo.setAuthorDesc(authorDesc);
				    	authorList.add(vo);				    	
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
				return authorList;
		}
}

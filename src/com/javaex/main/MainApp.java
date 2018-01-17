package com.javaex.main;

import java.util.List;

import com.javaex.dao.*;
import com.javaex.vo.*;

public class MainApp {

	public static void main(String[] args) {

		 /*AuthorVo vo = new AuthorVo("강풀", "26년");
		
		
		 AuthorDao aDao = new AuthorDao();
		 aDao.insertAuthor(vo);
		
		 List<AuthorVo> authorList = aDao.selectAuthorList();
		
		 System.out.println(authorList.toString());*/

		 BookVo bvo = new BookVo("우리들의 일그러진 영웅", "다림", "1998-02-22", 1);

		 BookDao bDao = new BookDao();
		 bDao.insertBook(bvo);

		 List<BookVo> bookList = bDao.selectBookList();

		 for (BookVo book : bookList) {
			System.out.println(book);
		}

	}

}

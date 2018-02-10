package egovframework.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 페이징 공용 클래스
 *
 */
public class Pager {

	private int startPage = 1;
	private int endPage = 1;
	private int prevPage = 0;
	private int nextPage = 0;
	private int totalPage;
	private int currentPage;
	private int pageSize;
	private int listSize;
	private int totalCount;
	private List<Integer> pageList;
	
	/**
	 * constructor
	 * @param currentPage
	 * @param totalCount
	 * @param pageSize
	 * @param listSize
	 */
	public Pager( int currentPage, int totalCount, int pageSize, int listSize )
	{
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.listSize = listSize;
		
		this.pageList = new ArrayList<Integer>();;
		
		calc();
	}
	
	/**
	 * constructor
	 * @param currentPage
	 * @param totalCount
	 * @param pageSize
	 */
	public Pager( int currentPage, int totalCount, int pageSize )
	{
		this( currentPage, totalCount, pageSize, 10 );
	}
	
	/**
	 * constructor
	 * @param currentPage
	 * @param totalCount
	 */
	public Pager( int currentPage, int totalCount )
	{
		this( currentPage, totalCount, 10, 10 );
	}
	
	/**
	 * 내부 계산 로직 business
	 */
	public void calc() {
		// 전체페이지
		this.totalPage	= (int) Math.ceil( (double) totalCount / pageSize );
		
		// 레코드가 없다면 전체는 1페이지로 잡자.
		if( this.totalPage == 0 ) {
			this.endPage = 1;
			this.totalPage = 1;
		}
		
		// 현재페이지 확인
		if( this.currentPage > this.totalPage )
			this.currentPage = this.totalPage;
		
		// 시작페이지 값
		this.startPage = (int) ( (this.currentPage - 1) / this.listSize ) * this.listSize + 1;
		// 종료페이지 값
		this.endPage = (int) this.startPage + this.listSize - 1;
		
		// 종료페이지 값 확인
		if( this.endPage > this.totalPage )
			this.endPage = this.totalPage;
		
		if( this.startPage > 1 )
			this.prevPage = this.startPage - 1;
		
		if( this.endPage < this.totalPage )
			this.nextPage = this.endPage + 1;
		
		// JSTL의 forEach 를 사용하지 못한다면 이렇게 쓰자.
		for( int i = this.startPage; i <= this.endPage; i++ ) {
			pageList.add( (Integer) i );
		}
	}
	
	// has 로 메서드를 만들면 엑세스가 안되네.. -_-
	/*
	public boolean hasPrevPage() {
		if( startPage > 1 )
			return true;
		else
			return false;
	}
	
	public boolean hasNextPage() {
		if( endPage > totalPage )
			return true;
		else
			return false;
	}
	*/

	
	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public int getPrevPage() {
		return prevPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getListSize() {
		return listSize;
	}

	public int getTotalCount() {
		return totalCount;
	}
	
	public List<Integer> getPageList() {
		return this.pageList;
	}
	public String toString (){
		return "totalCount"+totalCount;
	}
	
}

package com.gura.spring.qna.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gura.spring.qna.dao.QnaDao;
import com.gura.spring.qna.dto.QnaDto;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.gura.spring.exception.NotDeleteException;
import com.gura.spring.users.UsersDao;
import com.gura.spring.users.UsersDto;



@Service
public class QnaServiceImpl implements QnaService{
	
	@Autowired
	private QnaDao qnadao;
	
	@Autowired
	private UsersDao userDao;

	@Override
	public void getList(HttpServletRequest request, HttpSession session) {
		//�� �������� ��� ǥ���� ������
		final int PAGE_ROW_COUNT=5;
		//�ϴ� �������� ��� ǥ���� ������
		final int PAGE_DISPLAY_COUNT=5;
				
		//������ �������� ��ȣ�� �ϴ� 1�̶�� �ʱⰪ ����
		int pageNum=1;
		//������ ��ȣ�� �Ķ���ͷ� ���޵Ǵ��� �о�� ����.
		String strPageNum=request.getParameter("pageNum");
		//���� ������ ��ȣ�� �Ķ���ͷ� �Ѿ� �´ٸ�
		if(strPageNum != null){
		//���ڷ� �ٲ㼭 ������ ������ ��ȣ�� �����Ѵ�.
		pageNum=Integer.parseInt(strPageNum);
		}
				
		//������ �������� ���� ROWNUM
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
		//������ �������� �� ROWNUM
		int endRowNum=pageNum*PAGE_ROW_COUNT;
		String keyword=request.getParameter("keyword");
		String condition=request.getParameter("condition");
		//���� Ű���尡 �Ѿ���� �ʴ´ٸ� 
		if(keyword==null){
		//Ű����� �˻� ���ǿ� �� ���ڿ��� �־��ش�. 
		//Ŭ���̾�Ʈ ���������� ����Ҷ� "null" �� ��µ��� �ʰ� �ϱ� ���ؼ�  
		keyword="";
		condition=""; 
			}

		//Ư����ȣ�� ���ڵ��� Ű���带 �̸� �غ��Ѵ�. 
		String encodedK=URLEncoder.encode(keyword);
					
		//QnaDto ��ü�� startRowNum �� endRowNum �� ��´�.
		QnaDto dto=new QnaDto();
		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);

		//���� �˻� Ű���尡 �Ѿ�´ٸ� 
		if(!keyword.equals("")){
		//�˻� ������ �����̳Ŀ� ���� �б� �ϱ�
		if(condition.equals("title_content")){//���� + ���� �˻��� ���
		//�˻� Ű���带 CafeDto �� ��Ƽ� �����Ѵ�.
		dto.setTitle(keyword);
		dto.setContent(keyword);
		}else if(condition.equals("title")){ //���� �˻��� ���
		dto.setTitle(keyword);
		}else if(condition.equals("writer")){ //�ۼ��� �˻��� ���
		dto.setWriter(keyword);
			} // �ٸ� �˻� ������ �߰� �ϰ� �ʹٸ� �Ʒ��� else if() �� ��� �߰� �ϸ� �ȴ�.
		}
		//�� ��� ������ 
		List<QnaDto> list= qnadao.getList(dto);
		//��ü���� ����
		int totalRow = qnadao.getCount(dto);		
		//�ϴ� ���� ������ ��ȣ 
		int startPageNum = 1 + ((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//�ϴ� �� ������ ��ȣ
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
				
		//��ü �������� ����
		int totalPageCount=(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		//�� ������ ��ȣ�� ��ü ������ �������� ũ�ٸ� �߸��� ���̴�.
		if(endPageNum > totalPageCount){
		endPageNum=totalPageCount; //������ �ش�.
		}
		//view page ���� �ʿ��� ���� request �� ����ش�.
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPageNum", startPageNum);
		request.setAttribute("endPageNum", endPageNum);
		request.setAttribute("condition", condition);
		request.setAttribute("keyword", keyword);
		request.setAttribute("encodedK", encodedK);
		request.setAttribute("totalPageCount", totalPageCount);
		request.setAttribute("list", list);
		request.setAttribute("totalRow", totalRow);
		
		String id = (String) session.getAttribute("id");
		
		if(!StringUtils.isEmpty(id)) {
			UsersDto user = userDao.getData(id);
			request.setAttribute("adminNum", user.getAdminNum());
		} else {
			request.setAttribute("adminNum", 0);
		}
		
	}

	@Override
	public void getDetail(HttpServletRequest request, HttpSession session) {
	//�ڼ��� ������ �۹�ȣ�� �о�´�. 
	int num=Integer.parseInt(request.getParameter("num"));
	//��ȸ�� �ø���
	qnadao.addViewCount(num);
	String keyword=request.getParameter("keyword");
	String condition=request.getParameter("condition");
	//���� Ű���尡 �Ѿ���� �ʴ´ٸ� 
	if(keyword==null){
	//Ű����� �˻� ���ǿ� �� ���ڿ��� �־��ش�. 
	//Ŭ���̾�Ʈ ���������� ����Ҷ� "null" �� ��µ��� �ʰ� �ϱ� ���ؼ�  
	keyword="";
	condition=""; 
	}
	
	QnaDto dto=new QnaDto();
	//�ڼ��� ������ �۹�ȣ�� �־��ش�. 
	dto.setNum(num);
	//���� �˻� Ű���尡 �Ѿ�´ٸ� 
	if(!keyword.equals("")){
	//�˻� ������ �����̳Ŀ� ���� �б� �ϱ�
	if(condition.equals("title_content")){//���� + ���� �˻��� ���
	//�˻� Ű���带 QnaDto �� ��Ƽ� �����Ѵ�.
	dto.setTitle(keyword);
	dto.setContent(keyword);			
	}else if(condition.equals("title")){ //���� �˻��� ���
	dto.setTitle(keyword);	
	}else if(condition.equals("writer")){ //�ۼ��� �˻��� ���
	dto.setWriter(keyword);	
		} // �ٸ� �˻� ������ �߰� �ϰ� �ʹٸ� �Ʒ��� else if() �� ��� �߰� �ϸ� �ȴ�.
	}	
	dto=qnadao.getData(dto.getNum());

	//Ư����ȣ�� ���ڵ��� Ű���带 �̸� �غ��Ѵ�. 
	String encodedK=URLEncoder.encode(keyword);
	
	request.setAttribute("dto", dto);
	
	String id = (String) session.getAttribute("id");
	
	
	//id != null && id != ""
	//!StringUtils.isEmpty(id)
	if(!StringUtils.isEmpty(id)) {
		UsersDto user = userDao.getData(id);
		request.setAttribute("adminNum", user.getAdminNum());
	} else {
		request.setAttribute("adminNum", 0);
	}
		
	}

	//���� ����
	@Override
	public int saveContent(QnaDto dto) {
		
		return qnadao.insert(dto);

		
	}

	@Override
	public void updateContent(QnaDto dto) {
		
		qnadao.update(dto);
		
	}

	@Override
	public void deleteContent(int num, HttpServletRequest request) {
		//���ǿ��� �α��ε� ���̵� �о�ͼ�
		String id=(String)request.getSession().getAttribute("id");
		//������ ���� �ۼ���
		String writer=qnadao.getData(num).getWriter();
		//���� ���� �ۼ��ڰ� �α��ε� ���̵�� �ٸ��ٸ� 
		/*
		 * if(!writer.equals(id)) { //���ܸ� �߻����Ѽ� ������ ���� Controller ���� �ϵ��� �Ѵ�. throw new
		 * NotDeleteException("���� ���� ����� ����!"); }
		 */
		//������ �ۼ��� ���� �ƴϸ� �Ʒ��� �ڵ尡 ������ �ȵǾ� �ȴ�. 
		qnadao.delete(num);
		
	}

	@Override
	public void getData(HttpServletRequest request) {
		//������ �۹�ȣ
		int num=Integer.parseInt(request.getParameter("num"));
		//������ ���� ���� ���ͼ� 
		QnaDto dto=qnadao.getData(num);
		//request �� ����ش�.
		request.setAttribute("dto", dto);
	}


}


/*
@Service
public class QnaServiceImpl implements QnaService {
	@Autowired private QnaDao dao;
	
	@Override
	public void qna_insert(QnaVO vo) {
		dao.qna_insert(vo);
	}

	@Override
	public List<QnaVO> qna_list() {
		return dao.qna_list();
	}

	@Override
	public QnaVO qna_detail(int id) {
		return dao.qna_detail(id);
	}

	@Override
	public void qna_update(QnaVO vo) {
		dao.qna_update(vo);
	}

	@Override
	public void qna_delete(int id) {
		dao.qna_delete(id);
	}

	@Override
	public void qna_read(int id) {
		dao.qna_read(id);
	}

	@Override
	public void qna_reply_insert(QnaVO vo) {
		dao.qna_reply_insert(vo);
	}

	@Override
	public QnaPage qna_list(QnaPage page) {
		return dao.qna_list(page);
	}

}
*/
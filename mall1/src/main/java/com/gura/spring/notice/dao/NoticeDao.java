package com.gura.spring.notice.dao;

import java.util.List;
import com.gura.spring.notice.dto.NoticeDto;

public interface NoticeDao {

	//�۸��
	public List<NoticeDto>getList(NoticeDto dto);
	//���� ����
	public int getCount(NoticeDto dto);
	//�� �߰�
	public int insert(NoticeDto dto);
	//������ ������
	public NoticeDto getData(int num);
	//Ű���带 Ȱ���� ������ ������
	public NoticeDto getData(NoticeDto dto);
	//��ȸ�� ���� ��Ű��
	public void addViewCount(int num);
	//�� ����
	public void delete(int num);
	//�� ����
	public void update(NoticeDto dto);
}



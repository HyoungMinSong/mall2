package com.gura.spring.qna.dao;

import java.util.List;
import com.gura.spring.qna.dto.QnaDto;

public interface QnaDao {

	//�۸��
	public List<QnaDto>getList(QnaDto dto);
	//���� ����
	public int getCount(QnaDto dto);
	//�� �߰�
	public int insert(QnaDto dto);
	//������ ������
	public QnaDto getData(int num);
	//Ű���带 Ȱ���� ������ ������
	public QnaDto getData(QnaDto dto);
	//��ȸ�� ���� ��Ű��
	public void addViewCount(int num);
	//�� ����
	public void delete(int num);
	//�� ����
	public void update(QnaDto dto);
}

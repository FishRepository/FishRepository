package com.yy.online.service;

import java.util.Map;

public interface OnlineService {
	/**
	 * 用户注册
	 * 反射regUser值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> regUser(Map<String, Object> parm);
	
	/**
	 * 添加岗位
	 * 反射addPost值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> addPost(Map<String, Object> parm);
	
	/**
	 * 获取岗位
	 * 反射getPost值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getPost(Map<String, Object> parm);
	
	/**
	 * 添加证书
	 * 反射addCert值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> addCert(Map<String, Object> parm);
	
	/**
	 * 获取所有证书
	 * 反射getCert值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getCert(Map<String, Object> parm);
	
	/**
	 * 获取所有证书by post
	 * 反射getCertByPost值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getCertByPost(Map<String, Object> parm);
	
	/**
	 * 试题导入
	 * 反射addXls值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> addXls(Map<String, Object> parm);
	
	/**
	 * 搜索试题
	 * 反射searchTopic值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> searchTopic(Map<String, Object> parm);
	
	/**
	 * 试题添加图片
	 * 反射updateTopicImage值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> updateTopicImage(Map<String, Object> parm);
	
	/**
	 * 获取免费试题
	 * 反射getTopicByRang值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getTopicByRang(Map<String, Object> parm);

	/**
	 * 获取用户信息
	 * 反射getUserInfo值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getUserInfo(Map<String, Object> parm);
	
	/**
	 * 修改用户信息
	 * 反射updateUserInfo值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> updateUserInfo(Map<String, Object> parm);
	
	/**
	 * 订单记录
	 * 反射pay值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> pay(Map<String, Object> parm);
	
	/**
	 * 获取用户解析币
	 * 反射getUserCoin值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getUserCoin(Map<String, Object> parm);
	
	/**
	 * 用户充值解析币
	 * 反射rechargeCoin值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> rechargeCoin(Map<String, Object> parm);
	
	/**
	 * 用户花费解析币
	 * 反射subCoin值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> subCoin(Map<String, Object> parm);
	
	/**
	 * 获取试题解析
	 * 反射getTopicExplain值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getTopicExplain(Map<String, Object> parm);
	
	/**
	 * 获取订单列表
	 * 反射getOrderList值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getOrderList(Map<String, Object> parm);
	
	/**
	 * 删除试题
	 * 反射delTopic值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> delTopic(Map<String, Object> parm);
	
	
	
	
	/**
	 * 管理员登录
	 * 反射adminLogin值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> adminLogin(Map<String, Object> parm);

	
	/**
	 * 获取用户列表by区间
	 * 反射getUserByPage值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getUserByPage(Map<String, Object> parm);
	
	/**
	 * 添加试卷
	 * 反射addExamClass值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> addExamClass(Map<String, Object> parm);
	
	/**
	 * 获取试卷列表
	 * 反射getExamList值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getExamList(Map<String, Object> parm);
	
	/**
	 * 获取所有试卷
	 * 反射getAllExam值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getAllExam(Map<String, Object> parm);
	
	/**
	 * 添加试卷试题
	 * 反射addExamXls值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> addExamXls(Map<String, Object> parm);
	
	/**
	 * 获取一类套卷
	 * 反射getExamByClass值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getExamByClass(Map<String, Object> parm);
	
	/**
	 * 获取用户所有套卷
	 * 反射getUserExamList值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getUserExamList(Map<String, Object> parm);
	
	/**
	 * 获取付费试题
	 * 反射getPayTopicByRang值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getPayTopicByRang(Map<String, Object> parm);
	
	/**
	 * 添加广告位
	 * 反射addAdv值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> addAdv(Map<String, Object> parm);
	
	/**
	 * 获取所有广告位
	 * 反射getAllAdv值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getAllAdv(Map<String, Object> parm);
	
	/**
	 * 删除广告位
	 * 反射delAdv值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> delAdv(Map<String, Object> parm);
	
	/**
	 * 获取显示广告位
	 * 反射getShowAdv值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getShowAdv(Map<String, Object> parm);
	
	/**
	 * 更新广告
	 * 反射updataAdv值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> updataAdv(Map<String, Object> parm);
	
	/**
	 * 不显示广告
	 * 反射updataAdvShow值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> updataAdvShow(Map<String, Object> parm);
	
	/**
	 * 查找试卷
	 * 反射getUserExam值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getUserExam(Map<String, Object> parm);
	
	/**
	 * 海报信息
	 * 反射updateAdvInfo值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> updateAdvInfo(Map<String, Object> parm);
	
	/**
	 * 获取海报
	 * 反射getAdvInfo值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getAdvInfo(Map<String, Object> parm);
	
	/**
	 * 搜索付费试题
	 * 反射searchPayTopic值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> searchPayTopic(Map<String, Object> parm);
	
	/**
	 * addSection 添加章节
	 * 反射addSection值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> addSection(Map<String, Object> parm);
	
	/**
	 * delSection 删除章节
	 * 反射delSection值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> delSection(Map<String, Object> parm);
	
	/**
	 * updataSection 更新章节
	 * 反射updataSection值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> updataSection(Map<String, Object> parm);
	
	/**
	 * getSection 获取章节
	 * 反射getSection值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getSection(Map<String, Object> parm);
	
	/**
	 * addSectionTopic 添加章节题目
	 * 反射addSectionTopic值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> addSectionTopic(Map<String, Object> parm);
	
	/**
	 * searchSectionTopic 搜索章节试题
	 * 反射searchSectionTopic值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> searchSectionTopic(Map<String, Object> parm);
	
	/**
	 * delSectionTopic 删除章节试题
	 * 反射delSectionTopic值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> delSectionTopic(Map<String, Object> parm);
	
	/**
	 * updateSectionTopicImage 修改章节试题  添加图片
	 * 反射updateSectionTopicImage值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> updateSectionTopicImage(Map<String, Object> parm);
	
	/**
	 * getSectionTopic 获取章节试题
	 * 反射getSectionTopic值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getSectionTopic(Map<String, Object> parm);
	
	/**
	 * getSectionExplain 获取章节试题解析
	 * 反射getSectionExplain值的同名方法
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getSectionExplain(Map<String, Object> parm);

    /**
     * 获取英语课程列表
     * @param parm
     * @return
     */
    public Map<String, Object> getEnglishClass(Map<String, Object> parm);

    /**
     * 添加英语课程
     * @param parm
     * @return
     */
    public Map<String, Object> addEnglishClass(Map<String, Object> parm);

    /**
     * 删除英语课程
     * @param parm
     * @return
     */
    public Map<String, Object> deleteEnglishClass(Map<String, Object> parm);

    /**
     * 更新英语课程
     * @param parm
     * @return
     */
    public Map<String, Object> updateEnglishClass(Map<String, Object> parm);

    /**
     * 更新课程图片
     * @param parm
     * @return
     */
    public Map<String, Object> updateEnglishClassPic(Map<String, Object> parm);

    /**
     * 获取所有英语课程
     * @return
     */
    public Map<String, Object> getAllEnglishClass();

    /**
     * 获取英语课程章节列表
     * @param parm
     * @return
     */
    public Map<String, Object> getEnglishChap(Map<String, Object> parm);

    /**
     * 添加英语课程章节
     * @param parm
     * @return
     */
    public Map<String, Object> addEnglishChap(Map<String, Object> parm);

    /**
     * 删除英语课程章节
     * @param parm
     * @return
     */
    public Map<String, Object> deleteEnglishChap(Map<String, Object> parm);

    /**
     * 更新英语课程章节
     * @param parm
     * @return
     */
    public Map<String, Object> updateEnglishChap(Map<String, Object> parm);

    /**
     * 获取所有英语课程章节
     * @param parm
     * @return
     */
    public Map<String, Object> getChapByClassId(Map<String, Object> parm);

    /**
     * 添加英语试题
     * @param parm
     * @return
     */
    public Map<String, Object> addEnglishQuestion(Map<String, Object> parm);

    /**
     * 获取英语试题
     * @param parm
     * @return
     */
    public Map<String, Object> getEnglishQuestion(Map<String, Object> parm);

    /**
     * 修改英语试题
     * @param parm
     * @return
     */
    public Map<String, Object> updEnglishQuestion(Map<String, Object> parm);

    /**
     * 删除英语试题
     * @param parm
     * @return
     */
    public Map<String, Object> delEnglishQuestion(Map<String, Object> parm);

    /**
     * 更新英语试题图片
     * @param parm
     * @return
     */
    public Map<String, Object> updateEnglishQuestionPic(Map<String, Object> parm);

    /**
     * 更新英语试题音频地址
     * @param parm
     * @return
     */
    public Map<String, Object> updateEnglishQuestionVol(Map<String, Object> parm);

    /**
     * 获取英语全量数据(包括课程-章节-试题)
     * @return
     */
    public Map<String, Object> getAllEnglishData();

    /**
     * 根据课程ID获取章节列表
     * @return
     */
    public Map<String, Object> getAllEnglishChapDataByClassId(Map<String, Object> parm);

    /**
     * 根据章节ID获取所有试题列表
     * @return
     */
    public Map<String, Object> getAllEnglishQuestionDataByChapId(Map<String, Object> parm);

    /**
     * 根据章节ID获取听力试题列表
     * @return
     */
    public Map<String, Object> getAllEnglishQuestionListenDataByChapId(Map<String, Object> parm);

    /**
     * 根据章节ID获取会话试题列表
     * @return
     */
    public Map<String, Object> getAllEnglishQuestionTalkDataByChapId(Map<String, Object> parm);

    /**
     * 插入用户英语课程购买记录
     * @param parm
     * @return
     */
    public Map<String, Object> addEnglishPayRecord(Map<String, Object> parm);

    /**
     * 查询用户英语课程购买记录
     * @param parm
     * @return
     */
    public Map<String, Object> getEnglishPayRecord(Map<String, Object> parm);
}

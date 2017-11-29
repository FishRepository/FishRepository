package com.yy.online.dao;

import java.util.List;
import java.util.Map;

public interface OnlineDao {
	
	/*静默注册 微信openid*/
	public int regUser(Map<String, Object> parm);
	
	/*添加岗位*/
	public int addPost(Map<String, Object> parm);
	
	/*获取岗位*/
	public List<Map<String, Object>> getPost(Map<String, Object> parm);
	
	/*添加岗位*/
	public int addCert(Map<String, Object> parm);
	
	/*获取证书*/
	public List<Map<String, Object>> getCert(Map<String, Object> parm);
	
	/*获取证书by post*/
	public List<Map<String, Object>> getCertByPost(Map<String, Object> parm);
	
	/*导入试题*/
	public int addXls(Map<String, Object> parm);
	
	/*搜索试题*/
	public List<Map<String, Object>> searchTopic(Map<String, Object> parm);
	
	/*试题添加图片*/
	public int updateTopicImage(Map<String, Object> parm);
	
	/*导入付费试题*/
	public int addPayXls(Map<String, Object> parm);
	
	/*搜索付费试题*/
	public List<Map<String, Object>> searchPayTopic(Map<String, Object> parm);
	
	/*付费试题添加图片*/
	public int updatePayTopicImage(Map<String, Object> parm);
	
	/*通过类型获取题目配置*/
	public Map<String, Object> getCertConf(Map<String, Object> parm);
	
	/*获取免费试题*/
	public List<Map<String, Object>> getTopicByRang(Map<String, Object> parm);
	
	/*获取付费试题*/
	public List<Map<String, Object>> getPayTopicByRang(Map<String, Object> parm);
	
	/*获取用户信息*/
	public Map<String, Object> getUserInfo(Map<String, Object> parm);
	
	/*获取用户信息*/
	public int updateUserInfo(Map<String, Object> parm);
	
	/*订单记录*/
	public int pay(Map<String, Object> parm);
	
	/*获取用户解析币*/
	public Map<String, Object> getUserCoin(Map<String, Object> parm);
	
	/*用户充值解析币*/
	public int rechargeCoin(Map<String, Object> parm);
	
	/*用户花费解析币*/
	public int subCoin(Map<String, Object> parm);
	
	/*获取试题解析*/
	public Map<String, Object> getTopicExplain(Map<String, Object> parm);
	
	/*获取订单列表*/
	public List<Map<String, Object>> getOrderList(Map<String, Object> parm);
	
	/*删除试题*/
	public int delTopic(Map<String, Object> parm);
	
	/*管理员登录*/
	public Map<String, Object> adminLogin(Map<String, Object> parm);

	/*获取用户列表by区间*/
	public List<Map<String, Object>> getUserByPage(Map<String, Object> parm);
	
	/*添加试卷*/
	public int addExamClass(Map<String, Object> parm);
	
	/*获取试卷列表*/
	public List<Map<String, Object>> getExamList(Map<String, Object> parm);
	
	/*获取所有试卷*/
	public List<Map<String, Object>> getAllExam(Map<String, Object> parm);
	
	/*添加试卷试题*/
	public int addExamXls(Map<String, Object> parm);
	
	/*获取一类套卷*/
	public List<Map<String, Object>> getExamByClass(Map<String, Object> parm);
	
	/*用户购买试题*/
	public int insertUserExam(Map<String, Object> parm);
	
	/*获取用户所有套卷*/
	public List<Map<String, Object>> getUserExamList(Map<String, Object> parm);
	
	/*通过试卷id获取试卷详情*/
	public Map<String, Object> getExamInfo(Map<String, Object> parm);
	
	/*添加广告位*/
	public int addAdv(Map<String, Object> parm);
	
	/*获取所有广告位*/
	public List<Map<String, Object>> getAllAdv(Map<String, Object> parm);
	
	/*删除广告位*/
	public int delAdv(Map<String, Object> parm);
	
	/*获取显示广告位*/
	public Map<String, Object> getShowAdv(Map<String, Object> parm);
	
	/*更新广告*/
	public int updataAdv(Map<String, Object> parm);
	
	/*不显示广告*/
	public int updataAdvShow(Map<String, Object> parm);
	
	/*删除试卷*/
	public int delUserExam(Map<String, Object> parm);
	
	/*查找试卷*/
	public int getUserExam(Map<String, Object> parm);
	
	/*海报信息*/
	public int updateAdvInfo(Map<String, Object> parm);
	
	/*获取海报*/
	public Map<String, Object> getAdvInfo(Map<String, Object> parm);
	
	/*addSection 添加章节*/
	public int addSection(Map<String, Object> parm);
	
	/*delSection  删除章节*/
	public int delSection(Map<String, Object> parm);
	
	/*updataSection  修改章节*/
	public int updataSection(Map<String, Object> parm);
	
	/*getSection  获取章节*/
	public List<Map<String, Object>> getSection(Map<String, Object> parm);
	
	/*addSectionTopic  添加章节题目*/
	public int addSectionTopic(Map<String, Object> parm);
	
	/*搜索章节试题*/
	public List<Map<String, Object>> searchSectionTopic(Map<String, Object> parm);
	
	/*delSectionTopic  删除章节试题*/
	public int delSectionTopic(Map<String, Object> parm);
	
	/*updateSectionTopicImage  修改章节试题  添加图片*/
	public int updateSectionTopicImage(Map<String, Object> parm);
	
	/*getSectionTopic  获取章节试题*/
	public List<Map<String, Object>> getSectionTopic(Map<String, Object> parm);
	
	/*getSectionExplain  获取章节试题解析*/
	public Map<String, Object> getSectionExplain(Map<String, Object> parm);

    /**
     * 获取英语课程列表
     * @param parm
     * @return
     */
    public List<Map<String,Object>> getEnglishClass(Map<String, Object> parm);

    /**
     * 添加英语课程
     * @param parm
     * @return
     */
    public int addEnglishClass(Map<String, Object> parm);

    /**
     * 删除英语课程
     * @param parm
     * @return
     */
    public int deleteEnglishClass(Map<String, Object> parm);

    /**
     * 更新英语课程
     * @param parm
     * @return
     */
    public int updateEnglishClass(Map<String, Object> parm);

    /**
     * 更新课程图片
     */
    public int updateEnglishClassPic(Map<String, Object> parm);

    /**
     * 获取所有英语课程列表
     * @return
     */
    public List<Map<String,Object>> getAllEnglishClass();

    /**
     * 获取英语课程章节列表
     * @param parm
     * @return
     */
    public List<Map<String,Object>> getEnglishChap(Map<String, Object> parm);

    /**
     * 添加英语课程章节
     * @param parm
     * @return
     */
    public int addEnglishChap(Map<String, Object> parm);

    /**
     * 删除英语课程章节
     * @param parm
     * @return
     */
    public int deleteEnglishChap(Map<String, Object> parm);

    /**
     * 更新英语课程章节
     * @param parm
     * @return
     */
    public int updateEnglishChap(Map<String, Object> parm);

    /**
     * 获取所有英语课程列表
     * @return
     */
    public List<Map<String,Object>> getChapByClassId(Map<String, Object> parm);

    /**
     * 添加英语试题
     * @param parm
     * @return
     */
    public int addEnglishQuestion(Map<String, Object> parm);

    /**
     * 获取所有的英语试题
     * @return
     */
    public List<Map<String,Object>> getEnglishQuestion(Map<String, Object> parm);

    /**
     * 删除所有的英语试题
     * @param parm
     * @return
     */
    public int delEnglishQuestion(Map<String, Object> parm);

    /**
     * 更新英语试题图片
     */
    public int updateEnglishQuestionPic(Map<String, Object> parm);

    /**
     * 更新英语试题音频地址
     */
    public int updateEnglishQuestionVol(Map<String, Object> parm);
}

package com.yy.online.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.yy.jave.ConvertAudio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yy.online.dao.OnlineDao;

@Transactional
@Service("onlineServiceImpl")
public class OnlineServiceImpl implements OnlineService{
	
	@Autowired
	private OnlineDao onlineDao;
	
	
	@Override
	public Map<String, Object> regUser(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (this.onlineDao.regUser(parm)>0) {
			resultMap.put(MESSAGE, "注册成功");
		} else {
			resultMap.put(MESSAGE, "已注册");
		}
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> addPost(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (this.onlineDao.addPost(parm)>0) {
			resultMap.put(MESSAGE, "添加成功");
		} else {
			resultMap.put(MESSAGE, "不能重复添加");
		}
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getPost(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("LIST", this.onlineDao.getPost(parm));
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> addCert(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (this.onlineDao.addCert(parm)>0) {
			resultMap.put(MESSAGE, "添加成功");
		} else {
			resultMap.put(MESSAGE, "不能重复添加");
		}
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getCert(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> postList = this.onlineDao.getPost(parm);
		for (int i = 0; i < postList.size(); i++) {
			Map<String, Object> parmMap = new HashMap<String, Object>();
			parmMap.put("CLASS", postList.get(i).get("postId"));
			List<Map<String, Object>> certMap = this.onlineDao.getCert(parmMap);
			postList.get(i).put("certList", certMap);
		}
		resultMap.put("LIST", postList);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getCertByPost(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("certList", this.onlineDao.getCertByPost(parm));
		return resultMap;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> addXls(Map<String, Object> parm) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		this.onlineDao.addXls(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> searchTopic(Map<String, Object> parm) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		System.out.println(parm.get("PAY_TYPE").equals("0"));
		if (parm.get("PAY_TYPE").equals("0")) {
			resultMap.put("LIST", this.onlineDao.searchTopic(parm));
		} else {
			resultMap.put("LIST", this.onlineDao.searchPayTopic(parm));
		}
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> updateTopicImage(Map<String, Object> parm) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		System.out.println(parm.get("PAY_TYPE").equals("0"));
		if (parm.get("PAY_TYPE").equals("0")) {
			this.onlineDao.updateTopicImage(parm);
		} else {
			this.onlineDao.updatePayTopicImage(parm);
		}
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getTopicByRang(Map<String, Object> parm) {
		Map<String, Object> resultMap = this.onlineDao.getCertConf(parm);
		Map<String, Object> parmMap = new HashMap<>();
		parmMap.put("CERT_TEXT", resultMap.get("cert_name"));
		parmMap.put("POST_TYPE", resultMap.get("cert_class"));
		parmMap.put("CERT_TYPE", resultMap.get("cert_id"));
		parmMap.put("SHARE_TYPE", resultMap.get("cert_type"));
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		parmMap.put("TYPE", "1");
		parmMap.put("NUMBER", Integer.parseInt((String) (resultMap.get("topic_a_numb").equals("")?"0":resultMap.get("topic_a_numb"))));
		list.addAll(this.onlineDao.getTopicByRang(parmMap));
		parmMap.put("TYPE", "2");
		parmMap.put("NUMBER", Integer.parseInt((String) (resultMap.get("topic_b_numb").equals("")?"0":resultMap.get("topic_b_numb"))));
		list.addAll(this.onlineDao.getTopicByRang(parmMap));
		parmMap.put("TYPE", "3");
		parmMap.put("NUMBER", Integer.parseInt((String) (resultMap.get("topic_c_numb").equals("")?"0":resultMap.get("topic_c_numb"))));
		list.addAll(this.onlineDao.getTopicByRang(parmMap));
		
		resultMap.put("rightNumb", 0);
		resultMap.put("wrongNumb", 0);
		resultMap.put("nowCurrent", 0);
		resultMap.put("userScore", 0);
		resultMap.put("LIST", list);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getUserInfo(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("USER_INFO", this.onlineDao.getUserInfo(parm));
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> updateUserInfo(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		this.onlineDao.updateUserInfo(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> pay(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		this.onlineDao.pay(parm);
		this.onlineDao.insertUserExam(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getUserCoin(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = this.onlineDao.getUserCoin(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> rechargeCoin(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		this.onlineDao.rechargeCoin(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> subCoin(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		this.onlineDao.subCoin(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getTopicExplain(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("EXPLAIN", this.onlineDao.getTopicExplain(parm));
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getOrderList(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("LIST", this.onlineDao.getOrderList(parm));
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> delTopic(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		this.onlineDao.delTopic(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	

	@Override
	public Map<String, Object> adminLogin(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap=this.onlineDao.adminLogin(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	
	@Override
	public Map<String, Object> getUserByPage(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", this.onlineDao.getUserByPage(parm));
//		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> addExamClass(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(this.onlineDao.addExamClass(parm)>0){
			resultMap.put(MESSAGE, "添加成功");
		}else{
			resultMap.put(MESSAGE, "已有此类试题");
		}
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getExamList(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("LIST", this.onlineDao.getExamList(parm));
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getAllExam(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("LIST", this.onlineDao.getAllExam(parm));
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> addExamXls(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		this.onlineDao.addExamXls(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getExamByClass(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("LIST", this.onlineDao.getExamByClass(parm));
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> getUserExamList(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> examList = this.onlineDao.getUserExamList(parm);
		for(int i=0;i<examList.size();i++){
			Map<String, Object> parmMap = new HashMap<String, Object>();
			parmMap.put("EXAM_ID", examList.get(i).get("examId"));
			examList.get(i).put("examInfo", this.onlineDao.getExamInfo(parmMap));
		}
		resultMap.put("LIST", examList);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getPayTopicByRang(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = this.onlineDao.getExamInfo(parm);
		resultMap.put("LIST", this.onlineDao.getPayTopicByRang(parm));
		resultMap.put("rightNumb", 0);
		resultMap.put("wrongNumb", 0);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> addAdv(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		this.onlineDao.addAdv(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> getAllAdv(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("LIST", this.onlineDao.getAllAdv(parm));
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> delAdv(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		this.onlineDao.delAdv(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> getShowAdv(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = this.onlineDao.getShowAdv(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> updataAdv(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		this.onlineDao.updataAdv(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> updataAdvShow(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		this.onlineDao.updataAdvShow(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getUserExam(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(this.onlineDao.getUserExam(parm)>0){
			resultMap.put(MESSAGE, "已购买此类试题，请到个人中心查看");
			resultMap.put("STATU", "1");
		}else{
			resultMap.put("STATU", "0");
		}
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Scheduled(cron = "0 */30 * * * ?")
	@Override
	public void delUserExam() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		this.onlineDao.delUserExam(parmMap);
	}

	@Override
	public Map<String, Object> updateAdvInfo(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		this.onlineDao.updateAdvInfo(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> getAdvInfo(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("advInfo", this.onlineDao.getAdvInfo(parm));
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> searchPayTopic(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("LIST", this.onlineDao.searchPayTopic(parm));
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> addSection(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		this.onlineDao.addSection(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> delSection(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		this.onlineDao.delSection(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> updataSection(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		this.onlineDao.updataSection(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> getSection(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("LIST", this.onlineDao.getSection(parm));
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> addSectionTopic(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		this.onlineDao.addSectionTopic(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> searchSectionTopic(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("LIST", this.onlineDao.searchSectionTopic(parm));
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> delSectionTopic(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		this.onlineDao.delSectionTopic(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> updateSectionTopicImage(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		this.onlineDao.updateSectionTopicImage(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> getSectionTopic(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("rightNumb", 0);
		resultMap.put("wrongNumb", 0);
		resultMap.put("nowCurrent", 0);
		resultMap.put("userScore", 0);
		resultMap.put("LIST", this.onlineDao.getSectionTopic(parm));
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> getSectionExplain(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("EXPLAIN", this.onlineDao.getSectionExplain(parm));
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

    @Override
    public Map<String, Object> getEnglishClass(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("LIST", this.onlineDao.getEnglishClass(parm));
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> addEnglishClass(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        this.onlineDao.addEnglishClass(parm);
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> deleteEnglishClass(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int classId= Integer.parseInt(String.valueOf(parm.get("id")));
        //先删课程
        this.onlineDao.deleteEnglishClass(parm);
        //再删章节
        this.onlineDao.deleteEnglishChapByClassId(classId);
        //最后删课程
        this.onlineDao.deleteEnglishQuestionByClassId(classId);
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> updateEnglishClass(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        this.onlineDao.updateEnglishClass(parm);
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> updateEnglishClassPic(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        this.onlineDao.updateEnglishClassPic(parm);
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> getAllEnglishClass() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("LIST", this.onlineDao.getAllEnglishClass());
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> getEnglishChap(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("LIST", this.onlineDao.getEnglishChap(parm));
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> addEnglishChap(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        this.onlineDao.addEnglishChap(parm);
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> deleteEnglishChap(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int chapId= Integer.parseInt(String.valueOf(parm.get("id")));
        //先删章节
        this.onlineDao.deleteEnglishChap(parm);
        //再删课程
        this.onlineDao.deleteEnglishQuestionByChapId(chapId);
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> updateEnglishChap(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        this.onlineDao.updateEnglishChap(parm);
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> getChapByClassId(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("LIST", this.onlineDao.getChapByClassId(parm));
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> addEnglishQuestion(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        this.onlineDao.addEnglishQuestion(parm);
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> getEnglishQuestion(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("LIST", this.onlineDao.getEnglishQuestion(parm));
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> updEnglishQuestion(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        this.onlineDao.updEnglishQuestion(parm);
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> delEnglishQuestion(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        this.onlineDao.delEnglishQuestion(parm);
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> updateEnglishQuestionPic(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        this.onlineDao.updateEnglishQuestionPic(parm);
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> updateEnglishQuestionVol(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        this.onlineDao.updateEnglishQuestionVol(parm);
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> getAllEnglishData(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map<String,Object>> listClass=this.onlineDao.getAllEnglishClassData(parm);
        resultMap.put("LIST",listClass);
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> getAllEnglishChapDataByClassId(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int classId= Integer.parseInt(String.valueOf(parm.get("classId")));
        resultMap.put("LIST", this.onlineDao.getAllEnglishChapDataByClassId(classId));
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> getAllEnglishQuestionDataByChapId(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int chapId= Integer.parseInt(String.valueOf(parm.get("chapId")));
		List<Map<String, Object>> listeningList = this.onlineDao.getAllEnglishQuestionDataByChapId(chapId, 1);
		List<Map<String, Object>> conversationList = this.onlineDao.getAllEnglishQuestionDataByChapId(chapId, 2);
//        resultMap.put("LIST", this.onlineDao.getAllEnglishQuestionDataByChapId(chapId));
		resultMap.put("listeningList", listeningList);
		resultMap.put("conversationList", conversationList);
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> getAllEnglishQuestionListenDataByChapId(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int chapId= Integer.parseInt(String.valueOf(parm.get("chapId")));
        resultMap.put("LIST", this.onlineDao.getAllEnglishQuestionListenDataByChapId(chapId));
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> getAllEnglishQuestionTalkDataByChapId(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int chapId= Integer.parseInt(String.valueOf(parm.get("chapId")));
        resultMap.put("LIST", this.onlineDao.getAllEnglishQuestionTalkDataByChapId(chapId));
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> addEnglishPayRecord(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        this.onlineDao.addEnglishPayRecord(parm);
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> getEnglishPayRecord(Map<String, Object> parm) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("LIST", this.onlineDao.getEnglishPayRecord(parm));
        resultMap.put(RESULT, SUCCESS);
        return resultMap;
    }

	@Override
	public Map<String, Object> getEnglishPayRecordCount(Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int englishPayRecordCount = this.onlineDao.getEnglishPayRecordCount(param);
		resultMap.put("ret",englishPayRecordCount);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> englishPay(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		parm.put("ORDER_ID",0);
		this.onlineDao.englishPay(parm);
		this.onlineDao.addEnglishPayRecord(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	@Override
	public int countUsers(Map<String, Object> parm) {
		return this.onlineDao.countUsers(parm);
	}

	@Override
	public Map<String, Object> englishRSA(Map<String, Object> parm){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String filePath = parm.get("filePath").toString();
		ConvertAudio.changeToWav(filePath,new StringBuffer(filePath.substring(0,filePath.indexOf('.')+1)).append("wav").toString());
		return resultMap;
	}

	@Override
	public Map<String, Object> getTopicById(Map<String, Object> parm) {
		Map<String, Object> resultMap;
		resultMap = onlineDao.getTopicById(parm);
		resultMap.put(RESULT, SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> editeTopicById(Map<String, Object> parm) {
		Map<String, Object> resultMap = new HashMap<>();
		int result = onlineDao.editeTopicById(parm);
		if(result > 0){
			resultMap.put(RESULT, SUCCESS);
		}else {
			resultMap.put(RESULT, FAIL);
		}
		return resultMap;
	}

}

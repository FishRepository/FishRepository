const util = require('../../utils/util.js');
var app = getApp();
const options = {
  duration: 60000,
  sampleRate: 8000,
  numberOfChannels: 1,
  encodeBitRate: 24000,
  format: 'mp3',
  frameSize: 50
};
Page({
  /**
   * 页面的初始数据
   */
  data: {
    resData: {},
    listeningList: {},
    conversationList: {},
    userInfo: {},
    hostUrl: app.globalData.hostUrl,
    topicType: ["", "判断题", "单选题", "阅读理解"],
    showRight: ["", "A", "B", "C", "D"],
    qtType:["","听力测试","会话测试"],
    // nowCurrent: 0,
    // wrongNumb: 0,
    // rightNumb: 0,
    listeningNowCurrent: 0,
    listeningWrongNumb: 0,
    listeningRightNumb: 0,
    convNowCurrent: 0,
    convWrongNumb: 0,
    convRightNumb: 0,
    qtTypeSwitch: 1,//1，听力，会话
    playRecordPng: "../../images/smile-face.png",
    RASstatus: false
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    that.setData({
      hostUrl: app.globalData.hostUrl,
      userInfo: app.globalData.userInfo,
      key: options.chapId + "-ZJ-" + "key"
    })
    wx.getSystemInfo({
      success: function (res) {
        that.setData({
          clientHeight: res.windowHeight
        });
      }
    });
    wx.showLoading({
      title: '正在加载试题...'
    }),
      /*获取缓存*/
      wx.getStorage({
        key: that.data.key,
        success: function (res) {
          wx.hideLoading();
          that.setData({
            resData: res.data
          });
          that.setData({
            listeningNowCurrent: that.data.listeningNowCurrent,
            convNowCurrent: that.data.convNowCurrent
          })
        },
        fail: function () {
          wx.request({
            url: app.globalData.hostUrl + '/admin.do?method=getAllEnglishQuestionDataByChapId',
            data: {
              "chapId": options.chapId
            },
            success: function (res) {
              wx.hideLoading();
              if (res.data.listeningList.length == 0 && res.data.conversationList.length == 0) {
                wx.showToast({
                  title: '该章节暂无试题',
                })
              } else {
                wx.setStorage({
                  key: that.data.key,
                  data: res.data,
                  success: function () {
                    console.log("添加缓存成功");
                  }
                })
              }
              that.setData({
                resData: res.data
              });
            }
          })
        }
      })
  },
  getOurUserInfo: function () {
    var that = this;
    var openid = app.globalData.openid;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=getUserCoin', //获取平台用户解析币
      method: 'get',
      data: {
        'WID': openid
      },
      success: function (d) {
        that.setData({
          userInfo: {
            coin: d.data.user_coin
          }
        })
      }
    })
  },
  selectOption: function (e) {
    var that = this;
    var index = this.constantDataIndex();
    that.setData({
      topicResult: e.detail.value
    })
    var param = {};
    var chooseItem = this.constantDataChoose("chooseItem",index);
    var choose1 = this.constantDataChoose("choose1", index);
    var choose2 = this.constantDataChoose("choose2", index);
    var choose3 = this.constantDataChoose("choose3", index);
    var choose4 = this.constantDataChoose("choose4", index);
    param[chooseItem] = that.data.topicResult;
    switch (that.data.topicResult) {
      case "1":
        param[choose1] = true;
        param[choose2] = false;
        param[choose3] = false;
        param[choose4] = false;
        break;
      case "2":
        param[choose1] = false;
        param[choose2] = true;
        param[choose3] = false;
        param[choose4] = false;
        break;
      case "3":
        param[choose1] = false;
        param[choose2] = false;
        param[choose3] = true;
        param[choose4] = false;
        break;
      case "4":
        param[choose1] = false;
        param[choose2] = false;
        param[choose3] = false;
        param[choose4] = true;
        break;
      default:
        break
    }
    that.setData(param);
  },

  swiperChange: function (e) {
    var that = this;
    var param = {};
    var qtTypeSwitch = that.data.qtTypeSwitch;
    switch (qtTypeSwitch){
      case 1:
        var listeningNowCurrent = "resData.listeningNowCurrent";
        param[listeningNowCurrent] = e.detail.current;
        that.setData(param);
        that.setData({
          listeningNowCurrent: e.detail.current
        });
        wx.stopBackgroundAudio();
        for (var t = 0; t < that.data.resData.listeningList.length; t++) {
          var isPlayTTT = "resData.listeningList[" + t + "].isPlay";
          param[isPlayTTT] = false;
          that.setData(param);
        };
        break;
      case 2:
        var convNowCurrent = "resData.convNowCurrent";
        param[convNowCurrent] = e.detail.current;
        that.setData(param);
        that.setData({
          convNowCurrent: e.detail.current
        });
        wx.stopBackgroundAudio();
        for (var t = 0; t < that.data.resData.conversationList.length; t++) {
          var isPlayTTT = "resData.conversationList[" + t + "].isPlay";
          param[isPlayTTT] = false;
          that.setData(param);
        };
        break;
    }
    // var nowCurrent = "resData.nowCurrent";
    // param[nowCurrent] = e.detail.current;
    // that.setData(param);
    // that.setData({
    //   nowCurrent: e.detail.current
    // });
    // wx.stopBackgroundAudio();
    // for (var t = 0; t < that.data.resData.LIST.length; t++) {
    //   var isPlayTTT = "resData.LIST[" + t + "].isPlay";
    //   param[isPlayTTT] = false;
    //   that.setData(param);
    // };
  },

  showRight: function () {
    var that = this;
    var index = this.constantDataIndex();
    var list = this.constantDataList();
    if (list[index].chooseItem == "" || !list[index].chooseItem) {
      wx.showToast({
        title: '请选择一个选项!'
      })
      return;
    }
    var param = {};
    var showRight = this.constantDataChoose("showRight", index);
    var btnDisabled = this.constantDataChoose("btnDisabled", index);
    var radioDisabled = this.constantDataChoose("radioDisabled", index);
    param[showRight] = '1';
    param[btnDisabled] = true;
    param[radioDisabled] = true;
    that.setData(param);
    var rn = this.constantDataRight() > 0 ? this.constantDataRight() : 0;
    var wn = this.constantDataWrong() > 0 ? this.constantDataWrong() : 0;
    if (list[index].RIGHT_OPTION == that.data.topicResult) {
      var param = {};
      var rightNumb = that.data.qtTypeSwitch == 1 ? "resData.listeningRightNumb" : "resData.convRightNumb";
      param[rightNumb] = rn + 1;
      var isWrong = this.constantDataChoose("isWrong", index);
      param[isWrong] = false;
      that.setData(param);
    } else {
      var param = {};
      var wrongNumb = that.data.qtTypeSwitch == 1 ? "resData.listeningWrongNumb" : "resData.convWrongNumb";
      param[wrongNumb] = wn + 1;
      var isWrong = this.constantDataChoose("isWrong", index);
      param[isWrong] = true;
      that.setData(param);
    }
    wx.setStorageSync(that.data.key, that.data.resData)
  },
  prev: function () {
    var that = this;
    var index = this.constantDataIndex();
    if (index > 0) {
      var qtTypeSwitch = that.data.qtTypeSwitch;
      switch (qtTypeSwitch){
        case 1:
          that.setData({
            listeningNowCurrent: this.constantDataIndex - 1
          });
          break;
        case 2:
          that.setData({
            convNowCurrent: this.constantDataIndex - 1
          });
          break;
      }
    };
    wx.stopBackgroundAudio();
    var param = {};
    var list = this.constantDataList();
    for (var t = 0; t < list.length; t++) {
      var isPlayTTT = this.constantDataIsPlay(t);
      param[isPlayTTT] = false;
      that.setData(param);
    };
  },
  next: function () {
    var that = this;
    var index = this.constantDataIndex();
    var list = this.constantDataList();
    if (index < list.length - 1) {
      var qtTypeSwitch = that.data.qtTypeSwitch;
      switch (qtTypeSwitch) {
        case 1:
          that.setData({
            listeningNowCurrent: this.constantDataIndex + 1
          });
          break;
        case 2:
          that.setData({
            convNowCurrent: this.constantDataIndex + 1
          });
          break;
      }
    };
    wx.stopBackgroundAudio();
    var param = {};
    for (var t = 0; t < list.length; t++) {
      var isPlayTTT = this.constantDataIsPlay(t);
      param[isPlayTTT] = false;
      that.setData(param);
    };
  },
  showQtExplain: function () {
    var that = this;
    var param = {};
    var index = this.constantDataIndex();
    var showExplain = this.constantDataChoose("showExplain", index);
    // "resData.LIST[" + that.data.nowCurrent + "].showExplain";
    param[showExplain] = '1';
    that.setData(param);
  },
  imgPre(event){
    var preview = event.currentTarget.dataset.preview;
    var urls = [];
    urls[0] = preview;
    var cur = event.currentTarget.dataset.cur;
    wx.previewImage({
      current : cur,
      urls: urls
    })
  },
  previewImage: function (e) {
    var current = e.target.dataset.src;
    wx.previewImage({
      current: current, // 当前显示图片的http链接  
      urls: [current] 
    })
  },
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var that = this;
    that.getOurUserInfo();
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    wx.stopBackgroundAudio();
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  /**
   * 播放音频
   */
  playVoice:function(){
    var that = this;
    var index = this.constantDataIndex();
    var list = this.constantDataList();
    wx.playBackgroundAudio({
      dataUrl: app.globalData.hostUrl + list[index].VO_URL,
    });
    var param = {};
    var isPlay = this.constantDataIsPlay(index);
    param[isPlay]=true;
    that.setData(param);
    for (var t = 0; t < list.length;t++) {
      if(index==t){
        continue;
      }
      var isPlayTTT = this.constantDataIsPlay(t);
      param[isPlayTTT] = false;
      that.setData(param);
    }
  },

  /**
   * 暂停播放
   */
  stopVoice:function(){
    var that = this;
    var index = this.constantDataIndex();
    wx.pauseBackgroundAudio();
    var param = {};
    var isPlay = this.constantDataIsPlay(index);
    param[isPlay] = false;
    that.setData(param);
  },

  /**
   * 重新播放
   */
  replayVoice:function(){
    var that = this;
    var index = this.constantDataIndex();
    var list = this.constantDataList();
    wx.stopBackgroundAudio();
    wx.playBackgroundAudio({
      dataUrl: app.globalData.hostUrl + list[index].VO_URL,
    });
    var param = {};
    var isPlay = this.constantDataIsPlay(index);
    param[isPlay] = true;
    that.setData(param);
    for (var t = 0; t < list.length; t++) {
      if (index == t) {
        continue;
      }
      var isPlayTTT = this.constantDataIsPlay(t);
      param[isPlayTTT] = false;
      that.setData(param);
    }
  },

  /**
   * 播放用户录音
   */
  playRecord:function(){
    this.stopVoice();
    var index = this.constantDataIndex();
    var param = {};
    var that = this;
    var list = this.constantDataList();
    wx.playBackgroundAudio({
      dataUrl: list[index].recordUrl,
    });
    var recordIsPlay = this.constantDataChoose("recordIsPlay", index);
    param[recordIsPlay] = true;
    that.setData(param);
    for (var t = 0; t < list.length; t++) {
      if (index == t) {
        continue;
      }
      var recordIsPlayTTT = this.constantDataChoose("recordIsPlay", index);
      param[recordIsPlayTTT] = false;
      that.setData(param);
    }
  },
  /**
   * 暂停用户录音
   */
  stopPlayRecord: function () {

  },
  /**
   * 切换题目类型
   */
  qtTypeSwitch:function(e){
    var that = this;
    var list = this.constantDataList();
    var clicktype = e.target.dataset.clicktype*1;
    if (that.data.qtTypeSwitch === clicktype){
      return;
    }
    wx.stopBackgroundAudio();
    var param = {};
    for (var t = 0; t < list.length; t++) {
      var isPlayTTT = this.constantDataIsPlay(t);
      param[isPlayTTT] = false;
      that.setData(param);
    };
    that.setData({
      qtTypeSwitch: clicktype
    })
  },
  record: function (){
    var that = this;
    this.stopVoice();
    var index = this.constantDataIndex();
    var param = {};
    var isRecord = this.constantDataChoose("isRecord",index);
    param[isRecord] = true;
    that.setData(param);
    this.constantRecorderStart();
  },
  stopRecord: function (){
    var that = this;
    this.stopVoice();
    var index = this.constantDataIndex();
    var param = {};
    var isRecord = this.constantDataChoose("isRecord", index);
    param[isRecord] = false;
    that.setData(param);
    this.constantRecorderStop();
  },
  constantDataIndex: function () {
    var that = this;
    return that.qtTypeSwitch == 1 ? that.data.listeningNowCurrent : that.data.convNowCurrent;
  },
  constantDataList: function() {
    var that = this;
    return that.data.qtTypeSwitch == 1 ? that.data.resData.listeningList : that.data.resData.conversationList;
  },
  constantDataIsPlay: function(t) {
    var that = this;
    return that.data.qtTypeSwitch == 1 ? "resData.listeningList[" + t + "].isPlay" :  "resData.conversationList[" + t + "].isPlay";
  },
  constantDataChoose: function(choose,index){
    var that = this;
    return that.data.qtTypeSwitch == 1 ? "resData.listeningList[" + index + "]." + choose :
    "resData.conversationList[" + index + "]." + choose;
  },
  constantDataRight: function(){
    var that = this;
    return that.data.qtTypeSwitch == 1 ? that.data.resData.listeningRightNumb : that.data.resData.convRightNumb;
  },
  constantDataWrong: function () {
    var that = this;
    return that.data.qtTypeSwitch == 1 ? that.data.resData.listeningWrongNumb : that.data.resData.convWrongNumb;
  },
  constantRecorderStart: function () {
    var that = this;
    const recorderManager = this.constantRecorderManager();
    recorderManager.start(options);
  },
  constantRecorderStop: function () {
    var that = this;
    const recorderManager = wx.getRecorderManager();
    recorderManager.stop();
  },
  constantRecorderManager: function () {
    const recorderManager = wx.getRecorderManager();
    recorderManager.onStart(() => {
      console.log('recorder start');
    });
    recorderManager.onResume(() => {
      console.log('recorder resume');
    });
    recorderManager.onPause(() => {
      console.log('recorder pause');
    });
    recorderManager.onStop((res) => {
      console.log('recorder stop', res);
      const { tempFilePath } = res;
      if (tempFilePath){
        const that = this;
        var index = this.constantDataIndex();
        var param = {};
        var recordUrl = this.constantDataChoose("recordUrl", index);
        param[recordUrl] = tempFilePath;
        that.setData(param);
      }
      this.uploadFileToServer();
    });
    recorderManager.onFrameRecorded((res) => {
      const { frameBuffer } = res
      console.log('frameBuffer.byteLength', frameBuffer.byteLength)
    });
    return recorderManager;
  },
  uploadFileToServer: function () {
    const that = this;
    wx.showLoading({
      title: '正在评分',
    })
    var urls = app.globalData.hostUrl + "/admin.do?method=englishASR";
    console.log(that.data.recordFile);
    var preContext = this.constantDataList()[this.constantDataIndex()].QT_CONTENT;
    //预设param
    var index = this.constantDataIndex();
    var param = {};
    var RASstatus = this.constantDataChoose("RASstatus", index);
    var itemScore = this.constantDataChoose("itemScore", index);
    wx.uploadFile({
      url: urls,
      filePath: that.data.recordFile,
      name: 'file',
      formData:{
        fileName: 'file' + app.globalData.openid+".mp3",
        preContext: preContext
      },
      header: {
        'content-type': 'multipart/form-data'
      },
      success: function (res) {
        console.log("识别结果: "+res.data);
        var result = JSON.parse(res.data);
        if (result.ret==1){
          param[RASstatus] = true;
          param[itemScore] = result.sim;
          that.setData(param);
        }
        wx.hideLoading();
      },
      fail: function (res) {
        wx.hideLoading();
        console.log(res);
        wx.showModal({
          title: '提示',
          content: "网络请求失败，请确保网络是否正常",
          showCancel: false,
          success: function (res) {

          }
        });
        wx.hideToast();
      }
    });
  }
})
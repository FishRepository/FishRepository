var app = getApp();
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
    qtTypeSwitch: 1//1，听力，会话
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
              if (res.data.listeningList.length == 0) {
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
    var index = that.data.nowCurrent;
    that.setData({
      topicResult: e.detail.value
    })
    var param = {};
    var chooseItem = "resData.LIST[" + index + "].chooseItem";
    var choose1 = "resData.LIST[" + index + "].choose1";
    var choose2 = "resData.LIST[" + index + "].choose2";
    var choose3 = "resData.LIST[" + index + "].choose3";
    var choose4 = "resData.LIST[" + index + "].choose4";
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
    var index = that.data.nowCurrent;
    if (that.data.resData.LIST[index].chooseItem == "" || !that.data.resData.LIST[index].chooseItem) {
      wx.showToast({
        title: '请选择一个选项!'
      })
      return;
    }
    var param = {};
    var showRight = "resData.LIST[" + that.data.nowCurrent + "].showRight";
    var btnDisabled = "resData.LIST[" + that.data.nowCurrent + "].btnDisabled";
    var radioDisabled = "resData.LIST[" + that.data.nowCurrent + "].radioDisabled";
    param[showRight] = '1';
    param[btnDisabled] = true;
    param[radioDisabled] = true;
    that.setData(param);
    var rn = that.data.resData.rightNumb > 0 ? that.data.resData.rightNumb:0;
    var wn = that.data.resData.wrongNumb > 0 ? that.data.resData.wrongNumb : 0;
    if (that.data.resData.LIST[index].RIGHT_OPTION == that.data.topicResult) {
      var param = {};
      var rightNumb = "resData.rightNumb";
      param[rightNumb] = rn + 1;
      var isWrong = "resData.LIST[" + index + "].isWrong";
      param[isWrong] = false;
      that.setData(param);
    } else {
      var param = {};
      var wrongNumb = "resData.wrongNumb";
      param[wrongNumb] = wn + 1;
      var isWrong = "resData.LIST[" + index + "].isWrong";
      param[isWrong] = true;
      that.setData(param);
    }
    wx.setStorageSync(that.data.key, that.data.resData)
  },
  prev: function () {
    var that = this;
    var index = that.data.nowCurrent;
    if (index > 0) {
      that.setData({
        nowCurrent: that.data.nowCurrent - 1
      })
    };
    wx.stopBackgroundAudio();
    var param = {};
    for (var t = 0; t < that.data.resData.LIST.length; t++) {
      var isPlayTTT = "resData.LIST[" + t + "].isPlay";
      param[isPlayTTT] = false;
      that.setData(param);
    };
  },
  next: function () {
    var that = this;
    var index = that.data.nowCurrent;
    if (index < that.data.resData.LIST.length - 1) {
      that.setData({
        nowCurrent: that.data.nowCurrent + 1
      })
    };
    wx.stopBackgroundAudio();
    var param = {};
    for (var t = 0; t < that.data.resData.LIST.length; t++) {
      var isPlayTTT = "resData.LIST[" + t + "].isPlay";
      param[isPlayTTT] = false;
      that.setData(param);
    };
  },
  showQtExplain: function () {
    var that = this;
    var param = {};
    var showExplain = "resData.LIST[" + that.data.nowCurrent + "].showExplain";
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
    var index = that.data.nowCurrent;
    wx.playBackgroundAudio({
      dataUrl: app.globalData.hostUrl+that.data.resData.LIST[index].VO_URL,
    });
    var param = {};
    var isPlay = "resData.LIST[" + index + "].isPlay";
    param[isPlay]=true;
    that.setData(param);
    for (var t=0; t < that.data.resData.LIST.length;t++) {
      if(index==t){
        continue;
      }
      var isPlayTTT = "resData.LIST[" + t + "].isPlay";
      param[isPlayTTT] = false;
      that.setData(param);
    }
  },

  /**
   * 暂停播放
   */
  stopVoice:function(){
    var that = this;
    var index = that.data.nowCurrent;
    wx.pauseBackgroundAudio();
    var param = {};
    var isPlay = "resData.LIST[" + index + "].isPlay";
    param[isPlay] = false;
    that.setData(param);
  },

  /**
   * 重新播放
   */
  replayVoice:function(){
    var that = this;
    var index = that.data.nowCurrent;
    wx.stopBackgroundAudio();
    wx.playBackgroundAudio({
      dataUrl: app.globalData.hostUrl + that.data.resData.LIST[index].VO_URL,
    });
    var param = {};
    var isPlay = "resData.LIST[" + index + "].isPlay";
    param[isPlay] = true;
    that.setData(param);
    for (var t=0; t < that.data.resData.LIST.length; t++) {
      if (index == t) {
        continue;
      }
      var isPlayTTT = "resData.LIST[" + t + "].isPlay";
      param[isPlayTTT] = false;
      that.setData(param);
    }
  },
  /**
   * 切换题目类型
   */
  qtTypeSwitch:function(e){
    var that = this;
    var clicktype = e.target.dataset.clicktype*1;
    if (that.data.qtTypeSwitch === clicktype){
      console.log("点击灰色");
      return;
    }
    that.setData({
      qtTypeSwitch: clicktype
    })
  }
})
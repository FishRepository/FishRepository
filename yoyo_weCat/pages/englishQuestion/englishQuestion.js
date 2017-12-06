var app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    resData: {},
    userInfo: {},
    hostUrl: app.globalData.hostUrl,
    topicType: ["", "判断题", "单选题", "阅读理解"],
    showRight: ["", "A", "B", "C", "D"],
    qtType:["","听力测试","会话测试"],
    nowCurrent: 0,
    wrongNumb: 0, 
    rightNumb: 0,
    showExplain:false
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
            nowCurrent: that.data.nowCurrent
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
              if (res.data.LIST.length == 0) {
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
    var nowCurrent = "resData.nowCurrent";
    param[nowCurrent] = e.detail.current;
    that.setData(param);
    that.setData({
      nowCurrent: e.detail.current
    })
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
    }

  },
  next: function () {
    var that = this;
    var index = that.data.nowCurrent;
    if (index < that.data.resData.LIST.length - 1) {
      that.setData({
        nowCurrent: that.data.nowCurrent + 1
      })
    }
  },
  showExplain: function () {
    var that = this;
    var index = that.data.nowCurrent;
    var topicId = that.data.resData.LIST[index].topicId;
    var payType = that.data.payType;
    if (that.data.userInfo.coin != 0) {
      if (that.data.resData.LIST[index].showExplain == '0') {
        wx.showModal({
          title: '提示',
          content: '查看解析将花费1解析币',
          success: function (res) {
            if (res.confirm) {
              wx.request({
                url: app.globalData.hostUrl + '/admin.do?method=getTopicExplain',
                data: {
                  "topicId": topicId,
                  "payType": payType
                },
                method: "post",
                header: {
                  "Content-Type": "application/x-www-form-urlencoded"
                },
                success: function (res) {
                  if (res.data.RESULT == "SUCCESS") {

                    var param = {};
                    var showExplain = "resData.LIST[" + that.data.nowCurrent + "].showExplain";
                    param[showExplain] = '1';
                    var explain = "resData.LIST[" + that.data.nowCurrent + "].explain";
                    if (res.data.EXPLAIN.topicExplain == "") {
                      param[explain] = "此题没有解析";
                      that.setData(param);
                    } else {
                      param[explain] = res.data.EXPLAIN.topicExplain;
                      that.setData(param);
                      that.subCoin()
                    }
                  } else {
                    wx.showToast({
                      title: '获取解析失败，请稍后再尝试',
                    })
                  }
                }
              })
            } else if (res.cancel) {
              console.log('用户点击取消')
            }
          }
        })
      }
    } else {
      wx.showModal({
        title: '提示',
        content: '解析币不足，去充值？',
        success: function (res) {
          if (res.confirm) {
            wx.navigateTo({
              url: '/pages/pay/pay?back=1',
            })
          } else (
            console.log('取消充值')
          )
        }
      })
    }
  },
  // submit: function () {
  //   var that = this;
  //   var pass = that.data.resData.poss_score;
  //   var userScore = that.data.resData.userScore;
  //   var totalScore = that.data.resData.total_score;
  //   var totleNumb = that.data.resData.LIST.length;
  //   var wrongNumb = that.data.wrongNumb + that.data.rightNumb;
  //   var residueNumb = totleNumb - wrongNumb;
  //   if (residueNumb > 0) {
  //     wx.showModal({
  //       title: '提示',
  //       content: '您还有' + residueNumb + '未做，确定提交？',
  //       success: function (res) {
  //         if (res.confirm) {
  //           wx.navigateTo({
  //             url: '/pages/result/result?passScore=' + pass + '&userScore=' + userScore
  //           })
  //         } else {

  //         }
  //       }
  //     })
  //   }
  // },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
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

  }
  
})
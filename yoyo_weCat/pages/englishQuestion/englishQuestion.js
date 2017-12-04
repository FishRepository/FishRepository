var app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    resData: {},
    userInfo: {},
    showModel: false,
    animationData: {},
    hostUrl: "",
    topicType: ["", "判断题", "单选题", "阅读理解"],
    showType: ["", true, false],
    showRight: ["", "A", "B", "C", "D"],
    nowCurrent: 0,
    payType: "",
    wrongNumb: 0,
    rightNumb: 0,
    timeHour: "00",
    timeMin: "00",
    timeSec: "00",
    article: "",
    topicResult: "",
    userScore: 0,
    showWrong: false,
    wrongTip: "wrongTip"
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
            nowCurrent: that.data.resData.nowCurrent
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
  countDown: function (seconds) {
    var that = this;
    var time = setInterval(function () {
      var day = 0, hour = 0, minute = 0, second = 0;
      if (seconds > 0) {
        day = Math.floor(seconds / (60 * 60 * 24));
        hour = Math.floor(seconds / (60 * 60)) - (day * 24);
        minute = Math.floor(seconds / 60) - (day * 24 * 60) - (hour * 60);
        second = Math.floor(seconds) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
        if (minute <= 9) minute = '0' + minute;
        if (second <= 9) second = '0' + second;
        that.setData({
          timeHour: hour,
          timeMin: minute,
          timeSec: second
        })
        seconds--;
        that.setData({
          resTime: seconds
        })
        if (seconds == 0) {
          clearTimeout(time)
        }
      } else {
        var pass = that.data.resData.poss_score;
        var userScore = that.data.userScore;
        wx.navigateTo({
          url: '/pages/result/result?passScore=' + pass + '&userScore=' + userScore
        })
      }
    }, 1000)
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
    switch (that.data.topicResult) {
      case "1":
        param[choose1] = "wrongTip";
        break;
      case "2":
        param[choose2] = "wrongTip";
        break;
      case "3":
        param[choose3] = "wrongTip";
        break;
      case "4":
        param[choose4] = "wrongTip";
        break;
      default:
        break
    }
    param[chooseItem] = that.data.topicResult;
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
    var topicDisable = "resData.LIST[" + that.data.nowCurrent + "].topicDisable";
    var btnDisabled = "resData.LIST[" + that.data.nowCurrent + "].btnDisabled";
    param[showRight] = '1';
    param[topicDisable] = '1';
    param[btnDisabled] = true;
    that.setData(param);
    if (that.data.resData.LIST[index].RIGHT_OPTION == that.data.topicResult) {
      var param = {};
      var wrongTip = "resData.LIST[" + index + "].wrongTip";
      var userScore = "resData.userScore";
      var rightNumb = "resData.rightNumb";
      param[rightNumb] = that.data.resData.rightNumb + 1;
      param[userScore] = that.data.userScore;
      param[wrongTip] = "rightTip";
      switch (that.data.resData.LIST[index].QT_DIF) {
        case "1":
          param[userScore] = that.data.resData.userScore + parseFloat(that.data.resData.topic_a_score);
          break;
        case "2":
          param[userScore] = that.data.resData.userScore + parseFloat(that.data.resData.topic_b_score);
          break;
        case "3":
          param[userScore] = that.data.resData.userScore + parseFloat(that.data.resData.topic_c_score);
          break;
        default:
          break;
      }
      that.setData(param);
    } else {
      var param = {};
      var wrongNumb = "resData.wrongNumb";
      var isWrong = "resData.LIST[" + index + "].isWrong";
      var wrongTip = "resData.LIST[" + index + "].wrongTip";
      param[wrongNumb] = that.data.resData.wrongNumb + 1;
      param[isWrong] = true;
      param[wrongTip] = "wrongTip";
      that.setData(param);
    }
    wx.setStorageSync(that.data.key, that.data.resData)
  },
  showWrong: function () {
    var that = this;
    that.setData({
      showWrong: true
    })
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
  submit: function () {
    var that = this;
    var pass = that.data.resData.poss_score;
    var userScore = that.data.resData.userScore;
    var totalScore = that.data.resData.total_score;

    var totleNumb = that.data.resData.LIST.length;
    var wrongNumb = that.data.resData.wrongNumb + that.data.resData.rightNumb;
    var residueNumb = totleNumb - wrongNumb;
    if (residueNumb > 0) {
      wx.showModal({
        title: '提示',
        content: '您还有' + residueNumb + '未做，确定提交？',
        success: function (res) {
          if (res.confirm) {
            wx.navigateTo({
              url: '/pages/result/result?passScore=' + pass + '&userScore=' + userScore
            })
          } else {

          }
        }
      })
    }
  },
  closeWrong: function () {
    var that = this;
    that.setData({
      showWrong: false
    })
  },
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
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    var that = this;
    clearTimeout(that.countDown.time)
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
// pages/payTest/payTest.js
var util = require('../../utils/util.js');
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
  
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    that.setData({
      postType: options.postType,
      certType: options.certType
    });
    that.getExamList();
  },
  getExamList:function(){
    var that = this;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=getExamList',
      data:{
        postType: that.data.postType,
        certType: that.data.certType
      },
      success:function(res){
        if (res.data.LIST<=0){
          wx.showModal({
            title: '提示',
            content: '暂无此类试题！',
            success:function(){
              wx.navigateBack({
                delta: 1
              })
            }
          })
        }else{
          that.setData({
            LIST: res.data.LIST
          })
        }
      }
    })
  },
  paySub: function (e) {
    var that = this;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=getUserExam',
      data: {
        openid: app.globalData.openid,
        examId: e.currentTarget.dataset.id
      },
      success: function (res) {
        if (res.data.STATU == "1") {
          wx.showModal({
            title: '提示',
            content: res.data.MESSAGE,
            success: function (res) {
              if (res.confirm) {
                wx.redirectTo({
                  url:"/pages/userExam/userExam"
                })
              } else {
                
              }
            }
          })
        }else{
          var oType = 2;
          wx.request({
            url: app.globalData.hostUrl + '/admin.do?method=payMoney',
            data: {
              payMoney: e.currentTarget.dataset.pay,
              payClass: app.globalData.orderType[oType],
              openid: app.globalData.openid
            },
            header: {
              'content-type': 'application/x-www-form-urlencoded'
            },
            method: "post",
            success: function (d) {
              if (d.data.result.prepay_id) {
                that.requestPayment(d.data.result.prepay_id, d.data.orderNumber, oType, d.data.payMoney, d.data.orderTime, e.currentTarget.dataset.id);
              }
            }
          })
        }
      }
    })
  },
  checkExam: function (examId) {
    var that = this;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=getUserExam',
      data: {
        openid: app.globalData.openid,
        examId: examId
      },
      success: function (res) {
        if (res.data.STATU == "1") {
          wx.showModal({
            title: '提示',
            content: res.data.MESSAGE,
            success: function (res) {
              if (res.confirm) {
                wx.navigateBack({
                  delta: 1
                })
              } else {
                wx.navigateBack({
                  delta: 1
                })
              }
            }
          })
        }
      }
    })
  },
  requestPayment: function (prepayId, orderNumber, orderType, payMoney, orderTime, examId) {
    var that = this;
    var timeStamp = new Date().getTime();
    timeStamp = timeStamp.toString();
    var nonceStr = util.getRandom25String();
    var openid = app.globalData.openid;
    var appid = app.globalData.appid;

    var paySignStr = "appId=" + app.globalData.appid +
      "&nonceStr=" + nonceStr +
      "&package=prepay_id=" + prepayId +
      "&signType=MD5" +
      "&timeStamp=" + timeStamp +
      "&key=" + app.globalData.key;
    var paySign = util.hex_md5(paySignStr);
    wx.requestPayment({
      appId: appid,
      timeStamp: timeStamp,
      nonceStr: nonceStr,
      package: "prepay_id=" + prepayId,
      signType: 'MD5',
      paySign: paySign,
      success: function (res) {
        console.log(res)
        var orderState = "1";
        that.recordOrder(orderState, orderType, payMoney, orderNumber, orderTime, examId);
        wx.redirectTo({
          url: '/pages/userExam/userExam',
          success: function (res) { },
          fail: function (res) { },
          complete: function (res) { },
        })
      },
      fail: function (res) {
        console.log(res)
      }
    })
  },
  recordOrder: function (orderState, orderType, orderMoney, orderNumber, orderTime, examId) {
    var that = this;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=pay',
      method: "post",
      data: {
        order_money: orderMoney,
        order_type: orderType,
        order_state: orderState,
        order_number: orderNumber,
        order_time: orderTime,
        openid: app.globalData.openid,
        examId: examId,
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: function (d) {

      }
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
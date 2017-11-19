var util = require('../../utils/util.js');
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo:{},
    payMoney:"",
    payCoin:"",
    canPay:true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    that.setData({
      userInfo: app.globalData.userInfo,
      back: options.back
    })
    that.getUserIcon();
  },

  paySub: function () {
    var that = this;
    var oType = 1;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=payMoney',
      data: {
        payMoney: that.data.payMoney*100,
        payClass: app.globalData.orderType[oType],
        openid: app.globalData.openid
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      method: "post",
      success: function (d) {
        if (d.data.result.prepay_id) {
          that.requestPayment(d.data.result.prepay_id, d.data.orderNumber, oType, that.data.payMoney, d.data.orderTime);
        }
      }
    })
  },
  requestPayment: function (prepayId, orderNumber, orderType, payMoney, orderTime) {
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
        var orderState = "1";
        that.recordOrder(orderState, orderType, payMoney, orderNumber, orderTime);
        that.changeCoin();
        wx.navigateBack({
          delta: 1
        })
      },
      fail: function (res) {
        console.log(res)
      }
    })
  },

  recordOrder: function (orderState, orderType, orderMoney, orderNumber, orderTime) {
    var that = this;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=pay',
      method: "post",
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      data: {
        order_money: orderMoney,
        order_type: orderType,
        order_state: orderState,
        order_number: orderNumber,
        order_time: orderTime,
        openid: app.globalData.openid
      },
      success: function () {
        that.getUserIcon();
      }
    })
  },
  changeCoin:function(){
    var that = this;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=rechargeCoin',
      data:{
        WID: app.globalData.openid,
        COIN: that.data.payCoin
      },
      method:"post",
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success:function(res){
        if(res.data.RESULT=="SUCCESS"){
          that.getUserIcon();
        }
      }
    })
  },
  moneyKeyInput:function(e){
    if (e.detail.value < 0.5 || e.detail.value%0.5!=0){
      this.setData({
        canPay: true
      })
    }else{
      this.setData({
        canPay: false
      })
    }
    var money = e.detail.value;
    var coin = money*2;
    this.setData({
      payMoney: money,
      payCoin:coin
    })
  },
  coinKeyInput:function(e){
    if (e.detail.value < 1 || e.detail.value % 1 != 0) {
      this.setData({
        canPay: true
      })
    } else {
      this.setData({
        canPay: false
      })
    }
    var coin = e.detail.value;
    var money = coin / 2;
    this.setData({
      payMoney: money,
      payCoin: coin
    })
  },
  getUserIcon:function(){
    var that = this;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=getUserCoin',
      data:{
        WID: app.globalData.openid
      },
      success:function(res){
        that.setData({
          userInfo:{
            coin: res.data.user_coin
          }
        })
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
    
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    var that = this;
    that.getUserIcon();
    wx.stopPullDownRefresh()
  }
})
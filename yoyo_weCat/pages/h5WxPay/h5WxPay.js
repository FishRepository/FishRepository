var util = require('../../utils/util.js');
var app = getApp();
Page({
  data: {
    paySrc: '',
    pay: 0,
    title: '',
    openid: ''
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var src = app.globalData.hostUrl + '/admin.do?method=wxPay&title=' + options.title + '&pay=' + options.pay / 100 + '&openid=' + options.openid;
    var that = this;
    that.setData({
      paySrc: src,
      pay: options.payMoney / 100,
      title: options.title,
      openid: options.openid
    })
   
  }

})

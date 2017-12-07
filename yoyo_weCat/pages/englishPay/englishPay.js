var app = getApp();
Page({
  data: {
    LIST: [],
    orderState:["未支付","支付成功"],
    imgUrl: app.globalData.hostUrl
  },
  onLoad: function (options) {
    var that = this;
    var openid = app.globalData.openid;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=getEnglishPayRecord',
      data: {
        "wid": openid
      },
      success: function (res) {
        if (res.data.LIST <= 0) {
          wx.showModal({
            title: '提示',
            content: '暂无英语课程购买记录！',
            success: function () {
              wx.navigateBack({
                delta: 1
              })
            }
          })
        } else {
          that.setData({
            LIST: res.data.LIST
          })
        }
      }
    })
  }
})
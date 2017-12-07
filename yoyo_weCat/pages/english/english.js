var app = getApp();
Page({
  data: {
    LIST: [],
    payType: ["免费", "付费"],
    imgUrl: app.globalData.hostUrl
  },
  onLoad: function (options) {
    var that = this;
    this.title = options.title;
    var openid = app.globalData.openid;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=getAllEnglishClassData',
      data: {
        "WID": openid
      },
      success: function (res) {
        if (res.data.LIST <= 0) {
          wx.showModal({
            title: '提示',
            content: '暂无相关英语课程！',
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
  },
  viewDetail: function (e) {
    var ds = e.currentTarget.dataset;
    var isPay = ds.ispay;
    var payOver = ds.payover;
    if (isPay==0){
      wx.navigateTo({
        url: '../englishChap/englishChap?classId=' + ds.id
      })
    }else{
      if (payOver != undefined && payOver>=0){
        wx.navigateTo({
          url: '../englishChap/englishChap?classId=' + ds.id
        })
      }else{
        wx.showToast({
          title: '请先购买课程！',
        })
      }
    }
  },
  scroll: function (e) {
    
  },
  scrolltolower: function () {
    
  },
})
var app = getApp();
Page({
  data: {
    LIST: [],
    payType: ["免费", "付费"],
    imgUrl: app.globalData.hostUrl
  },
  onLoad: function (options) {
    var that = this
    this.title = options.title
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=getAllEnglishClassData',
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
    wx.navigateTo({
      url: '../englishChap/englishChap?classId=' + ds.id
    })
  },
  scroll: function (e) {
    
  },
  scrolltolower: function () {
    
  },
})
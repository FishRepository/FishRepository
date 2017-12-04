
var app = getApp();
Page({
  data: {
    LIST: [],
    choose: "",
    sectionType: ""
  },
  bindChange: function (e) {
    var that = this;
    const val = e.detail.value
    that.setData({
      choose: that.data.LIST[val].CHAP_NAME,
      sectionType: that.data.LIST[val].ID,
      key: that.data.LIST[val].CLASS_ID + "-" + that.data.LIST[val].ID + "-" + that.data.LIST[val].CHAP_NAME + "key"
    })
  },
  onLoad: function (options) {
    var that = this;
    var classId = options.classId;
    that.getChapByClassId(classId);
  },
  getChapByClassId: function (c) {
    var that = this;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=getAllEnglishChapDataByClassId',
      data: {
        classId: c
      },
      success: function (res) {
        if (res.data.LIST <= 0) {
          wx.showModal({
            title: '提示',
            content: '该课程暂无章节目录！',
            success: function () {
              wx.navigateBack({
                delta: 1
              })
            }
          })
        } else {
          that.setData({
            LIST: res.data.LIST,
            choose: res.data.LIST[0].CHAP_NAME,
            sectionType: res.data.LIST[0].ID,
            key: res.data.LIST[0].CLASS_ID + "-" + res.data.LIST[0].ID + "-" + res.data.LIST[0].CHAP_NAME + "key"
          })
        }
      }
    })
  },
  clearSession: function () {
    var that = this;
    wx.showModal({
      title: '提示',
      content: '清除此章节缓存后，再次进入将不会继续上一次章节测试',
      success: function (res) {
        if (res.confirm) {
          wx.removeStorage({
            key: that.data.key,
            success: function (res) {
              wx.showToast({
                title: '清除成功'
              })
            }
          })
        }
      }
    })
  },
})
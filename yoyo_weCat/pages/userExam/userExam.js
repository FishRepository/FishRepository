// pages/payTest/payTest.js
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
    that.getExamList();
  },
  getExamList: function () {
    var that = this;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=getUserExamList',
      data: {
        openid: app.globalData.openid
      },
      success: function (res) {
        that.setData({
          LIST:res.data.LIST
        })
      }
    })
  },
  clearStr:function(e){
    var key = e.currentTarget.dataset.e + '-' + e.currentTarget.dataset.p + '-' + e.currentTarget.dataset.c + 'key';
    wx.showModal({
      title: '提示',
      content: '删除此试题缓存后，再次进入将不会继续上一次测试',
      success: function (res) {
        if (res.confirm) {
          wx.removeStorage({
            key: key,
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
    var that = this;
    that.getExamList();
    wx.stopPullDownRefresh()
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
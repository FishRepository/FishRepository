var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo:{},
    sexIcon: ["../../images/2.0/icon_female.png","../../images/2.0/icon_male.png"]
  },
  getOurUserInfo: function (openid) {
    var that = this;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=getUserInfo', //获取平台用户信息
      method: 'get',
      data: {
        'WID': openid
      },
      success: function (d) {
        that.setData({
          userInfo: {
            nickName: d.data.USER_INFO.user_name,
            gender: d.data.USER_INFO.user_sex,
            coin: d.data.USER_INFO.user_coin,
            avatarUrl: app.globalData.userInfo.avatarUrl
          }
        })
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    that.setData({
      userInfo: app.globalData.userInfo
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    var that = this;
    var openid = app.globalData.openid;
    that.getOurUserInfo(openid)
  },
  /*清楚缓存*/
  clearStorage: function () {
    wx.showModal({
      title: '提示',
      content: '清除缓存后将不再保留试题退出时状态',
      success:function(res){
        if(res.confirm){
          wx.clearStorage()
        }
      }
    })
  },
  editeUser: function () {
    wx.navigateTo({
      url: '/pages/info/info',
    })
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
    var openid = app.globalData.openid;
    that.getOurUserInfo(openid)
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
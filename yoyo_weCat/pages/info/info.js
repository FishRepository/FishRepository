var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo:{},
    sex:["女", "男"],
    name:"",
    sex:"",
    phone:"",
    email:""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    var openid = app.globalData.openid;
    that.setData({
      userInfo: app.globalData.userInfo
    })
    that.getOurUserInfo(openid)
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
            avatarUrl: app.globalData.userInfo.avatarUrl,
            email: d.data.USER_INFO.user_email,
            phone: d.data.USER_INFO.user_phone
          }
        })
      }
    })
  },
  changeName:function(e){
    this.setData({
      name: e.detail.value
    })
  },
  changeSex:function(e){
    this.setData({
      sex: e.detail.value
    })
  },
  changePhone:function(e){
    this.setData({
      phone: e.detail.value
    })
  },
  changeEmail:function(e){
    this.setData({
      email: e.detail.value
    })
  },
  subInfo: function (){
    var that = this;
    var openid = app.globalData.openid;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=updateUserInfo',
      data:{
        openid:openid,
        userName: that.data.name == "" ? that.data.userInfo.nickName : that.data.name,
        userSex: that.data.sex == "" ? that.data.userInfo.gender : that.data.sex,
        userPhone: that.data.phone == "" ? that.data.userInfo.phone : that.data.phone,
        userEmail: that.data.email == "" ? that.data.userInfo.email : that.data.email
      },
      method:"post",
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success:function(res){
        if(res.data.RESULT=="SUCCESS")
        wx.navigateBack({
          delta: 1
        });
      }
    })
  },
  chooseGender: function (e) {
    var that = this
    var data = e.target.dataset
    var gender = data.sex
    var param = {}
    var genderInfo = 'userInfo.gender'
    param[genderInfo] = gender
    that.setData(param)
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
    
  }
})
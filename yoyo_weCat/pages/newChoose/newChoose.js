//index.js
//获取应用实例
var app = getApp();
app.getUserInfo();
var phoneReg = /^1[34578]\d{9}$/;
Page({
  data: {
    userInfo: {
      user_name: "",
      nickName: "",
      avatarUrl: ""
    },
    showAdv: true,
    adv: "0",
    advUrl: "../../images/initAdvImg.gif",
    showModel: false
  },
  onLoad: function (options) {
    var that = this
    that.setData({
      userInfo: app.globalData.userInfo,
      postType: options.postType || wx.getStorageSync("postVal"),
      certType: options.certType || wx.getStorageSync("certVal"),
      postText: options.postText || wx.getStorageSync("postText"),
      certText: options.certText || wx.getStorageSync("certText")
    })
    console.log(wx.getStorageSync("postText"));
    var openid = app.globalData.openid
    that.getOurUserInfo(openid);
    that.getAdv();
  },
  getAdv: function () {
    var that = this;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=getAllAdv',
      success: function (res) {
        that.setData({
          advUrl: app.globalData.hostUrl + res.data.LIST[0].IMAGE,
          adv: res.data.LIST[0].ADV_SHOW
        })
      }
    })
  },
  goUser:function(){
    wx.switchTab({
      url: '/pages/user/user'
    })
  },
  reload:function(){
    try{
      wx.removeStorageSync('postVal');
      wx.removeStorageSync('certVal');
      wx.removeStorageSync('certText');
      wx.removeStorageSync('postText');
      // wx.navigateBack({
      //   delta: 1
      // })
      wx.switchTab({
        url: '/pages/index/index'
      })
    }catch(e){

    }
  },
  closeAdv: function () {
    var that = this;
    that.setData({
      showAdv: false
    })
  },
  getOurUserInfo: function (openid) {
    var that = this;
    if (openid != "")
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
          // if (d.data.USER_INFO.user_phone != "" || !d.data.USER_INFO.user_phone){
          //   that.setData({
          //     showModel:true
          //   })
          // }
        }
      })
  },
  getPhoneNumber: function (e) {
    //var that = this;
    //console.log(e.detail.errMsg)
    console.log("iv------------" + e.detail.iv)
    console.log("encryptedData---------" + e.detail.encryptedData)
    console.log("key------------" + app.globalData.session_key)
  },
  onPullDownRefresh: function () {
    var that = this;
    var openid = app.globalData.openid
    that.getOurUserInfo(openid);
    wx.stopPullDownRefresh()
  },
  onReady: function () {

  }
})
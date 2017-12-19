//app.js
App({
  onLaunch: function () {
    var that = this
    //调用API从本地缓存中获取数据
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    wx.getUserInfo({
      success: function (res) {
        that.globalData.userInfo = res.userInfo;
      }
    })
  },
  getUserInfo:function(){
    var that = this
      //调用登录接口
    wx.login({
      success: function (r) {
        if(r.code){
          wx.request({
            url: that.globalData.hostUrl +'/admin.do?method=getOpenId',
            data:{
              js_code:r.code
            },
            success:function(d){
              that.globalData.openid = d.data.data[0].openid;
              that.globalData.session_key = d.data.data[0].session_key;
              that.userReg(d.data.data[0].openid);
            }
          })
        }
      }
    })
  },
  globalData:{
    userInfo:{
      nickName:""
    },
    session_key:"",
    appid:"wx3f315fb4a4971eb9",
    key:"7f86b2d529779abf05543da15be00eb5",
    hostUrl: "https://www.yoyotec.com/youyou_client",
    //"http://111.111.125.234:8080/youyou_client", // http://111.111.125.238:8080/youyou_client  https://www.yoyotec.com/youyou_client
    openid:"",
    payMoney:1,
    orderType: ["", "购买解析次数", "购买试题"],
    payCoin:1, //查看解析一次花费的解析币
  },
  userReg:function(openid){
    var that = this;
    wx.request({
      url: that.globalData.hostUrl +'/admin.do?method=regUser', //注册用户
      method: 'post',
      data: {
        "WID": openid,
        "NAME": that.globalData.userInfo.nickName,
        "SEX": that.globalData.userInfo.gender
      },
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (d) {
        that.getOurUserInfo(openid);
      }
    })
  },
  getOurUserInfo: function (openid){
    var that = this;
    wx.request({
      url: that.globalData.hostUrl +'/admin.do?method=getUserInfo', //获取平台用户信息
      method:'get',
      data:{
        'WID': openid
      },
      success:function(d){
        that.globalData.userInfo.nickName = d.data.USER_INFO.user_name;
        that.globalData.userInfo.gender = d.data.USER_INFO.user_sex;
        that.globalData.userInfo.coin = d.data.USER_INFO.user_coin;
      }
    })
  }
})
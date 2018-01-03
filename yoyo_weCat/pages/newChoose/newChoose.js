//index.js
//获取应用实例
var app = getApp();
app.getUserInfo();
const options = {
  duration: 60000,
  sampleRate: 16000,
  numberOfChannels: 1,
  encodeBitRate: 48000,
  format: 'mp3',
  frameSize: 50
};
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
    showModel: false,
    isRecord: false
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

  },
  record: function () {
    var that = this;
    var param = {};
    var isRecord = "isRecord";
    param[isRecord] = true;
    that.setData(param);
    this.constantRecorderStart();
  },
  stopRecord: function () {
    var that = this;
    var param = {};
    var isRecord = "isRecord";
    param[isRecord] = false;
    that.setData(param);
    this.constantRecorderStop();
  },
  constantRecorderStart: function () {
    var that = this;
    const recorderManager = this.constantRecorderManager();
    recorderManager.start(options);
  },
  constantRecorderStop: function () {
    var that = this;
    const recorderManager = wx.getRecorderManager();
    recorderManager.stop();
  },
  constantRecorderManager: function () {
    const recorderManager = wx.getRecorderManager();
    recorderManager.onStart(() => {
      console.log('recorder start');
    });
    recorderManager.onResume(() => {
      console.log('recorder resume');
    });
    recorderManager.onPause(() => {
      console.log('recorder pause');
    });
    recorderManager.onStop((res) => {
      console.log('recorder stop', res);
      const { tempFilePath } = res;
      const that = this;
      that.setData({
        recordFile: tempFilePath
      });
      this.uploadFileToServer();
      // wx.showToast({
      //   title: that.data.recordFile,
      //   icon: 'success',
      //   duration: 2000
      // })
    });
    recorderManager.onFrameRecorded((res) => {
      const { frameBuffer } = res
      console.log('frameBuffer.byteLength', frameBuffer.byteLength)
    });
    return recorderManager;
  },
  uploadFileToServer: function () {
    const that = this;
    wx.showToast();
    setTimeout(function () {
      var urls = app.globalData.hostUrl + "/admin.do?method=englishASR";
      console.log(that.data.recordFile);
      wx.uploadFile({
        url: urls,
        filePath: that.data.recordFile,
        name: 'file',
        formData: {
          fileName: 'file' + app.globalData.openid + ".mp3"
        },
        header: {
          'content-type': 'multipart/form-data'
        },
        success: function (res) {
          wx.showToast();
          // var str = res.data;
          // var data = JSON.parse(str);
          // if (data.states == 1) {
          //   var cEditData = s.data.editData;
          //   cEditData.recodeIdentity = data.identitys;
          //   s.setData({ editData: cEditData });
          // }
          // else {
          //   wx.showModal({
          //     title: '提示',
          //     content: data.message,
          //     showCancel: false,
          //     success: function (res) {

          //     }
          //   });
          // }
          // wx.hideToast();
        },
        fail: function (res) {
          console.log(res);
          wx.showModal({
            title: '提示',
            content: "网络请求失败，请确保网络是否正常",
            showCancel: false,
            success: function (res) {

            }
          });
          wx.hideToast();
        }
      });
    }, 1000);
  }
})
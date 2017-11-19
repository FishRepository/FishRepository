var app = getApp();
Page({
  data: {
    list: [],
    certList: {}, //试题列表
    postVal: 0, //post选择id
    certVal: 0, //cert选择id
    postText: "", //post选择值
    certText: "", //cert选择值
    payType: "",
    shareType: "", //共享类型,
    postIndex: 0,
    certIndex: 0,
    key: "",
    showAdv: true,
    adv: "0",
    advUrl: "",
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    app.getUserInfo();
    try{
      var postVal = wx.getStorageSync("postVal");
      var postText = wx.getStorageSync("postText");
      var certVal = wx.getStorageSync("certVal");
      var certText = wx.getStorageSync("certText");
      if(postVal&&certVal&&postText&&certText){
        wx.navigateTo({
          url: '/pages/newChoose/newChoose?postType=' + postVal + '&certType=' + certVal + '&postText=' + postText + '&certText=' + certText
        })
      }else{
        that.setData({
          payType: options.payType,
          type: options.type
        })
        that.getPost()
      }
    }catch(e){

    }
  },
  postChange: function (e) {
    var that = this;
    that.setData({
      postIndex: e.detail.value,
      postVal: that.data.list[e.detail.value].postId,
      postText: that.data.list[e.detail.value].postName,
      key: that.data.type + "-" + that.data.list[e.detail.value].postId + "-" + that.data.certVal + "key",
      certIndex:0
    })
    that.getCert(that.data.list[e.detail.value].postId);
    wx.setStorage({
      key: 'postVal',
      data: that.data.list[e.detail.value].postId,
    })
    wx.setStorage({
      key: 'postText',
      data: that.data.list[e.detail.value].postName,
    })
  },
  certChange: function (e) {
    var that = this;
    that.setData({
      certIndex: e.detail.value,
      certVal: that.data.certList[e.detail.value].certId,
      certText: that.data.certList[e.detail.value].certName,
      shareType: that.data.certList[e.detail.value].certType,
      key: that.data.type + "-" + that.data.postVal + "-" + that.data.certList[e.detail.value].certId + "key"
    })
    wx.setStorage({
      key: 'certVal',
      data: that.data.certList[e.detail.value].certId,
    })
    wx.setStorage({
      key: 'certText',
      data: that.data.certList[e.detail.value].certName,
    })
  },
  getPost: function (){
    var that = this;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=getPost',
      success: function (res) {
        that.setData({
          list: res.data.LIST,
          postVal: res.data.LIST[0].postId,
          postText: res.data.LIST[0].postName
        })
        wx.setStorage({
          key: 'postVal',
          data: res.data.LIST[0].postId,
        })
        wx.setStorage({
          key: 'postText',
          data: res.data.LIST[0].postName,
        })
        that.getCert(res.data.LIST[0].postId);
      }
    })
  },
  getCert: function (pId) {
    var that = this;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=getCertByPost',
      data: {
        postId: pId
      },
      success: function (res) {
        that.setData({
          certList: res.data.certList,
          certVal: res.data.certList[0].certId,
          certText: res.data.certList[0].certName,
          shareType: res.data.certList[0].certType,
          key: that.data.type + "-" + that.data.postVal + "-" + res.data.certList[0].certId + "key"
        })
        wx.setStorage({
          key: 'certVal',
          data: res.data.certList[0].certId,
        })
        wx.setStorage({
          key: 'certText',
          data: res.data.certList[0].certName,
        })
      }
    })
  },
  goExam: function () {
    var that = this;
    try {
      wx.setStorageSync('postVal', that.data.postVal);
      wx.setStorageSync('certVal', that.data.certVal);
      wx.setStorageSync('postText', that.data.postText);
      wx.setStorageSync('certText', that.data.certText);
    } catch (e) {
      
    }
    wx.navigateTo({
      url: '/pages/newChoose/newChoose?postType=' + that.data.postVal + 
      '&certType=' + that.data.certVal +
      '&certText=' + that.data.certText +
      '&postText=' + that.data.postText
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    var that = this;
    that.getPost()
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

  }
})
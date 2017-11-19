var app = getApp();
Page({
  data: {
    list: [],
    certList:{}, //试题列表
    postVal: 0, //post选择id
    certVal: 0, //cert选择id
    postText: "", //post选择值
    certText: "", //cert选择值
    payType: "",
    shareType: "", //共享类型,
    postIndex:0,
    certIndex:0,
    key:""
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    that.setData({
      payType: options.payType,
      type:options.type
    })
    wx.request({
      url: app.globalData.hostUrl+'/admin.do?method=getPost',
      success:function(res){
        that.setData({
          list:res.data.LIST,
          postVal: res.data.LIST[0].postId,
          postText: res.data.LIST[0].postName,
          type: options.type
        })
        that.getCert(res.data.LIST[0].postId);
      }
    })
  },
  postChange:function(e){
    var that = this;
    that.setData({
      postIndex: e.detail.value,
      postVal: that.data.list[e.detail.value].postId,
      postText: that.data.list[e.detail.value].postName,
      key: that.data.type + "-" + that.data.list[e.detail.value].postId + "-" + that.data.certVal + "key"
    })
    that.getCert(that.data.list[e.detail.value].postId)
  },
  certChange:function(e){
    var that = this;
    that.setData({
      certIndex: e.detail.value,
      certVal: that.data.certList[e.detail.value].certId,
      certText: that.data.certList[e.detail.value].certName,
      shareType: that.data.certList[e.detail.value].certType,
      key: that.data.type + "-" + that.data.postVal + "-" + that.data.certList[e.detail.value].certId + "key"
    })
  },
  getCert:function(pId){
    var that = this;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=getCertByPost',
      data:{
        postId:pId
      },
      success:function(res){
        that.setData({
          certList: res.data.certList,
          certVal: res.data.certList[0].certId,
          certText: res.data.certList[0].certName,
          shareType: res.data.certList[0].certType,
          key: that.data.type + "-" + that.data.postVal + "-" + res.data.certList[0].certId + "key"  
        })
      }
    })
  },
  goExam:function(){
    var that = this;
    if (that.data.payType=="0"){
      wx.navigateTo({
        url: '/pages/exam/exam?type=' + that.data.type + '&postType=' + that.data.postVal + '&certType=' + that.data.certVal
      })
    }else{
      wx.navigateTo({
        url: '/pages/payTest/payTest?postType=' + that.data.postVal + '&certType=' + that.data.certVal
      })
    }
  },
  clearStr:function(){
    var that = this;
    wx.showModal({
      title: '提示',
      content: '删除此试题缓存后，再次进入将不会继续上一次测试',
      success:function(res){
        if(res.confirm){
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

  }
})
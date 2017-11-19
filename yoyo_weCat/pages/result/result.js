// pages/result/result.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    imgList:[
      "../../images/90-100-img.gif",
      "../../images/80-90-img.gif",
      "../../images/70-80-img.gif",
      "../../images/60-70-img.gif", //60
      "../../images/50-60-img.gif",
      "../../images/40-50-img.gif",
      "../../images/30-40-img.gif"
    ],
    showIndex:1,
    userScore:0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    var passScore = options.passScore;
    var userScore = options.userScore;
    that.setData({
      userScore: userScore
    })
    
    var outScore = userScore - passScore;
    var path = parseInt(Math.abs(outScore) / 10);
    if (outScore>0){
      switch (path){
        case "3":
          that.setData({
            showIndex: 0
          })
          break;
        case "2":
          that.setData({
            showIndex: 1
          })
          break;
        case "1":
          that.setData({
            showIndex: 2
          })
          break;
        case "0":
          that.setData({
            showIndex: 3
          })
          break;
        default:
          that.setData({
            showIndex: 3
          })
          break;
      }
    }else{
      //path = Math.abs(path);
      switch (path) {
        case "0":
          that.setData({
            showIndex: 4
          })
          break;
        case "1":
          that.setData({
            showIndex: 5
          })
          break;
        case "2":
          that.setData({
            showIndex: 6
          })
          break;
        default:
          that.setData({
            showIndex: 6
          })
          break;
      }
    }
  },
  returnHome:function(){
    wx.navigateBack({
      delta: 10000000
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
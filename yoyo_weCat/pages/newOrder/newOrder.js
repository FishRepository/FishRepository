
var app = getApp();
Page({
  data: {
    LIST: [],
    LISTALL: [],
    choose: "",
    postType: "",
    certType: "",
    sectionType: "",
    showMoreStat: true,
    isChoose: false,
    currentId:""
  },
  bindChange: function (e) {
    var that = this;
    const val = e.detail.value
    console.log(val)
    that.setData({
      choose: that.data.LIST[val].NAME,
      sectionType: that.data.LIST[val].ID,
      key: that.data.LIST[val].ID + "-" + that.data.LIST[val].POST_TYPE + "-" + that.data.LIST[val].CERT_TYPE + "key"
    })
    console.log(that.data.key)
  },
  onLoad: function (options) {
    var that = this;
    var post = options.postType,cert = options.certType;
    that.getSection(post,cert);
  },
  showMore: function () {
    var that = this
    var showMoreStat = false
    var LIST = that.data.LISTALL
    var currentId = that.data.currentId
    var chooseState = "LIST[" + currentId + "].chooseStat"
    that.setData({
      showMoreStat: showMoreStat,
      LIST: LIST
    })
    var param = {}
    param[chooseState] = true
    that.setData(param)
  },
  chooseChap: function (e) {
    var that = this
    var data = e.currentTarget.dataset
    var currentId = data.id
    var idChooseState = that.data.LIST[currentId].chooseStat
    if(idChooseState === true){
      return false
    }
    var param = {}
    var chooseState = "LIST[" + currentId + "].chooseStat"
    var currentId_ = "currentId"
    param[chooseState] = true
    param[currentId_] = currentId
    that.setData(param)
    var LIST = that.data.LIST
    for (var i = 0; i < LIST.length; i++) {
      if (i == currentId) {
        continue
      }
      var idChooseState_ = that.data.LIST[i].chooseStat
      if (idChooseState_ === true) {
        var param_ = {}
        var chooseState_ = "LIST[" + i + "].chooseStat"
        param_[chooseState_] = false
        that.setData(param_)
      }
    }
    that.setData({
      isChoose: true
    })
    const val = data.value
    that.setData({
      choose: that.data.LIST[currentId].NAME,
      sectionType: that.data.LIST[currentId].ID,
      key: that.data.LIST[currentId].ID + "-" + that.data.LIST[currentId].POST_TYPE + "-" + that.data.LIST[currentId].CERT_TYPE + "key"
    })
  },
  getSection:function(p,c){
    var that = this;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=getSection',
      data:{
        postType:p,
        certType:c
      },
      success:function(res){
        //console.log(res.data.LIST)
        if (res.data.LIST <= 0) {
          wx.showModal({
            title: '提示',
            content: '此类试题暂无章节目录！',
            success: function () {
              wx.navigateBack({
                delta: 1
              })
            }
          })
        } else {
          var LIST = res.data.LIST.slice(0, 5)
          that.setData({
            LIST: LIST,
            LISTALL: res.data.LIST,
            choose: res.data.LIST[0].NAME,
            postType: res.data.LIST[0].POST_TYPE,
            certType: res.data.LIST[0].CERT_TYPE,
            sectionType: res.data.LIST[0].ID,
            key: res.data.LIST[0].ID + "-" + res.data.LIST[0].POST_TYPE + "-" + res.data.LIST[0].CERT_TYPE + "key"
          })
        }
      }
    })
  },
  clearSession: function () {
    var that = this;
    var isChoose = that.data.isChoose
    if (!isChoose){
      wx.showToast({
        title: '请先选择章节'
      })
      return false
     }
    wx.showModal({
      title: '提示',
      content: '删除此试题缓存后，再次进入将不会继续上一次测试',
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
  goExam: function () {
    var that = this;
    var isChoose = that.data.isChoose
    if (!isChoose) {
      wx.showToast({
        title: '请先选择章节'
      })
      return false
    }
    var sectionType = that.data.sectionType
    var postType = that.data.postType
    var certType = that.data.certType
    wx.navigateTo({
      url: '/pages/newExam/newExam?sectionType=' + sectionType + '&postType=' + postType + '&certType=' + certType,
    })
  }
})
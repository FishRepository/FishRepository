
var app = getApp();
Page({
  data: {
    LIST: [],
    choose:"",
    postType:"",
    certType:"",
    sectionType:""
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
          that.setData({
            LIST: res.data.LIST,
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
})
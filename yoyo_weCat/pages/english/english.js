var util = require('../../utils/util.js');
var app = getApp();
Page({
  data: {
    LIST: [],
    payType: ["免费", "付费"],
    imgUrl: app.globalData.hostUrl
  },
  onLoad: function (options) {
    var that = this;
    this.title = options.title;
    var openid = app.globalData.openid;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=getAllEnglishClassData',
      data: {
        "WID": openid
      },
      success: function (res) {
        if (res.data.LIST <= 0) {
          wx.showModal({
            title: '提示',
            content: '暂无相关英语课程！',
            success: function () {
              wx.navigateBack({
                delta: 1
              })
            }
          })
        } else {
          that.setData({
            LIST: res.data.LIST
          })
        }
      }
    })
  },
  viewDetail: function (e) {
    var that = this
    var ds = e.currentTarget.dataset;
    var isPay = ds.ispay;
    var payOver = ds.payover;
    var openid = app.globalData.openid;
    var classId = ds.id;
    if (isPay==0){
      wx.navigateTo({
        url: '../englishChap/englishChap?classId=' + ds.id
      })
    }else{
      if (payOver != undefined && payOver>=0){
        wx.navigateTo({
          url: '../englishChap/englishChap?classId=' + ds.id
        })
      }else{
        // wx.showToast({
        //   title: '请先购买课程！',
        // })
        wx.showModal({
          title: '提示',
          content: '点击确认购买课程！',
          success: function (res) {
            if (res.confirm) {
              var oType = 2;
              var payMoney = ds.money * 100;
              wx.request({
                url: app.globalData.hostUrl + '/admin.do?method=payMoney',
                data: {
                  payMoney: payMoney,
                  payClass: app.globalData.orderType[oType],
                  openid: app.globalData.openid
                },
                header: {
                  'content-type': 'application/x-www-form-urlencoded'
                },
                method: "post",
                success: function (d) {
                  if (d.data.result.prepay_id) {
                    that.requestPayment(d.data.result.prepay_id, d.data.orderNumber, oType, payMoney, d.data.orderTime, classId);
                  }
                }
              })
            }
          }
        })
        
      }
    }
  },
  payForEnglishClass: function (e) {
    var that = this;
    var ds = e.currentTarget.dataset;
    var openid = app.globalData.openid;
    var classId = ds.classid;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=getEnglishPayRecordCount',//获取用户是否购买了此章节
      method: 'get',
      data: {
        'WID': openid,
        'CLASS_ID': classId
      },
      success: function (res) {
        if (res.data.ret > 0) {
          wx.showModal({
            title: '提示',
            content: '您已购买了此课程，点击确定进入试题练习！',
            success: function (res) {
              if(res.confirm){
                wx.navigateTo({
                  url: '../englishChap/englishChap?classId=' + ds.classId
                })
              }
            }
          })
        } else {
          var oType = 2;
          var payMoney = ds.money * 100;
          wx.request({
            url: app.globalData.hostUrl + '/admin.do?method=payMoney',
            data: {
              payMoney: payMoney,
              payClass: app.globalData.orderType[oType],
              openid: app.globalData.openid
            },
            header: {
              'content-type': 'application/x-www-form-urlencoded'
            },
            method: "post",
            success: function (d) {
              if (d.data.result.prepay_id) {
                that.requestPayment(d.data.result.prepay_id, d.data.orderNumber, oType, payMoney, d.data.orderTime, classId);
              }
            }
          })
        }
      }
    })
  },
  requestPayment: function (prepayId, orderNumber, orderType, payMoney, orderTime, examId) {
    var that = this;
    var timeStamp = new Date().getTime();
    timeStamp = timeStamp.toString();
    var nonceStr = util.getRandom25String();
    var openid = app.globalData.openid;
    var appid = app.globalData.appid;

    var paySignStr = "appId=" + app.globalData.appid +
      "&nonceStr=" + nonceStr +
      "&package=prepay_id=" + prepayId +
      "&signType=MD5" +
      "&timeStamp=" + timeStamp +
      "&key=" + app.globalData.key;
    var paySign = util.hex_md5(paySignStr);
    wx.requestPayment({
      appId: appid,
      timeStamp: timeStamp,
      nonceStr: nonceStr,
      package: "prepay_id=" + prepayId,
      signType: 'MD5',
      paySign: paySign,
      success: function (res) {
        console.log(res)
        var orderState = "1";
        that.recordOrder(orderState, orderType, payMoney, orderNumber, orderTime, examId);
        wx.redirectTo({
          url: '/pages/english/english',
          success: function (res) { },
          fail: function (res) { },
          complete: function (res) { },
        })
      },
      fail: function (res) {
        console.log(res)
      }
    })
  },
  recordOrder: function (orderState, orderType, orderMoney, orderNumber, orderTime, examId) {
    var that = this;
    wx.request({
      url: app.globalData.hostUrl + '/admin.do?method=englishPay',
      method: "post",
      data: {
        order_money: orderMoney,
        order_type: orderType,
        order_state: orderState,
        order_number: orderNumber,
        order_time: orderTime,
        openid: app.globalData.openid,
        CLASS_ID: examId,
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: function (d) {

      }
    })
  },

  scroll: function (e) {
    
  },
  scrolltolower: function () {
    
  },
})
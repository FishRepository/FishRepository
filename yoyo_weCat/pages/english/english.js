Page({
  data: {
    films: [],
    hasMore: true,
    showLoading: false,
    start: 0,
    englishCourse: [
      {
        "id" :"1",
        "ecTitle" :"海员英语课程初级",
        "ecImg": "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=3494076888,1278194760&fm=58",
        "ecDesc": "海员英语课程初级海员英语课程初级海员英语课程初级海员英语课程初级海员英语课程初级海员英语课程初级"
      },{
        "id": "2",
        "ecTitle": "海员英语课程中级",
        "ecImg": "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=3494076888,1278194760&fm=58",
        "ecDesc": "海员英语课程中级海员英语课程中级海员英语课程初级海员英语课程初级海员英语课程初级海员英语课程初级"
      }, {
        "id": "3",
        "ecTitle": "海员英语课程高级",
        "ecImg": "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=3494076888,1278194760&fm=58",
        "ecDesc": "海员英语课程高级海员英语课程高级海员英语课程初级海员英语课程初级海员英语课程初级海员英语课程初级"
      }, {
        "id": "4",
        "ecTitle": "海员英语课程特级",
        "ecImg": "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=3494076888,1278194760&fm=58",
        "ecDesc": "海员英语课程特级海员英语课程特级海员英语课程初级海员英语课程初级海员英语课程初级海员英语课程初级"
      }
    ]
  },
  onPullDownRefresh: function () {
    console.log('onPullDownRefresh', new Date())
  },
  scroll: function (e) {
    //console.log(e)
  },
  scrolltolower: function () {
    
  },
  viewDetail: function (e) {
    var ds = e.currentTarget.dataset;
    wx.navigateTo({
      url: '../detail/detail?id=' + ds.id + '&title=' + ds.title + '&type=ing'
    })
  }
})

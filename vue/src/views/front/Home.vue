<template>
  <div class="main-content">
    <div style="display: flex; align-items: flex-start; grid-gap: 10px;">

<!--      <div style="flex: 1">-->
<!--        <div style="display: flex;font-size: 12px; color: #666; cursor: pointer;" @click="getLocationId"><i-->
<!--            class="el-icon-refresh"></i> 重新定位-->
<!--        </div>-->
<!--        <div id="he-plugin-standard" style="display: flex"></div>-->
<!--      </div>-->

      <!--      <div style="width: 150px" class="card">-->
      <!--        <div class="category-item" :class="{ 'category-item-active': item.name === current }"-->
      <!--             v-for="item in categoryList" :key="item.id" @click="selectCategory(item.name)">{{ item.name }}</div>-->
      <!--      </div>-->
      <div style="flex: 1;">
        <div class="card" style="min-height: 30vh;display: flex;flex-direction: column; width: 80%">
          <div style="font-size: 30px">重要公告</div>
          <div class="blog-box" v-for="item in tableData" :key="item.id" v-if="total > 0" style="display: flex">
            <div style="flex: 1;width: 0;">
              <a :href="'/front/noticeDetail?noticeId=' + item.id" target="_blank">
                <div style="font-size: 20px;color: #666">{{ item.title }}</div>
              </a>
              <div style="display: flex;align-items: flex-end">
                <div style="flex: 1; font-size: 13px">
                  <span style="color: #666; margin-right: 20px;font-weight: bolder"><i
                      class="el-icon-user"></i> {{ item.user }}</span>
                </div>
                <div style="display: flex">
                  <div class="notice-time">{{ item.time }}</div>
                </div>
              </div>
            </div>
          </div>
          <div v-if="total === 0" style="padding: 20px ;text-align: center; font-size: 16px; color: #666">暂无数据</div>
        </div>
        <div class="card" style="margin-bottom: 10px;margin-top: 10px;width: 80%">
          <div style="display: flex; align-items: center; padding-bottom: 10px; border-bottom: 1px solid #ddd">
            <div style="font-size: 20px; flex: 1">今日热议</div>
            <div style="font-size: 12px; color: #666; cursor: pointer;" @click="refreshTop"><i
                class="el-icon-refresh"></i> 换一换
            </div>
          </div>
          <div>
            <div v-for="item in showList" :key="item.id" style="margin: 15px 0" class="line1">
              <a :href="'/front/blogDetail?blogId=' + item.id" target="_blank">
                <span style="width: 18px; display: inline-block; text-align: right; margin-right: 10px">
                  <span style="color: orangered" v-if="item.index === 1">{{ item.index }}</span>
                  <span style="color: goldenrod" v-else-if="item.index === 2">{{ item.index }}</span>
                  <span style="color: dodgerblue" v-else-if="item.index === 3">{{ item.index }}</span>
                  <span style="color: #666" v-else>{{ item.index }}</span>
                </span>
                <span style="color: #666;">{{ item.title }}</span>
              </a>
            </div>
          </div>
        </div>
      </div>


      <div style="width: 400px;display: flex;flex-direction: column">

        <chat></chat>


        <!--        <div class="card" style="margin-bottom: 10px">-->
        <!--          <div style="font-size: 20px; font-weight: bold; margin-bottom: 10px">欢迎您！😊</div>-->
        <!--          <a href="/front/person"><div style="color: #666">今天有什么想说的</div></a>-->
        <!--        </div>-->


        <!--        <div style="margin-bottom: 10px">-->
        <!--          <div v-for="item in topActivityList" :key="item.id" style="margin-bottom: 10px">-->
        <!--            <a :href="'/front/activityDetail?activityId=' + item.id" target="_blank"><img :src="item.cover" alt="" style="width: 100%; border-radius: 5px"></a>-->
        <!--          </div>-->
        <!--        </div>-->
      </div>

    </div>
  </div>
</template>

<script>

import Footer from "@/components/Footer";
import BlogList from "@/components/BlogList";
import axios from "axios";
import Chat from "@/views/front/Chat";


export default {
  components: {
    Chat,
    BlogList,
    Footer
  },
  data() {
    return {
      title: null,
      tableData: [],  // 所有的数据


      topList: [],
      showList: [],
      lastIndex: 0,
      topActivityList: [],
      locationName: "",
      locationId: "",

      intervalId: null
    }
  },
  created() {
    this.getWeather()
    // this.startInterval()
  },
  beforeDestroy() {
    // this.stopInterval()
  },
  mounted() {
    this.load()
    this.loadNotices()
    this.refreshTop()
    this.getLocationId()
    this.loadTopActivity()


  },
  watch: {
    locationId() {
      const script = document.createElement('script')
      script.src = `https://widget.qweather.net/standard/static/js/he-standard-common.js?v=2.0`
      script.async = true
      document.body.appendChild(script)
      console.log("正在预处理天气")
      script.onload = () => {
        window.WIDGET = {
          "CONFIG": {
            "layout": "2",
            "width": 200,
            "height": 200,
            "background": "1",
            "dataColor": "FFFFFF",
            "borderRadius": "20",
            "city": this.locationId,
            "key": "b955e773584b452fb11617ea7c3cc0ec"
          }
        }
      }
    }
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    loadTopActivity() {
      this.$request.get('/activity/selectTop').then(res => {
        this.topActivityList = res.data || []
      })
    },
    refreshTop() {
      this.$request.get('/blog/selectTop').then(res => {
        this.topList = res.data || []
        let i = 1
        this.topList.forEach(item => item.index = i++)

        // 0  5  0
        if (this.lastIndex === 20) {
          this.lastIndex = 0
        }
        this.showList = this.topList.slice(this.lastIndex, this.lastIndex + 5)  // 0-5   5- 10  // 0-5
        this.lastIndex += 5  // 5  10  5
      })
    },

    load() {
      // 请求分类的数据
      this.$request.get('/category/selectAll').then(res => {
        this.categoryList = res.data || []
        this.categoryList.unshift({name: '全部博客'})
      })

    },

    loadNotices() {
      let url = '/notice/selectPage'
      this.$request.get(url, {
        params: {
          pageNum: 1,
          pageSize: 5,
          categoryName: this.categoryName === '全部公告' ? null : this.categoryName,
          title: this.title
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },

    getLocationName() {
      return new Promise((resolve, reject) => {
        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(position => {
            const latitude = position.coords.latitude.toFixed(2);
            const longitude = position.coords.longitude.toFixed(2);
            this.locationName = `${longitude},${latitude}`;
            console.log(this.locationName)
            console.log("测试获得地名")
            resolve();
          }, error => {
            this.locationName = null
            console.log("获得地名失败")
            resolve();
          });
        }
        else {
          this.locationName = null
          console.log("获得地名失败")
          resolve();
        }
      });
    },
    getLocationId() {
      console.log("get id")
      console.log(this.locationName)
      this.getLocationName().then(() => {
        if (this.locationName) {
          const key = "b955e773584b452fb11617ea7c3cc0ec"; // 替换成你的API Key
          const url = `https://geoapi.qweather.com/v2/city/lookup?location=${this.locationName}&key=${key}`;

          axios.get(url)
              .then(response => {
                // 请求成功，处理返回的数据
                this.locationId = ""
                this.locationId = "CN" + response.data.location[0].id;
                console.log("获得地区id成功")
              })
              .catch(error => {
                // 请求失败，处理错误
                console.error(error);
              });
        } else {
          this.locationId = "CN101010100"
        }
      })
    },

    getWeather() {
      const script = document.createElement('script')
      script.src = `https://widget.qweather.net/standard/static/js/he-standard-common.js?v=2.0`
      script.async = true
      document.body.appendChild(script)
      console.log("正在获得天气")
      script.onload = () => {
        window.WIDGET = {
          "CONFIG": {
            "layout": "2",
            "width": 300,
            "height": 270,
            "background": "1",
            "dataColor": "FFFFFF",
            "borderRadius": "20",
            "city": "CN101010100",
            "key": "b955e773584b452fb11617ea7c3cc0ec"
          }
        }
      }
    },
    startInterval() {
      this.intervalId = setInterval(this.getLocationId, 5000);
    },
    stopInterval() {
      clearInterval(this.intervalId);
    }

  }
}
</script>

<style>
.category-item {
  text-align: center;
  padding: 10px 0;
  font-size: 16px;
  cursor: pointer;
}

.category-item-active {
  background-color: #1890ff;
  color: #fff;
  border-radius: 5px;
}

.blog-box {
  display: flex;
  grid-gap: 15px;
  padding: 10px 0;
  border-bottom: 1px solid #ddd;
}

.notice-time {
  display: flex;
  grid-gap: 10px;
  padding-right: 0;
}

</style>

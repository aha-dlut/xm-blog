<template>
  <div class="main-content">
    <div style="display: flex; align-items: flex-start; grid-gap: 10px">

      <div style="width: 150px" class="card">
        <div class="category-item" :class="{ 'category-item-active': item.name === current }"
             v-for="item in categoryList" :key="item.id" @click="selectCategory(item.name)">{{ item.name }}</div>
      </div>

      <div style="flex: 1;">




        <notice-list :categoryName="current" ref="noticeListRef"/>

<!--        <blog-list :categoryName="current" ref="blogListRef" />-->



      </div>

      <div style="width: 260px">
        <div class="card" style="margin-bottom: 10px">
          <div style="font-size: 20px; font-weight: bold; margin-bottom: 10px">欢迎您！😊</div>
          <a href="/front/person"><div style="color: #666">本页可获得所有通知</div></a>
        </div>




      </div>



    </div>
  </div>
</template>

<script>

import Footer from "@/components/Footer";
import NoticeList from "@/components/NoticeList";
import Notice from "@/views/manager/Notice";
export default {
  components: {
    Notice,
    NoticeList,
    Footer
  },
  data() {
    return {
      current: '全部公告',  //当前选中的分类名称
      categoryList: [],

      topList: [],
      showList: [],
      lastIndex: 0,
      topActivityList: []
    }
  },
  mounted() {
    this.load()
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {

    selectCategory(categoryName) {
      this.current = categoryName
    },
    load() {
      // 请求分类的数据
      this.$request.get('/category/selectAll').then(res => {
        this.categoryList = res.data || []
        this.categoryList.unshift({ name: '全部公告' })
      })
    },
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


</style>

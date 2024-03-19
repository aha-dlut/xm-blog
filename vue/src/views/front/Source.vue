<template>
  <div class="main-content">
    <div style="display: flex; align-items: flex-start; grid-gap: 10px">

      <div style="width: 150px" class="card">
        <div class="category-item" :class="{ 'category-item-active': item.name === current }"
             v-for="item in categoryList" :key="item.id" @click="selectCategory(item.name)">{{ item.name }}</div>
      </div>



    </div>
  </div>
</template>

<script>
export default {
  name: "Source",


  data() {
    return {
      current: '全部公告',  //当前选中的分类名称
      categoryList: [],

    }
  },
  mounted() {
    this.load()
  },
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

<style scoped>
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
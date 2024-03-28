<template>
  <div class="main-content">
    <div style="display: flex; align-items: flex-start; grid-gap: 10px">

      <div style="width: 150px;margin-top: 50px" class="card">
        <div class="category-item" :class="{ 'category-item-active': item.name === current }"
             v-for="item in categoryList" :key="item.name" @click="selectCategory(item.name)">{{ item.name }}
        </div>
      </div>

      <div style="flex: 1;">
      <common-files-list :category="current" ref="commonFilesListRef"></common-files-list>

      </div>

    </div>


  </div>

</template>

<script>
import CommonFilesList from "@/components/CommonFilesList";
export default {
  name: "CommonFiles",
  components: {CommonFilesList},
  data() {
    return {
      current: '全部文件',
      categoryList: [{"name": "全部文件", "value": "全部文件"},{"name": "人文社科", "value": "人文社科"}, {"name": "数学科学", "value": "数学科学"}, {
        "name": "工程学科",
        "value": "工程学科"
      }, {"name": "外语文件", "value": "外语文件"}, {"name": "校内文件", "value": "校内文件"},{"name": "教材", "value": "教材"},  {"name": "其他文件", "value": "其他文件"}],
    }
  },
  methods:{
    selectCategory(categoryName) {
      this.current = categoryName

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
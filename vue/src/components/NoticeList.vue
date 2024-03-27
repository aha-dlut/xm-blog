<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入标题查询" style="width: 200px" v-model="title"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="loadNotices(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>
    <div class="card" style="min-height: 80vh">
      <div class="blog-box" v-for="item in tableData" :key="item.id" v-if="total > 0">
        <div style="flex: 1; width: 0">
          <a :href="'/front/noticeDetail?noticeId=' + item.id" target="_blank"><div class="blog-title">{{ item.title }}</div></a>
          <div style="width: 150px">
            <img style="width: 100%; height: 80px; border-radius: 5px" :src="item.cover" alt="">
          </div>
        </div>
<!--          <div class="line1" style="color: #666; margin-bottom: 10px; font-size: 13px">{{ item.descr }}</div>-->
          <div style="display: flex; align-items: center">
            <div style="flex: 1; font-size: 13px">
              <span style="color: #666; margin-right: 20px"><i class="el-icon-user"></i> {{ item.user }}</span>
            </div>
            <div class="notice-time">{{item.time}}</div>
          </div>
        </div>

      </div>
      <div v-if="total === 0" style="padding: 20px ;text-align: center; font-size: 16px; color: #666">暂无数据</div>
      <div style="margin-top: 10px" v-if="total">
        <el-pagination
            background
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 20]"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="total">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "NoticeList",
  props: {
    categoryName: null,
    type: null,
    showOpt: false
  },
  data() {
    return {
      title:null,
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
    }
  },
  watch: {  // 监听数据变化  加载最新数据
    categoryName() {
      this.loadNotices(1)
    },
    title(){
      this.loadNotices(1)
    }
  },
  created() {
    this.loadNotices(1)
  },
  methods: {


    loadNotices(pageNum) {
      if (pageNum) this.pageNum = pageNum
      let url ='/notice/selectPage'
      // let url
      // switch (this.type) {
      //   case 'user': url = '/blog/selectUser'; break;
      //   // case 'like': url = '/blog/selectLike'; break;
      //   // case 'collect': url = '/blog/selectCollect'; break;
      //   // case 'comment': url = '/blog/selectComment'; break;
      //   default: url = '/blog/selectPage'
      // }
      this.$request.get(url, {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          categoryName: this.categoryName === '全部公告' ? null : this.categoryName,
          title: this.title
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    handleCurrentChange(pageNum) {
      this.loadNotices(pageNum)
    },

    reset(){
      this.title=null
      this.loadNotices(1)
    }
  }
}
</script>

<style scoped>
.blog-box {
  display: flex;
  grid-gap: 15px;
  padding: 10px 0;
  border-bottom: 1px solid #ddd;
}
.blog-box:first-child {
  padding-top: 0;
}
.blog-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 10px;
  cursor: pointer;
}
.blog-title:hover {
  color: #2a60c9;
}
</style>
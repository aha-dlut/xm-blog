<template>
  <div class="main-content">
    <div style="display: flex; grid-gap: 10px">

      <div style="flex: 1; width: 0">
        <div class="card" style="padding: 30px; margin-bottom: 10px">
          <div style="font-weight: bold; font-size: 24px; margin-bottom: 20px">{{ notice.title }}</div>
          <div style="color: #666; margin-bottom: 20px">
            <span style="margin-right: 20px"><i class="el-icon-user"></i> {{ notice.user }}</span>
            <span style="margin-right: 20px"><i class="el-icon-date"></i> {{ notice.time }}</span>

          </div>

          <div class="w-e-text">
            <div v-html="notice.content"></div>
          </div>

        </div>

      </div>

    </div>

  </div>
</template>

<script>


export default {
  name: "NoticeDetail",
  components: {
  },
  data() {
    return {
      noticeId: this.$route.query.noticeId,
      notice: {},
    }
  },
  created() {
    this.load()
  },
  methods: {

    load() {
      this.$request.get('/notice/selectById/' + this.noticeId).then(res => {
        this.notice = res.data || {}
        console.log(this.notice.title)
        console.log(this.notice.content)
        console.log(this.notice.time)
      })
    }
  }
}
</script>

<style>
/* blockquote 样式 */
blockquote {
  display: block;
  border-left: 8px solid #d0e5f2;
  padding: 20px 10px;
  margin: 10px 0;
  line-height: 1.4;
  font-size: 100%;
  background-color: #f1f1f1;
}

/* code 样式 */
code {
  display: inline-block;
  *display: inline;
  *zoom: 1;
  background-color: #f1f1f1;
  border-radius: 3px;
  padding: 3px 5px;
  margin: 0 3px;
}
pre code {
  display: block;
}
p {
  line-height: 30px
}

.active {
  color: orange !important;
}
.recommend-title {
  margin-bottom: 5px;
}
.recommend-title:hover {
  color: #2a60c9;
}
.comment-active {
  color: #2a60c9;
}
pre {
  white-space: pre-wrap; /*css-3*/
  white-space: -moz-pre-wrap; /*Mozilla,since1999*/
  white-space: pre-wrap; /*Opera4-6*/
  white-space: -o-pre-wrap; /*Opera7*/
  word-wrap: break-word; /*InternetExplorer5.5+*/
}
</style>
<template>
  <div style="width: 50%; margin: 5px auto">
    <div class="card" style="margin-bottom: 100px">
      <div style="font-weight: bold; font-size: 24px; margin-bottom: 50px">发表新树洞/编辑树洞</div>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="descr">
          <el-input type="textarea" v-model="form.descr" placeholder="简介"></el-input>
        </el-form-item>
        <el-form-item label="封面" prop="cover">
          <el-upload
              :action="$baseUrl + '/files/upload'"
              :headers="{ token: user.token }"
              list-type="picture"
              :on-success="handleCoverSuccess"
          >
            <el-button type="primary">上传封面</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="分类" prop="categoryId">
          <el-select v-model="form.categoryId" style="width: 100%">
            <el-option v-for="item in categoryList" :key="item.id" :value="item.id" :label="item.name"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="内容" prop="content">
          <div id="editor"></div>
        </el-form-item>
      </el-form>
      <div style="text-align: center"><el-button type="primary" size="medium" style="width: 100px" @click="save">保 存</el-button></div>
    </div>
  </div>
</template>

<script>
import E from "wangeditor";
import hljs from "highlight.js";

export default {
  name: "NewBlog",
  data() {
    return {
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {},
      categoryList: [],
      editor: null,
      blogId: this.$route.query.blogId
    }
  },
  mounted() {
    this.$request.get('/category/selectAll').then(res => {
      this.categoryList = res.data || []
    })

    this.$request.get('/blog/selectById/' + this.blogId).then(res => {
      this.form = res.data || {}
      if (this.form.id) {

        setTimeout(() => {
          this.editor.txt.html(this.form.content)
        }, 0)
      }
    })

    this.setRichText()
  },
  methods: {
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate((valid) => {
        if (valid) {

          this.form.content = this.editor.txt.html()
          this.$request({
            url: this.form.id ? '/blog/update' : '/blog/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              setTimeout(()=>{
                window.location.href='/front/person'
              },1000)
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })
    },
    handleCoverSuccess(res) {
      this.form.cover = res.data
    },
    setRichText() {
      this.$nextTick(() => {
        this.editor = new E(`#editor`)
        this.editor.highlight = hljs
        this.editor.config.uploadImgServer = this.$baseUrl + '/files/editor/upload'
        this.editor.config.uploadFileName = 'file'
        this.editor.config.uploadImgHeaders = {
          token: this.user.token
        }
        this.editor.config.uploadImgParams = {
          type: 'img',
        }
        this.editor.config.zIndex = 0
        this.editor.create()  // 创建
      })
    },
  }
}
</script>

<style scoped>

</style>
<template>
  <div>

    <div class="search">
      <el-input placeholder="请输入商品名查询" style="width: 200px; margin-right: 10px" v-model="name"></el-input>
      <el-input placeholder="请输入分类查询" style="width: 200px; margin-right: 10px" v-model="type"></el-input>
      <el-input placeholder="请输入卖家名称查询" style="width: 200px" v-model="userName"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="add">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>


    <div class="table">
      <el-table :data="tableData" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="name" label="商品名" show-overflow-tooltip></el-table-column>
        <el-table-column prop="cover" label="封面">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 50px; height: 50px; border-radius: 5px" v-if="scope.row.cover"
                        :src="scope.row.cover" :preview-src-list="[scope.row.cover]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="type" label="分类"></el-table-column>

        <el-table-column prop="userName" label="发布人"></el-table-column>
        <el-table-column prop="date" label="发布日期"></el-table-column>
        <el-table-column label="查看商品描述">
          <template v-slot="scope">
            <el-button @click="preview(scope.row.descr)">查看</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="300">
          <template v-slot="scope">
            <el-button v-if="scope.row.sell" size="mini" type="warning" plain @click="handleSoldChange(scope.row)">下架</el-button>
            <el-button v-else size="mini" type="primary" plain @click="handleSoldChange(scope.row)">上架</el-button>
            <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
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

    <el-dialog title="信息" :visible.sync="fromVisible" width="60%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="商品名" prop="name">
          <el-input v-model="form.name" placeholder="商品名"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input type="number" v-model="form.price" placeholder="价格"></el-input>
        </el-form-item>
        <!--        <el-form-item label="商品描述" prop="descr">-->
        <!--          <el-input type="textarea" v-model="form.descr" placeholder="商品描述"></el-input>-->
        <!--        </el-form-item>-->

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
        <el-form-item label="联系方式" prop="association">
          <el-input type="textarea" v-model="form.association" placeholder="联系方式"></el-input>
        </el-form-item>
        <el-form-item label="分类" prop="type">
          <el-select v-model="form.type" style="width: 100%">
            <el-option v-for="item in categoryList" :key="item.name" :value="item.value" :label="item.name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品描述" prop="descr">
          <div id="editor"></div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>

    </el-dialog>

    <el-dialog title="商品详细描述" :visible.sync="fromVisible1" width="50%" :close-on-click-modal="false" destroy-on-close>
      <div class="w-e-text">
        <div v-html="descr"></div>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible1 = false">关 闭</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import E from "wangeditor";
import hljs from "highlight.js";

export default {
  name: "goods",

  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      name: null,
      type: null,
      userName: null,
      descr: '',
      editor: null,
      categoryList: [{"name": "日用品", "value": "日用品"}, {"name": "学习", "value": "学习"}, {
        "name": "数码",
        "value": "数码"
      }, {"name": "其他", "value": "其他"}],
      fromVisible: false,
      fromVisible1: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        title: [
          {required: true, message: '请输入标题', trigger: 'blur'},
        ],
        content: [
          {required: true, message: '请输入内容', trigger: 'blur'},
        ]
      },
    }
  },

  created() {
    this.load(1)
  },
  mounted() {


  },
  methods: {
    preview(descr) {
      this.descr = descr
      this.fromVisible1 = true
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.setRichText()
      this.fromVisible = true   // 打开弹窗
      setTimeout(() => {
        this.editor.txt.html(this.form.descr)
      }, 0)

    },
    add() {
      this.form = {}  // 新增数据的时候清空数据
      this.setRichText()
      this.fromVisible = true;

    },
    save() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.form.descr = this.editor.txt.html()
          this.$request({
            url: this.form.id ? '/goods/update' : '/goods/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              this.load(1)
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })

    },

    load(pageNum) {  // 分页查询
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/goods/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          type: this.type,
          userName: this.userName,
        }
      }).then(res => {
        console.log(res.data)
        this.tableData = res.data?.list
        this.total = res.data?.total
      })

    },

    handleSoldChange(row){
      this.$request.post("/goods/changeSell/"+row.id).then(res=>{
        if (res.code === '200') {   // 表示操作成功
          this.$message.success('操作成功')
          this.load(1)
        } else {
          this.$message.error(res.msg)  // 弹出错误的信息
        }
      })

    },

    handleCoverSuccess(res) {
      this.form.cover = res.data
    },
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)   //  [1,2]
    },
    reset() {
      this.title = null
      this.categoryName = null
      this.userName = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/goods/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/goods/delete/batch', {data: this.ids}).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
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
        this.editor.create()  // 创建
      })
    },


  }
}
</script>

<style scoped>

</style>
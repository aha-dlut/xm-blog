<template>
  <div style=" width: 90%;
    margin: 5px auto;">

    <div class="search">
      <el-input placeholder="请输入商品名查询" style="width: 200px; margin-right: 10px" v-model="name"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
      <el-button type="primary" v-if="type==='user'" plain @click="add">新增</el-button>
    </div>

    <el-row :gutter="10" v-if="total" style="padding-top: 10px">
      <el-col :span="span" v-for="item in tableData" :key="item.id">
        <div class="card" style="margin-bottom: 10px" @click="goDetail(item.id)">
          <img :src="item.cover" alt="" style="width: 100%; height: 200px; border-radius: 5px">
          <div style="margin: 10px 0; font-weight: bold">{{ item.name }}</div>
          <div style="display: flex; align-items: center">
            <div style="flex: 1; color: #666"><i class="el-icon-date"></i> {{ item.date }}</div>
            <div style=" padding-right: 2px;color: #e50f0f">￥{{ item.price }}</div>
            <div v-if="type==='user'">
              <el-button size="mini" type="primary" plain @click="handleEdit(item)">编辑</el-button>
              <el-button v-if="item.sell" size="mini" type="warning" plain @click="handleSoldChange(item)">下架
              </el-button>
              <el-button v-else size="mini" type="primary" plain @click="handleSoldChange(item)">上架</el-button>
            </div>
            <div v-else>
              <el-button type="primary" @click="preview(item)" v-if="item.sell===true">查看详情</el-button>
              <el-button type="primary"  v-else-if="item.sell===false" disabled >已下架</el-button>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
    <div class="card" v-if="total === 0" style="text-align: center; font-size: 16px; color: #666">暂无数据</div>
    <div class="card" style="padding: 10px" v-if="total">
      <el-pagination
          background
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-size="pageSize"
          layout="total, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>
    <el-dialog title="商品详细描述" :visible.sync="fromVisible1" width="50%" :close-on-click-modal="false" destroy-on-close>
      <div class="w-e-text">
        <div style="font-size: 50px;font-weight: bolder;color: #000b17">{{this.previewName}}</div>
        <div style="font-size: 20px;color: #000b17">价格:{{this.previewPrice}}</div>
        <div style="font-size: 20px;color: #000b17">卖家:{{this.previewUserName}}</div>
        <div style="font-size: 20px;color: #000b17;padding-bottom: 50px">联系方式:{{this.previewAssociation}}</div>
        <hr>
        <div style="font-size: 20px;">商品描述:</div>
        <div v-html="descr"></div>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="previewClose">关 闭</el-button>
      </div>
    </el-dialog>

    <el-dialog title="信息" :visible.sync="fromVisible" width="60%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="商品名" prop="name">
          <el-input v-model="form.name" placeholder="商品名"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input type="number" v-model="form.price" placeholder="价格"></el-input>
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
        <el-form-item label="联系方式" prop="association">
          <el-input type="textarea" v-model="form.association" placeholder="联系方式"></el-input>
        </el-form-item>
        <el-form-item label="分类" prop="type">
          <el-select v-model="form.type" style="width: 100%">
            <el-option-group v-for="category in categoryList" :key="category.name" :label="category.name">
              <el-option v-for="subCategory in category.subCategories" :key="subCategory.name"
                         :value="subCategory.value" :label="subCategory.name"></el-option>
            </el-option-group>
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

  </div>
</template>

<script>
import E from "wangeditor";
import hljs from "highlight.js";

export default {
  name: "goods",
  props: {
    type: 'all',
    span: null,
    userId:null,
  },
  watch:{
    type() {
      this.load(1)
    }
  },
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 8,  // 每页显示的个数
      total: 0,
      name: null,
      userName: null,
      descr: '',
      editor: null,
      good:null,

      //用于预览商品全部信息
      previewName:'',
      previewUserName:'',
      previewPrice:'',
      previewAssociation:'',

      fromVisible: false,
      fromVisible1: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      categoryList: [{
        "name": "日用品",
        "value": "日用品",
        "subCategories": [{"name": "零食速食", "value": "零食速食"}, {"name": "个人清洁", "value": "个人清洁"}, {
          "name": "卫生用具",
          "value": "卫生用具"
        }, {"name": "体育运动", "value": "体育运动"}, {"name": "其他日用品", "value": "其他日用产品"}]
      }, {
        "name": "学习用品",
        "value": "学习用品",
        "subCategories": [{"name": "书本资料", "value": "书本资料"}, {"name": "文具", "value": "文具"}, {
          "name": "其他学习产品",
          "value": "其他学习产品"
        }]
      }, {
        "name": "电脑数码",
        "value": "电脑数码",
        "subCategories": [{"name": "手机平板", "value": "手机平板"}, {"name": "电脑", "value": "电脑"}, {
          "name": "耳机",
          "value": "耳机"
        }, {"name": "相机", "value": "相机"}, {"name": "网络设备", "value": "网络设备"}, {"name": "其他电子产品", "value": "其他电子产品"}]
      }, {
        "name": "其他",
        "value": "其他",
        "subCategories": [{"name": "其他", "value": "其他"}]
      }],
    }
  },

  created() {
    this.load(1)
  },
  mounted() {


  },
  methods: {
    preview(item) {
      this.fromVisible1 = true
      this.descr=item.descr
      this.previewName=item.name
      this.previewUserName=item.userName
      this.previewPrice=item.price
      this.previewAssociation=item.association
    },
    previewClose(){
      this.fromVisible1=false
      this.previewUserName=''
      this.previewPrice=''
      this.previewAssociation=''
      this.previewName=''

    },
    handleEdit(item) {   // 编辑数据
      this.form = item  // 给form对象赋值  注意要深拷贝数据
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
      console.log(this.type)
      console.log(this.userId)
      this.$request.get('/goods/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          type: this.type==='all'||this.type==='user'?null:this.type,
          userId:this.type==='user'?this.userId:null,
          userName: this.userName,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })

    },

    handleSoldChange(row) {
      this.$request.post("/goods/changeSell/" + row.id).then(res => {
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
      this.name = null
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
<template>
  <div>

    <div class="search">
      <el-input placeholder="请输入名称查询" style="width: 200px" v-model="name"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>
    <div style="padding: 15px; border-bottom: 1px solid #ddd">
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>


    <div class="table">
      <el-table :data="tableData" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="name" label="文件名称"></el-table-column>
        <el-table-column prop="folder" label="是否文件夹"></el-table-column>
        <el-table-column prop="file" label="文件路径" show-overflow-tooltip></el-table-column>
        <el-table-column prop="type" label="文件类型"></el-table-column>
        <el-table-column prop="size" label="文件大小"></el-table-column>
        <el-table-column prop="crateTime" label="创建时间"></el-table-column>
        <el-table-column prop="updateTime" label="修改时间"></el-table-column>
        <el-table-column prop="category" label="文件分类"></el-table-column>
        <el-table-column label="操作" align="center" width="180">
          <template v-slot="scope">
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


  </div>


</template>

<script>
export default {
  name: "DiskFiles",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      name: null,
      uploadUrl: '',
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      ids: [],
      current: '全部文件',
      categoryList: [{"name": "全部文件", "value": "全部文件"}, {"name": "人文社科", "value": "人文社科"}, {
        "name": "数学科学",
        "value": "数学科学"
      }, {
        "name": "工程学科",
        "value": "工程学科"
      }, {"name": "外语文件", "value": "外语文件"}, {"name": "校内文件", "value": "校内文件"}, {
        "name": "教材",
        "value": "教材"
      }, {"name": "其他文件", "value": "其他文件"}],

    }
  },
  created() {
    this.load(1)
  },
  methods: {
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/commonFiles/delete/' + id).then(res => {
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
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)   //  [1,2]
    },
    handleFileSuccess(res) {
      if (res.code === '200') {
        this.$message.success("上传成功")
        this.load()
      } else {
        this.$message.error('上传失败')
      }
    },
    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/commonFiles/delete/batch', {data: this.ids}).then(res => {
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
    load(pageNum) {  // 分页查询
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/commonFiles/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.name = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    addFolder() {
      this.tableData.unshift({name: '', type: 'folder', unSave: true, folderId: this.folderId || null})    //  unSave 控制输入框是否显示
    },
    save(row) {  // 上传文件夹的方法 和编辑后保存
      if (row.id) {  // 编辑
        this.$request.put('/commonFiles/update', row).then(res => {
          if (res.code === '200') {
            this.$message.success("操作成功")
            this.load()
          } else {
            this.$message.error(res.msg)
          }
        })
      } else {
        let url = '/commonFiles/add?folder=是&name=' + row.name
        if (this.folderId) {  // 外层的folderId
          url += '&folderId=' + this.folderId
        }
        this.$request.post(url).then(res => {
          if (res.code === '200') {
            this.$message.success("操作成功")
            this.load()
          } else {
            this.$message.error(res.msg)
          }
        })
      }

    },
  }
}
</script>

<style scoped>

</style>

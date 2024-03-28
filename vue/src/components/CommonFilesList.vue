<template>
  <div>
    <div style="padding: 15px; border-bottom: 1px solid #ddd">
      <el-upload
          style="display: inline-block; margin-right: 10px"
          :action="uploadUrl"
          :headers="{ token: user.token }"
          :show-file-list="false"
          :on-success="handleFileSuccess"
      >
        <el-button type="primary" v-if="category !='全部文件'">上传文件</el-button>
      </el-upload>
      <el-button type="primary" plain @click="addFolder" v-if="category !='全部文件'">新建文件夹</el-button>
    </div>

    <div class="search">
    <el-input placeholder="请输入文件名称查询" style="width: 200px" v-model="fileName"></el-input>
    <el-button type="info" plain style="margin-left: 10px" @click="load">查询</el-button>
    <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
  </div>
    <div style="padding: 15px;display: flex;flex-direction: row">
      <div @click="all" style="cursor: pointer;color: #77A5C6;font-size: 15px">全部文件 <i class="el-icon-arrow-right" v-if="folders.length"></i></div>
      <div v-for="(item, index) in folders" :key="item.id">
        {{ item.name }} <i class="el-icon-arrow-right" v-if="index !== folders.length - 1"></i>
      </div>
    </div>
    <div @click="changeFolderId()" style="font-size: 13px;color: #77A5C6;cursor: pointer" v-if="folders.length"><i class="el-icon-back"></i>上一页</div>

    <div>
      <el-table size="medium" stripe :data="tableData">
        <el-table-column label="名称">
          <template v-slot="scope">
            <div v-if="scope.row.unSave" >
              <i style="color: #409EFF" :class="typeList.find(v => v.text === scope.row.type)?.icon || 'el-icon-document'"></i>
              <el-input size="mini" style="width: 300px; margin: 0 5px" v-model="scope.row.name"></el-input>
              <el-button type="primary" size="mini" @click="save(scope.row)">确定</el-button>
              <el-button type="danger" size="mini" @click="cancel(scope.row)">取消</el-button>
            </div>

            <div style="display: flex; cursor: pointer" @click="openFile(scope.row)" v-else @mouseenter="mouseEnter(scope.row)" @mouseleave="mouseLeave(scope.row)">
              <div style="flex: 1">
                <i style="color: #409EFF" :class="typeList.find(v => v.text === scope.row.type)?.icon || 'el-icon-file'"></i>
                <span v-if="!scope.row.unSave" style="margin-left: 5px">{{ scope.row.name }}</span>
              </div>
              <div style="color: #409EFF; font-size: 14px" v-if="scope.row.optShow">
                <el-tooltip content="下载" effect="light" :open-delay="1000" v-if="scope.row.folder === '否'">
                  <i class="el-icon-download" style="margin-right: 10px; cursor: pointer" @click.stop="download(scope.row.file)"></i>
                </el-tooltip>
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column prop="updateTime" label="修改时间" width="300"></el-table-column>
        <el-table-column prop="size" label="文件大小（KB）" width="300"></el-table-column>
      </el-table>

    </div>
  </div>
</template>

<script>
export default {
  name: "CommonFilesList",
  props: {
    category: null,//当前如果前面没传默认是null
  },
  data(){
    return{
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      uploadUrl: '',
      ids: [],
      folders: [],
      tableData:[],
      folderId:null,
      fileName:null,
      typeList: [
        { text: 'mp3', icon: 'el-icon-video-camera' },
        { text: 'mp4', icon: 'el-icon-video-camera' },
        { text: 'jpg', icon: 'el-icon-picture-outline' },
        { text: 'jpeg', icon: 'el-icon-picture-outline' },
        { text: 'png', icon: 'el-icon-picture-outline' },
        { text: 'pdf', icon: 'el-icon-document' },
        { text: 'docx', icon: 'el-icon-document' },
        { text: 'txt', icon: 'el-icon-document' },
        { text: 'zip', icon: 'el-icon-box' },
        { text: 'folder', icon: 'el-icon-folder' },
      ],
    }
  },
  created() {

    this.load()

    this.uploadUrl = this.$baseUrl + '/commonFiles/add?folder=否'
    if(this.category!=null&&this.category!='全部文件'){
      this.uploadUrl +='&category='+this.category
    }
    if (this.folderId) {  // 如果不这么判断  就会传 undefined到后台 就会报错
      this.uploadUrl += '&folderId=' + this.folderId
    }

  },
  watch:{
    category(){
      this.uploadUrl = this.$baseUrl + '/commonFiles/add?folder=否'
      if(this.category!=null&&this.category!='全部文件'){
        this.uploadUrl +='&category='+this.category
      }
      if (this.folderId) {  // 如果不这么判断  就会传 undefined到后台 就会报错
        this.uploadUrl += '&folderId=' + this.folderId
      }
      this.folderId = null
      this.folders = []
      this.load()
    },
    folderId(){
      console.log(this.folderId)
      this.uploadUrl = this.$baseUrl + '/commonFiles/add?folder=否'
      if(this.category!=null&&this.category!='全部文件'){
        this.uploadUrl +='&category='+this.category
      }
      if (this.folderId) {  // 如果不这么判断  就会传 undefined到后台 就会报错
        this.uploadUrl += '&folderId=' + this.folderId
      }
      this.load()
    }
  },
  methods:{

    reset(){
      this.fileName = null
      this.load()
    },
    all(){
      this.folderId = null
      this.load()
    },
    changeFolderId(){
      if(this.folders.length === 0){
        this.folderId = null
      }else{
        this.folderId = this.folders[this.folders.length-1].folderId
        this.load()
      }
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
        if(this.category!=null&&this.category!='全部文件'){
          url+='&category='+this.category
        }
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
    cancel(row) {
      let index = row.$index
      if (row.id) {  // 编辑
        this.load() // 加载最新的数据即可
      } else {  // 新增
        this.tableData.splice(index, 1)
      }

    },
    addFolder() {
      this.tableData.unshift({  name: '', type: 'folder', unSave: true, folderId: this.folderId || null })    //  unSave 控制输入框是否显示
    },
    openFile(row) {
      if (row.folder === '是') { // 文件夹
         // location.href = '/front/commonFile?category=' + this.category + '&folderId=' + row.id
        this.folderId = row.id
        this.load()
      } else {
        window.open(this.$baseUrl + '/commonFiles/preview/' + row.id)
      }
    },
    mouseEnter(row) {
      if (!row.unSave) {
        this.$set(row, 'optShow', true)
      }
    },
    mouseLeave(row) {
      if (!row.unSave) {
        this.$set(row, 'optShow', false)
      }
    },
    download(url) {
      window.open(url)  // 文件下载
    },
    load() {
      this.$request.get('/commonFiles/selectAll', {
        params: {
          category: this.category!='全部文件'?this.category:null,   // all  img  video  zip
          folderId: this.folderId || null,
          name:this.fileName||null
        }
      }).then(res => {
        this.tableData = res.data || []
        this.tableData.forEach(item => {this.$set(item, 'optShow', false)   // 强制设置每行  加一个 optShow属性
        })
      })

      this.$request.get('/commonFiles/selectFolders', {
        params: { folderId: this.folderId || null }
      }).then(res => {
        this.folders = res.data || []
      })
    },
    handleFileSuccess(res) {
      if (res.code === '200') {
        this.$message.success("上传成功")
        this.load()
      } else {
        this.$message.error('上传失败')
      }
    },

  }
}
</script>

<style scoped>

</style>
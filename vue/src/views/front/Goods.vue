<template>

  <div>

    <el-row :gutter="10">

      <el-col span="2">
        <div style="   width: 100%;
    min-height: calc(100vh - 60px);">
          <el-menu default-active=this.type class="el-menu-vertical-demo" text-color="#fff"
                   active-text-color="#ffd04b" background-color="#333333" unique-opened="true">
            <el-menu-item index="all" @click="selectType('all')"><i class="el-icon-s-home"></i> <span
                slot="title">全部商品</span>
            </el-menu-item>
            <el-submenu v-for="category in categoryList" :key="category.name" :index="category.value">
              <template slot="title"><i class="el-icon-location"></i> <span>{{ category.name }}</span></template>
              <el-menu-item-group>
                <el-menu-item v-for="subCategory in category.subCategories" :key="subCategory.name"
                              :index="subCategory.value" @click="selectType(subCategory.value)">
                  {{ subCategory.name }}
                </el-menu-item>
              </el-menu-item-group>
            </el-submenu>
            <el-menu-item index="4" @click="selectType('user')"><i class="el-icon-setting"></i> <span slot="title">我的商品</span></el-menu-item>
          </el-menu>
        </div>
      </el-col>


      <el-col span="20">

        <goods-list :type=this.type :span=6 :user-id="this.user.id"></goods-list>

      </el-col>
    </el-row>


  </div>

</template>
<script>

import GoodsList from "@/components/GoodsList";
import E from "wangeditor";
import hljs from "highlight.js";

export default {
  name: "goods",
  components: {
    GoodsList
  },
  data() {
    return {
      type: null,
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


  methods: {
    loadFiles(category) {
      // location.href = '/front/diskHome?category=' + category
    },
    handleItemClickAll() {
      this.type = null;

    },
    selectType(type) {
      console.log(type)
      this.type = type
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
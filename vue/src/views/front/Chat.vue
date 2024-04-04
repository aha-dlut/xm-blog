<template>
  <div>
    <!--  中间部分  -->
    <div style="width: 100%; border: 1px solid #ddd; border-radius: 5px; background-color: #f1f1f1;">
      <div
          style="padding: 20px 0; text-align: center; border-bottom: 1px solid #ddd; color: #000; background-color: #77A5C6">
        大家正在聊
      </div>
      <div class="im-message-box">
        <div v-for="item in messages" :key="item.id">
          <!--  右边的气泡 -->
          <div style="display: flex; flex-direction: row-reverse; align-items: flex-start"
               v-if="item.name === user.name">
            <img :src="item.avatar" alt="" style="width: 40px; height: 40px; border-radius: 50%; margin-left: 10px">
            <div class="im-message im-message-right" v-html="item.content" v-if="item.type === 'text'"></div>
            <div class="im-message" style="padding: 0" v-if="item.type === 'img'">
              <!-- 注意  el-image 的load函数必须加上，否则无法触发滚动条到最底端 -->
              <el-image style="width: 100px" :src="item.content" alt="" :preview-src-list=[item.content]
                        @load="scrollToBottom"></el-image>
            </div>
            <div class="im-message im-message-download" v-if="item.type === 'file'"
                 @click="download(item.content)">
              <div><i class="el-icon-folder-opened"></i>
                <span>{{ item.content.substring(item.content.indexOf('-') + 1) }}</span>
              </div>
            </div>
          </div>

          <!--  左边的气泡 -->
          <div style="display: flex; align-items: flex-start" v-else>
            <img :src="item.avatar" alt="" style="width: 40px; height: 40px; border-radius: 50%; margin-right: 10px">
            <div style="width: 100%">
              <div style="color: #888; font-size: 12px; width: 50%">{{ item.name }}</div>
              <div class="im-message im-message-left" v-html="item.content" v-if="item.type === 'text'"></div>
              <div class="im-message" style="padding: 0" v-if="item.type === 'img'">
                <!-- 注意  el-image 的load函数必须加上，否则无法触发滚动条到最底端 -->
                <el-image style="width: 100px" :src="item.content" alt=""
                          :preview-src-list=[item.content]
                          @load="scrollToBottom"></el-image>
              </div>
              <div class="im-message im-message-download" v-if="item.type === 'file'"
                   @click="download(item.content)">
                <div><i class="el-icon-folder-opened"></i>
                  <span>{{ item.content.substring(item.content.indexOf('-') + 1) }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 输入区域 -->
      <div style="padding: 10px 5px; border-top: 1px solid #ddd; display: flex; align-items: center; width: 100%;">
        <!--        <el-popover placement="top" width="300" trigger="click">-->
        <!--          <div class="emoji-box">-->
        <!--            <span v-for="(item, index) in emojis" :key="index"-->
        <!--                  style="margin-right: 5px; font-size: 20px; cursor: pointer" v-html="item"-->
        <!--                  @click="clickEmoji(item)"></span>-->
        <!--          </div>-->
        <!--          <i slot="reference" class="fa fa-smile-o" style="font-size: 22px; color: #666;"></i>-->
        <!--        </el-popover>-->
        <!--        <div style="margin-left: 5px">-->
        <!--          <el-upload action="http://localhost:8080/files/upload" :show-file-list="false" :on-success="handleFile">-->
        <!--            <i class="fa fa-folder-open-o" style="font-size: 20px; color: #666;"></i>-->
        <!--          </el-upload>-->
        <!--        </div>-->
        <div id="im-content" contenteditable
             style="flex: 1; background-color: #fff; margin: 0 5px; padding: 10px; border: 1px solid #ddd; border-radius: 2px; outline: none; font-size: 14px;"></div>
        <el-button type="primary" @click="send" style="border: none">发送</el-button>
      </div>
    </div>
    <!--  中间部分结束  -->
  </div>


</template>

<script>

let client
export default {
  name: "Chat",


  data() {
    return {

      user: JSON.parse(localStorage.getItem("xm-user") || '{}'),

      messages: []


    }
  },


  mounted() {
    // let username = this.user.username
    // let avatar = this.user.avatar
    // let url = 'ws://localhost:9090/chat/' + username + '/' + avatar
    // client = new WebSocket(url)
    let username = window.btoa(encodeURI(this.user.username))
    let avatar = window.btoa(encodeURI(this.user.avatar))
    client = new WebSocket(`ws://192.168.31.73:9090/chat/${username}/${avatar}`)
    // client = new WebSocket(`ws://152.136.253.71:9090/chat/${username}/${avatar}`)
    client.onopen = () => {
      console.log('websocket open')
    }
    client.onclose = () => {  // 页面刷新的时候和后台websocket服务关闭的时候
      console.log('websocket close')
    }
    client.onmessage = (msg) => {
      if (msg.data) {
        let message = JSON.parse(msg.data)
        this.messages.push(message)

        this.scrollToBottom()
      }
    }
// 加载聊天数据
  },
  methods: {
    scrollToBottom() {

      this.$nextTick(() => {

        // 设置聊天滚动条到底部

        let imMessageBox = document.getElementsByClassName("im-message-box")[0]

        //设置滚动条到最底部

        imMessageBox.scrollTop = imMessageBox.scrollHeight

      })

    },
    send() {

      let inputBox = document.getElementById('im-content')

      const content = inputBox.innerHTML

      if (!content) {

        this.$notify.error('不能发送空的内容')

        return

      }
      if (client) {

        let message = {
          username: this.user.username,
          content: content,
          avatar: this.user.avatar,
          name: this.user.name,
          type: 'text'
        }

        client.send(JSON.stringify(message))

      }

      inputBox.innerHTML = ''  // 清空输入框
    },


  },
  beforeDestroy() {

    if (client) {

      client.close()

    }

  },

}

</script>

<style scoped>
.im-message-box {

  height: 50vh;

  padding: 10px;

  overflow-y: auto;

  background-color: white;

}

.emoji-box {

  height: 200px;

  overflow-y: scroll;

  line-height: 30px;

}

.user-list-box {

  height: calc(50vh + 60px);

  overflow-y: scroll;

  background-color: #f8f8ff;

}

.im-message-box::-webkit-scrollbar, .emoji-box::-webkit-scrollbar, .user-list-box::-webkit-scrollbar {

  width: 4px;

}

.im-message-box::-webkit-scrollbar-thumb, .emoji-box::-webkit-scrollbar-thumb, .user-list-box::-webkit-scrollbar-thumb {

  border-radius: 10px;

  background: rgba(0, 0, 0, 0.1);

}

.im-message-box::-webkit-scrollbar-track, .emoji-box::-webkit-scrollbar-track, .user-list-box::-webkit-scrollbar-track {

  border-radius: 0;

  background: rgba(0, 0, 0, 0.1);

}

.im-message {

  font-size: 14px;

  padding: 10px;

  margin: 5px 0;

  border-radius: 5px;

  color: #000;

  max-width: 50%;

  line-height: 20px;

  width: fit-content;

}

.im-message-left {

  background-color: #fff;

}

.im-message-right {

  background-color: #26d470;

}

.im-message-download {

  background-color: #DBEDFF;

  cursor: pointer;

}

.user-list-item {

  padding: 10px;

  display: flex;

  align-items: center;

  border-bottom: 1px solid #eee;

  cursor: default;

  font-size: 14px;

}

.user-list-item:nth-last-child(1) {

  border: none;

}


</style>
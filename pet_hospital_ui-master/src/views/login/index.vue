<template>
  <div class="login-container">
    <el-form v-if="isLogin" ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
      <div class="title-container">
        <h3 class="title">宠物医院管理系统登录</h3>
      </div>
      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="用户名"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>
      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="密码"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <el-row>
        <el-col :span="12">
          <el-button :loading="loading" type="primary" style="width:90%;margin-bottom:30px;" @click.native.prevent="handleLogin">登录</el-button>
        </el-col>
        <el-col :span="12">
          <el-button type="text" style="width:90%;margin-bottom:30px;" @click.native.prevent="isRegister = true; isLogin = false">没有账号？去注册</el-button>
        </el-col>
      </el-row>
    </el-form>
    <el-form v-if="isRegister" ref="registerForm" :model="registerForm" :rules="registerRules" class="login-form" auto-complete="on" label-position="left">
      <div class="title-container">
        <h3 class="title">宠物医院管理系统注册</h3>
      </div>
      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          v-model="registerForm.username"
          placeholder="用户名"
          name="username"
          type="text"
          maxlength="12"
          show-word-limit
          auto-complete="on"
        />
      </el-form-item>
      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          v-model="registerForm.password"
          :type="passwordType"
          placeholder="密码"
          maxlength="18"
          show-word-limit
          auto-complete="on"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <el-form-item prop="confirmPassword">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          v-model="registerForm.confirmPassword"
          :type="passwordType"
          placeholder="确认密码"
          maxlength="18"
          show-word-limit
          auto-complete="on"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <el-form-item prop="name">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          v-model="registerForm.name"
          placeholder="姓名"
          type="text"
          auto-complete="on"
        />
      </el-form-item>
      <el-form-item prop="sex">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-radio-group v-model="registerForm.sex">
          <el-radio label="男">男</el-radio>
          <el-radio label="女">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item prop="idCard">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          v-model="registerForm.idCard"
          placeholder="身份证号"
          name="idCard"
          type="text"
          maxlength="18"
          show-word-limit
          auto-complete="on"
        />
      </el-form-item>
      <el-form-item prop="phone">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          v-model="registerForm.phone"
          placeholder="手机号"
          name="phone"
          type="text"
          maxlength="11"
          show-word-limit
          auto-complete="on"
        />
      </el-form-item>
      <el-form-item prop="address">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          v-model="registerForm.address"
          placeholder="地址"
          type="text"
          auto-complete="on"
        />
      </el-form-item>
      <el-row>
        <el-col :span="12">
          <el-button :loading="loading" type="primary" style="width:90%;margin-bottom:30px;" @click.native.prevent="handleRegister">注册</el-button>
        </el-col>
        <el-col :span="12">
          <el-button type="text" style="width:90%;margin-bottom:30px;" @click.native.prevent="isRegister = false; isLogin = true">已有账号？去登录</el-button>
        </el-col>
      </el-row>
    </el-form>

    <div style="width: 100%;">
      <video-player
        ref="videoPlayer"
        :playsinline="true"
        style="width: 20%; margin: 0 auto;"
        :options="playerOptions"
      />
    </div>
  </div>
</template>

<script>

import { register } from '@/api/user'

export default {
  name: 'Login',
  data() {
    const checkPhone = (rule, value, callback) => {
      if (!/^(?:(?:\+|00)86)?1(?:(?:3[\d])|(?:4[5-7|9])|(?:5[0-3|5-9])|(?:6[5-7])|(?:7[0-8])|(?:8[\d])|(?:9[1|8|9]))\d{8}$/.test(value)) {
        callback(new Error('请输入正确的手机号'))
      } else {
        callback()
      }
    }
    const checkUsername = (rule, value, callback) => {
      if (!/^[a-zA-Z0-9_-]{6,12}$/.test(value)) {
        callback(new Error('请输入6到12位的字符'))
      } else {
        callback()
      }
    }
    const checkPassword = (rule, value, callback) => {
      if (!/^.{6,18}$/.test(value)) {
        callback(new Error('请输入6到18位的字符'))
      } else {
        callback()
      }
    }
    const checkConfirmPassword = (rule, value, callback) => {
      if (this.registerForm.password !== value) {
        callback(new Error('两次密码不一致'))
      } else {
        callback()
      }
    }
    const checkIdCard = (rule, value, callback) => {
      if (!/^[1-9]\d{5}(?:18|19|20)\d{2}(?:0\d|10|11|12)(?:0[1-9]|[1-2]\d|30|31)\d{3}[\dXx]$/.test(value)) {
        callback(new Error('请输入正确的身份证号'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        username: 'zhangsan',
        password: '123456'
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', message: '请输入用户名' }],
        password: [{ required: true, trigger: 'blur', message: '请输入密码' }]
      },
      registerForm: {
        username: '',
        password: '',
        confirmPassword: '',
        name: '',
        sex: '男',
        idCard: '',
        phone: '',
        address: ''
      },
      registerRules: {
        username: [{ required: true, trigger: 'blur', message: '请输入用户名' }, { validator: checkUsername, trigger: 'blur' }],
        password: [{ required: true, trigger: 'blur', message: '请输入密码' }, { validator: checkPassword, trigger: 'blur' }],
        confirmPassword: [{ required: true, trigger: 'blur', message: '请确认密码' }, { validator: checkConfirmPassword, trigger: 'blur' }],
        idCard: [{ required: true, trigger: 'blur', message: '请输入身份证号' }, { validator: checkIdCard, trigger: 'blur' }],
        phone: [{ required: true, trigger: 'blur', message: '请输入手机号' }, { validator: checkPhone, trigger: 'blur' }],
        address: [{ required: true, trigger: 'blur', message: '请输入地址' }]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined,
      isRegister: false,
      isLogin: true,
      playerOptions: {
        // 播放速度
        playbackRates: [0.5, 1.0, 1.5, 2.0],
        // 如果true,浏览器准备好时开始回放。
        autoplay: true,
        // 默认情况下将会消除任何音频。
        muted: true,
        // 导致视频一结束就重新开始。
        loop: false,
        // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
        preload: 'auto',
        language: 'zh-CN',
        // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
        aspectRatio: '16:9',
        // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
        fluid: true,
        sources: [{
          // 类型
          type: 'video/mp4',
          // url地址
          src: 'https://upos-sz-mirror08c.bilivideo.com/upgcxcode/71/20/1313762071/1313762071-1-192.mp4?e=ig8euxZM2rNcNbN1nWdVhwdlhbRHhwdVhoNvNC8BqJIzNbfq9rVEuxTEnE8L5F6VnEsSTx0vkX8fqJeYTj_lta53NCM=&uipk=5&nbs=1&deadline=1750233573&gen=playurlv2&os=08cbv&oi=1741553087&trid=d0894020531e4dd1bc5a8a181761fca0T&mid=38846785&platform=html5&og=hw&upsig=bee0265c71b4db6eb3cae01ae3d4b4f0&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,mid,platform,og&bvc=vod&nettype=0&bw=220597&orderid=0,1&buvid=&build=0&mobi_app=&f=T_0_0&logo=80000000'
        }],
        // 你的封面地址
        poster: '',
        // 允许覆盖Video.js无法播放媒体源时显示的默认信息。
        notSupportedMessage: '此视频暂无法播放，请稍后再试',
        controlBar: {
          timeDivider: true,
          durationDisplay: true,
          remainingTimeDisplay: false,
          // 全屏按钮
          fullscreenToggle: true
        }}
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login', this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || '/' })
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    handleRegister() {
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          register(this.registerForm).then(res => {
            this.$message.success(res.body.data)
            this.registerForm = {
              username: '',
              password: '',
              confirmPassword: '',
              name: '',
              sex: '男',
              idCard: '',
              phone: '',
              address: ''
            }
            this.isLogin = true
            this.isRegister = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg:#283443;
$light_gray:#fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
  .demo{
    display: inline-block;
    width: 100%;
    height: 100px;
    text-align: center;
    line-height: 100px;
    border: 1px solid transparent;
    border-radius: 4px;
    overflow: hidden;
    background: #fff;
    position: relative;
    box-shadow: 0 1px 1px rgba(0, 0, 0, .2);
    margin-right: 4px;
  }
}
</style>

<template>
  <el-form :model="ruleForm" :rules="rules" class="login-container" label-position="left" label-width="0px" ref="ruleForm">
    <!--<h3 class="login_title">login</h3>-->
    <div class="m-logo">
      <img src="https://vuejs.org/images/logo.png">
    </div>
    <el-form-item prop="username">
      <el-input type="text" v-model="ruleForm.username" auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" v-model="ruleForm.password" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button type="primary"  @click="submitForm('ruleForm')" style="width: 100%">登录</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      ruleForm: {
        username: '',
        password: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'change' }
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const _this = this
          this.$axios.post('http://localhost:8081/login', this.ruleForm).then(res => {
            console.log(res.data)
            const jwt = res.headers['authorization']
            const userInfo = res.data.data
            // 把数据共享出去
            _this.$store.commit("SET_TOKEN", jwt)
            _this.$store.commit("SET_USERINFO", userInfo)
            // 获取
            console.log(_this.$store.getters.getUser)
            _this.$router.push("/blogs")
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>
/*logo图片样式*/
.m-logo img{
  width: 68px;
  padding-left: 133px;
  padding-bottom: 20px;
}
/*登录表单容器样式*/
.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 120px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
</style>
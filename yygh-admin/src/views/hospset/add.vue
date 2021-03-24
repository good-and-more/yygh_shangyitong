<template>
  <div class="app-container">
      <div style="margin-bottom:20px" v-if="flag">医院设置添加</div>
      <div style="margin-bottom:20px" v-else>医院设置编辑</div>
      <el-form label-width="120px">
         <el-form-item label="医院名称">
            <el-input v-model="hospitalSet.hosname"/>
         </el-form-item>
         <el-form-item label="医院编号">
            <el-input v-model="hospitalSet.hoscode"/>
         </el-form-item>
         <el-form-item label="api基础路径">
            <el-input v-model="hospitalSet.apiUrl"/>
         </el-form-item>
         <el-form-item label="联系人姓名">
            <el-input v-model="hospitalSet.contactsName"/>
         </el-form-item>
         <el-form-item label="联系人手机">
            <el-input v-model="hospitalSet.contactsPhone"/>
         </el-form-item>
         <el-form-item>
            <el-button type="primary" @click="saveOrUpdate">保存</el-button>
         </el-form-item>
      </el-form>

  </div>
</template>

<script>
// 引入接口定义的js文件
import hospset from '@/api/hospset'

export default {
// 定义变量和初始值
  data() {
    return {
        hospitalSet: {},
        flag: true
    }
  },
  created() {
    // 一般都是调用methods定义的方法得到数据
    // 获取路由中的id值，调用接口得到信息并回填
    if(this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id
        this.flag = false
        this.getHospSet(id)
    } else {
        this.hospitalSet = {}
        this.flag = true
    }
  },
  methods: {
    getHospSet(id) {
        hospset.getHospSet(id)
        .then(response => {
            this.hospitalSet = response.data
        })
    },
    save() {
        hospset.saveHospitalSet(this.hospitalSet)
        .then(response => {
            //提示信息
            this.$message({
                type: 'success',
                message: '添加成功!'
            })
            //跳转页面列表，路由跳转功能
            this.$router.push({path:'/hospSet/list'})
        })
    },
    update() {
        hospset.updateHospitalSet(this.hospitalSet)
        .then(response => {
            //提示信息
            this.$message({
                type: 'success',
                message: '修改成功!'
            })
            //跳转页面列表，路由跳转功能
            this.$router.push({path:'/hospSet/list'})
        })
    },
    saveOrUpdate() {
        if(this.hospitalSet.id) {
            this.update()
        } else {
            this.save()
        }
    }
  }
}
</script>
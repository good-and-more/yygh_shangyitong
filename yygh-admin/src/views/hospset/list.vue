<template>
  <div class="app-container">
      医院设置列表
  </div>
</template>

<script>
// 引入接口定义的js文件
import hospset from '@/api/hospset'

export default {
// 定义变量和初始值
  data() {
    return {
        currentPage: 1,
        pageSize: 3,
        hospitalSetQueryVo:{},
        list:[],//每页数据的集合
        total: 0
    }
  },
  created() {
    // 一般都是调用methods定义的方法得到数据
    this.getList()
  },
  methods: {
    //   定义方法，进行请求接口调用
    getList() {
        hospset.getHospSetList(this.currentPage,this.pageSize,this.hospitalSetQueryVo)
        .then(response => {
            // 返回集合赋值list
            this.list = response.data.records
            this.total = response.data.total
            console.log(this.list)
            console.log(this.total)
            console.log(response)
            })
        .catch(error => {console.log(error)})
    }
    
  }
}
</script>
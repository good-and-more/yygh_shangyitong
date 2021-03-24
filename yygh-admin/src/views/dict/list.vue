<template>
  <div class="app-container">
  <div style="margin-bottom:20px">数据字典</div>
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="hospitalSetQueryVo.hosname" placeholder="医院名称"/>
      </el-form-item>
      <el-form-item>
        <el-input v-model="hospitalSetQueryVo.hoscode" placeholder="医院编号"/>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
    </el-form>
    <div>
      <el-button type="danger" size="mini" @click="batchDelHospsetByIds()">批量删除</el-button>
    </div>

    <!-- banner列表 -->
    <el-table :data="list" stripe style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"/>
      <el-table-column type="index" width="50" label="序号"/>
      <el-table-column prop="hosname" label="医院名称" />
      <el-table-column prop="hoscode" label="医院编号" />
      <el-table-column prop="apiUrl" label="api基础路径" width="200"/>
      <el-table-column prop="contactsName" label="联系人姓名" />
      <el-table-column prop="contactsPhone" label="联系人手机" />
      <el-table-column label="状态" width="80">
        <template slot-scope="scope">
                  {{ scope.row.status === 1 ? '可用' : '不可用' }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="400">
      <template slot-scope="scope">
        <router-link :to="'/hospSet/edit/'+scope.row.id">
          <el-button type="primary" size="mini" icon="el-icon-edit">编辑</el-button>
        </router-link>
        <el-button v-if="scope.row.status==1" type="primary" size="mini" icon="el-icon-delete" @click="lockHostSet(scope.row.id,0)">锁定</el-button>
        <el-button v-if="scope.row.status==0" type="danger" size="mini" icon="el-icon-delete" @click="lockHostSet(scope.row.id,1)">取消锁定</el-button>
        <el-button size="mini" type="danger" icon="el-icon-delete" @click="removeHospsetById(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      :current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
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
        pageSize: 5,
        hospitalSetQueryVo:{},
        list:[],//每页数据的集合
        total: 0,
        multipleSelection:[]
    }
  },
  created() {
    // 一般都是调用methods定义的方法得到数据
    this.getList()
  },
  methods: {
    // 定义方法，进行请求接口调用
    getList(page=1) {
      this.currentPage = page
      hospset.getHospSetList(this.currentPage,this.pageSize,this.hospitalSetQueryVo)
      .then(response => {
          // 返回集合赋值list
          this.list = response.data.records
          this.total = response.data.total
          })
      .catch(error => {console.log(error)})
    },
    removeHospsetById(id) {
      this.$confirm('此操作将永久删除医院是设置信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
      .then(() => { //确定执行then方法
        //调用接口
        hospset.removeHospsetById(id)
        .then(response => {
          //提示
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          //刷新页面
          this.getList(1)
        })
      })
    },
    // 批量删除分两步，第一步，获取复选框的ids
    // 第二步，调用批量删除方法
    handleSelectionChange(selection) {
      this.multipleSelection = selection
    },
    batchDelHospsetByIds(ids) {
      this.$confirm('此操作将批量永久删除医院是设置信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
      .then(() => { //确定执行then方法
        var ids = []
        // 遍历数组得到id值并存入ids里面
        for (let index = 0; index < this.multipleSelection.length; index++) {
          var obj = this.multipleSelection[index]
          ids.push(obj.id)
        }
        //调用接口
        hospset.batchDelHospsetByIds(ids)
        .then(response => {
          //提示
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          //刷新页面
          this.getList(1)
        })
      })
    },
    
    lockHostSet(id,status) {
      hospset.lockHospSet(id,status)
      .then(response => {
        this.getList()
      })
    }
  }
}
</script>
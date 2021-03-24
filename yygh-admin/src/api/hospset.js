import request from '@/utils/request'

export default {
    getHospSetList(currentPage,pageSize,hospitalSetQueryVo) {
        return request ({
            url: `/admin/hosp/hospitalSet/selectPage/${currentPage}/${pageSize}`,
            method: 'post',
            data: hospitalSetQueryVo
        })
    },
    removeHospsetById(id) {
        return request ({
            url: `/admin/hosp/hospitalSet/delete/${id}`,
            method: 'get',
        })
    },
    // 批量删除医院设置信息
    batchDelHospsetByIds(ids) {
        return request ({
            url: `/admin/hosp/hospitalSet/batchDel`,
            method: 'post',
            data: ids
        })
    },
    // 锁定与取消锁定医院
    lockHospSet(id,status) {
        return request ({
            url: `/admin/hosp/hospitalSet/lock/${id}/${status}`,
            method: 'get',
        })
    },
    // 增加医院
    saveHospitalSet(hospitalSet) {
        return request ({
            url: `/admin/hosp/hospitalSet/saveHospitalSet`,
            method: 'post',
            data: hospitalSet
        })
    },
    getHospSet (id) {
        return request ({
            url: `/admin/hosp/hospitalSet/getHospSet/${id}`,
            method: 'get',
        })
    },
    // 修改医院信息
    updateHospitalSet(hospitalSet) {
        return request ({
            url: `/admin/hosp/hospitalSet/updateHospitalSet`,
            method: 'post',
            data: hospitalSet
        })
    }
}
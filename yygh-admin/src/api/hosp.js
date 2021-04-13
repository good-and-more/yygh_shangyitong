import request from '@/utils/request'

export default {
    getHospList(page,limit,searchObj) {
        return request ({
            url: `/admin/hosp/hospital/list/${page}/${limit}`,
            method: 'get',
            params: searchObj
        })
    },
    //根据id查询下级数据字典，例如省
    findByDictCode(dictCode) {
        return request ({
            url: `/admin/cmn/dict/findByDictCode/${dictCode}`,
            method: 'get'
        })
    },
    //联动查询，根据上级id查所有子字典
    findByParendId(parentId) {
        return request ({
            url: `/admin/cmn/dict/findByParentId/${parentId}`,
            method: 'get'
        })
    },
    //更新医院上线状态
    updateHospStatus(id,status) {
        return request ({
            url: `/admin/hosp/hospital/updateHospStatus/${id}/${status}`,
            method: 'get'
        })
    },
    //查看医院详情
    getHospById(id) {
        return request ({
            url: `/admin/hosp/hospital/showHospDetail/${id}`,
            method: 'get'
        })
    },
    //查看医院科室
    getDeptByHoscode(hoscode) {
        return request ({
            url: `/admin/hosp/department/getDeptList/${hoscode}`,
            method: 'get'
        })
    },
    //查询预约规则
    getScheduleRule(page,limit,hoscode,depcode) {
        return request ({
            url: `/admin/hosp/schedule/getScheduleRule/${page}/${limit}/${hoscode}/${depcode}`,
            method: 'get',
        })
    },
    //查询排班详情
    getScheduleDetail(hoscode,depcode,workDate) {
        return request ({
            url: `/admin/hosp/schedule/getScheduleDetail/${hoscode}/${depcode}/${workDate}`,
            method: 'get'
        })
    },
}
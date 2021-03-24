import request from '@/utils/request'

export default {
    getHospSetList(currentPage,pageSize,hospitalSetQueryVo) {
        return request ({
            url: `/admin/hosp/hospitalSet/selectPage/${currentPage}/${pageSize}`,
            method: 'post',
            data: hospitalSetQueryVo
        })
    },
    
}
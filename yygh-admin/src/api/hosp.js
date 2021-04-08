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
}
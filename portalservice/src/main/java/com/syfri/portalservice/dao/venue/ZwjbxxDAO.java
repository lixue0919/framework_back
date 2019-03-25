package com.syfri.portalservice.dao.venue;

import com.syfri.baseapi.dao.BaseDAO;
import com.syfri.portalservice.model.venue.ZwjbxxVO;

import java.util.List;

public interface ZwjbxxDAO extends BaseDAO<ZwjbxxVO>{
    public List<ZwjbxxVO> doSearchListQyByVO(ZwjbxxVO vo);

    /*--企业选择的展位数量从大到小进行排序  by li.xue 2018/12/29.--*/
    List<ZwjbxxVO> doFindQyZwNumDesc(ZwjbxxVO zwjbxxVO);

    //查询企业选择的标准展位list及价格信息 add by yushch 20190116
    List<ZwjbxxVO> doFindBzzwAndJgByVo(ZwjbxxVO zwjbxxVO);
    //查询企业选择的光地展位list及价格信息 add by yushch 20190116
    List<ZwjbxxVO> doFindSngdzwAndJgByVo(ZwjbxxVO zwjbxxVO);
    //查询企业选择的OD展位及价格信息
    List<ZwjbxxVO> doFindOdAndJgByVo(ZwjbxxVO zwjbxxVO);
    /*查询记录数.*/
    public int doSearchCountExact(ZwjbxxVO zwjbxxVO);
}
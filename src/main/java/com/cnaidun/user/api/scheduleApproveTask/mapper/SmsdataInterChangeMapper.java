package com.cnaidun.user.api.scheduleApproveTask.mapper;



import com.cnaidun.user.api.scheduleApproveTask.bean.WeiXinDataInterChange;
import com.cnaidun.user.api.scheduleApproveTask.bean.WeixinSend;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

//import org.mapstruct.Mapper;

/**
 * Copyright (C), 2018-2018
 * FileName: WorkSheetMapper
 * Author:   cjle
 * Date:     2018/7/11 13:37
 * Description: 工单mapper
 * History:
 * <author>          <time>          <version>          <desc>
 * 修改者           修改时间           版本号              描述
 */

public interface SmsdataInterChangeMapper {



    @Select("select * from weixin_send where  dsend= 0")
    List<WeixinSend> selectSmsData(WeixinSend info);

    @Select("select c.opinion,c.docNO,c.status,s.workno,s.uid,s.worktypecode,s.createtime from weixin_datainterchange c,work_sheet s where  s.worknumber = c.docNO  and c.dsend=0")
    List<WeiXinDataInterChange> selectUserInfo();

    @Update("update weixin_datainterchange set dsend =1 where docNO=#{docNO}" )
    void updateStatus(WeiXinDataInterChange data);

}

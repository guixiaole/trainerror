package com.gxl.trainerror.mapper;

import com.gxl.trainerror.bean.JiCheInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface JiCheInfoMapper {
    @Select("select * from ji_che_info ")
    @Results(id="jicheInfoAll",value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "jiXing",column = "ji_xing"),
            @Result(property = "jiXingHao",column = "ji_xing_hao"),
            @Result(property = "jiCheHao",column = "ji_che_hao"),
            @Result(property = "danShuangDuan",column = "dan_shuang_duan"),
            @Result(property = "otherJiCheHao",column = "other_ji_che_hao"),
            @Result(property = "zhiDongJiName",column = "zhi_dong_ji_name"),
            @Result(property = "zhiDongJiHao",column = "zhi_dong_ji_hao"),
            @Result(property = "lieZhiRatio",column = "lie_zhi_ratio"),
            @Result(property = "stepShunXuId",column = "step_shun_xu_id"),
            @Result(property = "eventChangeId",column = "event_change_id"),
            @Result(property = "stepShunXu",column = "step_shun_xu_id",one = @One(select = "com.gxl.trainerror.mapper.StepShunXuMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "eventChange",column = "event_change_id",one = @One(select = "com.gxl.trainerror.mapper.EventChangeMapper.selectById",fetchType = FetchType.EAGER))
    })
    public List<JiCheInfo> selectAll();
    public Integer insertJiChe(JiCheInfo jiCheInfo);
    @Delete("DELETE FROM ji_che_info WHERE id = #{id}")
    public Integer deleteById(Integer id);
    @Update("update ji_che_info set step_shun_xu_id =#{stepShunXuId} where id = #{id}")
    public Integer updateStepShunXuById(Integer id,Integer stepShunXuId);
    @Update("update ji_che_info set event_change_id =#{eventChangeId} where id = #{id}")
    public Integer updateEventChangeById(Integer id,Integer eventChangeId);
    @Select("select * from ji_che_info where ji_xing = #{jixing} and ji_che_hao = #{jiche} ")
    @ResultMap(value = "jicheInfoAll")
    public JiCheInfo selectByJiXingJiChe(String jixing,String jiche);
    @Select("select * from ji_che_info where ji_xing_hao = #{jixing} and ji_che_hao = #{jiche} ")
    @ResultMap(value = "jicheInfoAll")
    public JiCheInfo selectByJiXingHaoJiChe(String jixing,String jiche);
}

package com.ruoyi.postran.mapper;

import com.ruoyi.postran.domain.PostranAssign;
import com.ruoyi.postran.domain.PostranEntryAssign;

import java.util.List;
import java.util.Map;

/**
 * 120核阳转运调派Mapper接口
 *
 * @author ruoyi
 * @date 2022-11-24
 */
public interface PostranAssignMapper
{
    /**
     * 查询120核阳转运调派
     *
     * @param fdId 120核阳转运调派主键
     * @return 120核阳转运调派
     */
    public PostranAssign selectPostranAssignByFdId(String fdId);

    /**
     * 查询120核阳转运调派列表
     *
     * @param postranAssign 120核阳转运调派
     * @return 120核阳转运调派集合
     */
    public List<PostranAssign> selectPostranAssignList(PostranAssign postranAssign);

    /**
     * 新增120核阳转运调派
     *
     * @param postranAssign 120核阳转运调派
     * @return 结果
     */
    public int insertPostranAssign(PostranAssign postranAssign);

    /**
     * 修改120核阳转运调派
     *
     * @param postranAssign 120核阳转运调派
     * @return 结果
     */
    public int updatePostranAssign(PostranAssign postranAssign);

    /**
     * 删除120核阳转运调派
     *
     * @param fdId 120核阳转运调派主键
     * @return 结果
     */
    public int deletePostranAssignByFdId(String fdId);

    /**
     * 批量删除120核阳转运调派
     *
     * @param fdIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePostranAssignByFdIds(String[] fdIds);

    List<PostranEntryAssign> selectPostranEntryAssignList(PostranEntryAssign postranEntryAssign);

    List<Map<String, Object>> getPostranAssignList(List<String> fdIds);
}

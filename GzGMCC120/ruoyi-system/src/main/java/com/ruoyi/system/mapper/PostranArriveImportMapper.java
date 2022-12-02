package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.PostranArriveImport;

/**
 * 到达医院导入Mapper接口
 * 
 * @author ruoyi
 * @date 2022-11-25
 */
public interface PostranArriveImportMapper 
{
    /**
     * 查询到达医院导入
     * 
     * @param importId 到达医院导入主键
     * @return 到达医院导入
     */
    public PostranArriveImport selectPostranArriveImportByImportId(Long importId);

    /**
     * 查询到达医院导入列表
     * 
     * @param postranArriveImport 到达医院导入
     * @return 到达医院导入集合
     */
    public List<PostranArriveImport> selectPostranArriveImportList(PostranArriveImport postranArriveImport);

    /**
     * 新增到达医院导入
     * 
     * @param postranArriveImport 到达医院导入
     * @return 结果
     */
    public int insertPostranArriveImport(PostranArriveImport postranArriveImport);

    /**
     * 修改到达医院导入
     * 
     * @param postranArriveImport 到达医院导入
     * @return 结果
     */
    public int updatePostranArriveImport(PostranArriveImport postranArriveImport);

    /**
     * 删除到达医院导入
     * 
     * @param importId 到达医院导入主键
     * @return 结果
     */
    public int deletePostranArriveImportByImportId(Long importId);

    /**
     * 批量删除到达医院导入
     * 
     * @param importIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePostranArriveImportByImportIds(Long[] importIds);

    public int  batchInsertPostranArriveImport(List<PostranArriveImport> arriveImports);
}

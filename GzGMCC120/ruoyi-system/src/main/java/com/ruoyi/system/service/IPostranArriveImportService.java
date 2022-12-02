package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.PostranArriveImport;

/**
 * 到达医院导入Service接口
 *
 * @author ruoyi
 * @date 2022-11-25
 */
public interface IPostranArriveImportService
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
     * 批量删除到达医院导入
     *
     * @param importIds 需要删除的到达医院导入主键集合
     * @return 结果
     */
    public int deletePostranArriveImportByImportIds(Long[] importIds);

    /**
     * 删除到达医院导入信息
     *
     * @param importId 到达医院导入主键
     * @return 结果
     */
    public int deletePostranArriveImportByImportId(Long importId);

    String importData(List<PostranArriveImport> importList, String type, String operName);
}
